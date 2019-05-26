#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <ctype.h>
#include <fcntl.h>
#include <unistd.h>
#include <signal.h>
#include <setjmp.h>

#include "Dictionary.h"

#define FIRST_TEST easy_test
#define MAXSCORE 80
#define PNTSPERTEST 20
#define BREAKER_TEST_RANGE 20000

static uint8_t testsPassed;
static volatile sig_atomic_t testStatus;
static uint8_t disable_exit_handler;
jmp_buf test_crash;

// don't let C destroy this at function end and allocate plenty
const char origtempstr[20*BREAKER_TEST_RANGE];

enum Test_e {
  easy_test = 0,
  medium_test,
  breaker_test,
  print_test,

  NUM_TESTS,
};

void strtrim(char *str) {
  int index = strlen(str) - 1;
  while (index >= 0 && isspace(str[index])) {
    index--;
  }
  if (index < 0) index = 0;
  else if (str[index] != '\0' && !isspace(str[index])) index++;
  str[index] = '\0';
}

// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left1(unsigned int value, int shift) {
  int sizeInBits = 8*sizeof(unsigned int);
  shift = shift & (sizeInBits - 1);
  if ( shift == 0 )
    return value;
  return (value << shift) | (value >> (sizeInBits -shift));
}
// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash1(char* input) {
  unsigned int result = 0xBAE86554;
  while (*input) {
    result ^= *input++;
    result = rotate_left1(result, 5);
  }
  return result;
}
// hash()
// turns a string into an int in the range 0 to tableSize
int hash1(char* key){
  return pre_hash1(key)%101;
}

char *testName(int test) {

  if (test == easy_test) return "easy_test";
  if (test == medium_test) return "medium_test";
  if (test == breaker_test) return "breaker_test";
  if (test == print_test) return "print_test";

  return "";
}

// return 0 if pass otherwise the number of the test that was failed
uint8_t runTest(Dictionary *pA, Dictionary *pB, int test) {
  Dictionary A = *pA;
  Dictionary B = *pB;

  switch(test) {

    case easy_test:
      {
        if (!isEmpty(A)) return 1;
        if (lookup(A, "aa") != NULL) return 2;
        makeEmpty(A);
        return 0;
      }
    case medium_test:
      {
        insert(A, "aa", "aa");
        delete(A, "aa");
        if (lookup(A, "aa") != NULL) return 2;
        insert(A,"aa", "aa");

        // find item that hashes same as "aa"
        int index = hash1("aa");
        char cmpstr[3];
        cmpstr[0] = cmpstr[1] = cmpstr[2] = '\0';
        while(hash1(cmpstr) != index) {
          for (char c1 = 'a'; (int) c1 <= 'z'; c1 = c1 + 1) {
            for (char c2 = 'a'; (int) c2 <= 'z'; c2 = c2 + 1) {
              cmpstr[0] = c1; cmpstr[1] = c2;
              if (strcmp(cmpstr, "aa") && hash1(cmpstr) == index) break;
            }
            if (strcmp(cmpstr, "aa") && hash1(cmpstr) == index) break;
          }
        }
        if (lookup(A, cmpstr) != NULL) return 2;
        if (lookup(A, "aa") == NULL) return 3;
        insert(A, cmpstr, cmpstr);
        if (lookup(A, "aa") == NULL) return 4;
        delete(A, "aa");
        makeEmpty(A);
        if (lookup(A, cmpstr) != NULL) return 5;
        return 0;
      }
    case breaker_test:
      {
        char *tempstr = (char *) origtempstr; // easy access
        for (int64_t i = 0; i < BREAKER_TEST_RANGE; i++) {
          char *curstr = (tempstr + 20 * i);
          sprintf(curstr, "item%ld", i); // initialize accordingly
          insert(A, curstr, curstr); // should be constant time
        }
        if (size(A) != BREAKER_TEST_RANGE) return 1; // constant time
        for (int64_t i = 0; i < BREAKER_TEST_RANGE; i++) {
          char *curstr = (tempstr + 20 * i);
          // at worst should take BREAKER_TEST_RANGE / 101 iterations
          if (strcmp(lookup(A, curstr), curstr)) return 2;
        }
        if (size(A) != BREAKER_TEST_RANGE) return 3; // constant time
        // note that last items should be head of their corresponding lists
        for (int64_t i = BREAKER_TEST_RANGE - 1; i >= 0; i--) {
          char *curstr = (tempstr + 20 * i);
          delete(A, curstr); // should be constant time
        }
        if (!isEmpty(A)) return 4; // constant time
        return 0;
      }
    case print_test:
      {
        FILE *in = NULL;
        FILE *out = NULL;
        out = fopen("print_test.txt", "w"); // for writing
        insert(B, "aa", "aa");
        printDictionary(out, A);
        fclose(out);
        for (int i = 0; i < 1000; i++); //some IO delay
        in = fopen("print_test.txt", "r"); // for reading
        char str[1001], temp[257];
        str[0] = '\0';
        temp[256] = '\0';
        while(fgets(temp, 256, in)) {
          strcat(str, temp);
        }
        fclose(in);
        strtrim(str);
        if (strlen(str) != 0) return 1;
        out = fopen("print_test.txt", "w"); // for writing
        insert(A, "aa", "aa");
        insert(B, "bb", "bb");

        // get two character string that hashes same as aa
        int index = hash1("aa");
        char *cmpstr = (char *) origtempstr; // and won't be able to delete
        cmpstr[0] = cmpstr[1] = cmpstr[2] = '\0';
        while(hash1(cmpstr) != index) {
          for (char c1 = 'a'; (int) c1 <= 'z'; c1 = c1 + 1) {
            for (char c2 = 'a'; (int) c2 <= 'z'; c2 = c2 + 1) {
              cmpstr[0] = c1; cmpstr[1] = c2;
              if (strcmp(cmpstr, "aa") && hash1(cmpstr) == index) break;
            }
            if (strcmp(cmpstr, "aa") && hash1(cmpstr) == index) break;
          }
        }
        insert(B, cmpstr, cmpstr);

        printDictionary(out, B);
        fclose(out);
        for (int i = 0; i < 1000; i++); //some IO delay
        in = fopen("print_test.txt", "r"); // for reading
        while(fgets(temp, 256, in)) {
          strcat(str, temp);
        }
        strtrim(str);
        fclose(in);
        char nstr[20];
        sprintf(nstr, "bb bb\n%s %s\naa aa", cmpstr, cmpstr);
        if (strcmp(str, nstr) != 0) return 2;
        system("rm print_test.txt");
        return 0;
      }
  }
  return 254;
}

void segfault_handler(int signal) { // everyone knows what this is
  testStatus = 255;
  longjmp(test_crash, 1);
}

void exit_attempt_handler(void) { // only I decide when you are done
  if (disable_exit_handler) return; // allow this to be disabled
  testStatus = 255;
  longjmp(test_crash, 2);
}

void abrupt_termination_handler(int signal) { // program killed externally
  testStatus = 255;
  longjmp(test_crash, 3);
}

//at least I got rid of the goto
void end_program(uint8_t argc) {
  disable_exit_handler = 1;

  uint8_t totalScore = (MAXSCORE - NUM_TESTS * PNTSPERTEST) +
    testsPassed * PNTSPERTEST;
  if (testStatus == 255) totalScore = 10;
  if (testStatus != 255 && argc == 2) {
    printf("\nYou passed %d out of %d tests\n", testsPassed, NUM_TESTS);
  } else if (argc == 2) {
    printf("\nRecieving charity points for a premature program end\n");
  }

  printf("\nYou will receive %d out of %d possible points on the Hashtable Dictionary\n\n",
      totalScore, MAXSCORE);

  exit(0);
}

int main (int argc, char **argv) {
  if (argc > 2 || (argc == 2 && strcmp(argv[1], "-v") != 0)) {
    printf("Usage: %s [-v]", (argc > 0 ? argv[0] : "./ModelDictionaryTest"));
    exit(1);
  }

  printf("\n"); // more spacing
  if (argc == 2) printf("\n"); // consistency in verbose mode

  testsPassed = 0;
  disable_exit_handler = 0;
  atexit(exit_attempt_handler);
  signal(SIGSEGV, segfault_handler);

  for (uint8_t i = FIRST_TEST; i < NUM_TESTS; i++) {
    uint8_t fail_type = setjmp(test_crash);
    if (fail_type != 0) goto fail_jmp;
    Dictionary A = newDictionary();
    Dictionary B = newDictionary();
    testStatus = runTest(&A, &B, i);
    freeDictionary(&A);
    freeDictionary(&B);
fail_jmp:
    if (argc == 2) { // it's verbose mode
      printf("Test %s %s", testName(i),
          testStatus == 0 ? "PASSED" : "FAILED");
      if (testStatus == 255) {
        printf(": due to a %s\n", fail_type == 1 ? "segfault" : fail_type == 2 ?
            "program exit" : "program interruption");
        printf("\nWARNING: Program will now stop running tests\n\n");
        end_program(argc);

      } else if (testStatus == 254) {
        printf(": undefined test\n");
      } else if (testStatus != 0) {
        printf(": test %d\n", testStatus);
      } else {
        printf("\n");
      }
    }
    if (testStatus == 0) {
      testsPassed++;
    }
  }

  end_program(argc);

  return 0;
}
