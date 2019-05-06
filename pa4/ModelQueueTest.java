import java.lang.Throwable;
import java.io.*;

class ModelQueueTest {

  static int test_count;

  static boolean verbose;

  static int easy_test;
  static int medium_test;
  static int hard_test;
  static int breaker_test;
  static int exception_test;

  static String testName(int test) {

    if (test == easy_test) return "easy_test";
    if (test == medium_test) return "medium_test";
    if (test == hard_test) return "hard_test";
    if (test == breaker_test) return "breaker_test";
    if (test == exception_test) return "exception_test";

    return "";
  }

  public static int runTest(int test) {

    Queue A = new Queue();
    Queue B = new Queue();
    int break_test_range = 10000;

    try {
      if (test == easy_test) {
        if (!A.isEmpty()) return 1;
        A.enqueue(1);
        if (A.isEmpty() || A.length() != 1) return 2;
        A.dequeueAll();
        if (!A.isEmpty() || !A.toString().trim().equals("")) return 3;
      } else if (test == medium_test) {
        A.enqueue("test");
        A.enqueue(123);
        if (!A.peek().equals("test") || !A.dequeue().equals("test")) return 1;
        A.enqueue(new Queue());
        if (A.length() != 2) return 2;
        if (((int) A.peek()) != 123 || ((int) A.dequeue()) != 123) return 3;
        A.dequeueAll();
        A.enqueue("pointer_fun");
        A.enqueue(A.peek());
        A.enqueue(A.dequeue());
        if (A.dequeue() != A.dequeue() || !A.isEmpty()) return 4;
      } else if (test == hard_test) {
        // queueception
        A.enqueue(new Queue());
        // queueception loop
        ((Queue) A.peek()).enqueue(A);
        if (((Queue) A.peek()).peek() != A) return 1;
        A.enqueue(A);
        B = (Queue) A.dequeue();
        if (A.length() != 1) return 2;
        ((Queue) B.peek()).enqueue(B);
        A.enqueue("123");
        if (A.dequeue() != A) return 3;
        ((Queue) A.peek()).dequeueAll();
        A.enqueue("123");
        A.enqueue(A.dequeue());
        B.enqueue("test");
        A.dequeue();
        if (!A.toString().trim().equals("123 test")) return 4;
      } else if (test == breaker_test) {
        B.enqueue("PA4");
        String str = B.toString();
        for (int i = 0; i < break_test_range; i++) {
          A.enqueue(B);
          if (i < break_test_range - 1) str += " " + B.toString();
        }
        if (!A.toString().trim().equals(str.trim())) return 1;
        if (A.length() != break_test_range) return 2;
        for (int i = 0; i < break_test_range - 1; i++) {
          A.dequeue();
        }
        A.dequeueAll();
        if (!A.isEmpty()) return 3;
        str = B.peek().toString();
        A.enqueue(B.dequeue());
        for (int i = 0; i < break_test_range - 1; i++) {
          A.enqueue(A.peek());
          str += " " + A.peek().toString();
        }
        A.enqueue(B);
        str += " " + B.toString();
        if (!A.toString().trim().equals(str.trim())) return 5;
        A.dequeueAll();
        if (A.length() != B.length()) return 6;
      } else if (test == exception_test) {
        boolean exception_caught = false;
        try {
          A.dequeue();
        } catch(QueueEmptyException qe) {
          exception_caught = true;
        }
        if (!exception_caught) return 1;
        try {
          A.dequeueAll();
        } catch(QueueEmptyException qe) {
          exception_caught = true;
        }
        if (!exception_caught) return 2;
        exception_caught = false;
        A.enqueue(1);
        A.dequeue();
        try {
          A.dequeue();
        } catch(QueueEmptyException qe) {
          exception_caught = true;
        }
        if (!exception_caught) return 3;
        exception_caught = false;
        A.enqueue(2);
        A.peek();
        A.enqueue(3);
        A.dequeue();
        A.peek();
        A.dequeueAll();
        try {
          A.dequeue();
        } catch(QueueEmptyException qe) {
          exception_caught = true;
        }
        if (!exception_caught) return 4;
        exception_caught = false;
        try {
          A.peek();
        } catch(QueueEmptyException qe) {
          exception_caught = true;
        }
        if (!exception_caught) return 5;
      }
    } catch (Exception e) {
      if (verbose) {
        System.out.println("\nUnfortunately your program crashed on test " +
            testName(test) + " With an exception of:\n");
        e.printStackTrace();
        System.out.println();
      }
      return 255;
    } catch (StackOverflowError se) {
      if (verbose) {
        System.out.println("\nUnfortunately your program crashed on test " +
            testName(test) + " With a stack overflow error\n");
      }
      return 255;
    }
    return 0;
  }

  public static void main(String args[]) {

    if (args.length > 1 || (args.length == 1 && !args[0].equals("-v"))) {
      System.err.println("Usage: ./ListTest [-v]");
      System.exit(1);
    }
    verbose = false;
    if (args.length == 1) verbose = true;

    test_count = 0;

    // form is TESTCASE_FUNCTION
    easy_test = test_count++;
    medium_test = test_count++;
    hard_test = test_count++;
    breaker_test = test_count++;
    exception_test = test_count++;

    int test_status = 0;
    int tests_passed = 0;
    if (verbose)
      System.out.println("\nList of tests passed/failed:\n");
    for (int i = 0; i < test_count; i++) {
      test_status = runTest(i);
      if (verbose)
        System.out.printf("%s %s", testName(i),
            test_status == 0 ? "PASSED" : "FAILED");
      if (test_status == 0) {
        if (verbose) System.out.printf("\n");
        tests_passed++;
      } else if (test_status == 255) {
        if (verbose) System.out.printf(": due to exception\n");
        break;
      } else {
        if (verbose) System.out.printf(": in test %d\n", test_status);
      }
    }

    if (verbose && test_status != 255) {
      System.out.printf("\n\nPassed %d tests out of %d possible\n",
          tests_passed, test_count);
    } else if (verbose) {
      System.out.printf("\n\nReceiving Charity points due to an exception\n");
    }

    final int maxScore = 25;
    final int charity = 2;

    int totalPoints = (maxScore - test_count * 5) + tests_passed * 5;
    if (test_status == 255) { // your code had an exception
      totalPoints = charity;
    }

    System.out.printf("\nThis gives you a score of %d / %d " +
        "for this component of the assignment\n\n", totalPoints, maxScore);

  }
}

