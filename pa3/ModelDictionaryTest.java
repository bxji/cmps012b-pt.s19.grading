import java.lang.Throwable;
import java.io.*;

class ModelDictionaryTest {

  static int test_count;

  static int isEmpty_test1;
  static int size_test1;
  static int size_test2;
  static int lookup_test1;
  static int lookup_test2;
  static int lookup_test3;
  static int toString_test1;
  static int exception_test1;

  static String testName(int test) {

    if (test == isEmpty_test1) return "isEmpty_test1";
    if (test == size_test1) return "size_test1";
    if (test == size_test2) return "size_test2";
    if (test == lookup_test1) return "lookup_test1";
    if (test == lookup_test2) return "lookup_test2";
    if (test == lookup_test3) return "lookup_test3";
    if (test == toString_test1) return "toString_test1";
    if (test == exception_test1) return "exception_test1";

    return "";
  }

  public static int runTest(int test) {

    Dictionary A = new Dictionary();
    Dictionary B = new Dictionary();
    final int test_range = 1000;

    try {
      if(test == isEmpty_test1) {
        if (!A.isEmpty() || !B.isEmpty()) return 1;
        A.insert("test", "123");
        if (A.isEmpty() || !B.isEmpty()) return 2;
      } else if(test == size_test1) {
        if (A.size() != B.size()) return 1;
        A.insert("test", "123");
        if (A.size() == B.size()) return 2;
        B.insert("test", "123");
        if (A.size() != B.size()) return 3;
      } else if(test == size_test2) {
        for (int i = 0; i < test_range; i++) {
          A.insert(i + 1 + "", "test" + i + 1);
        }
        for (int i = 0; i < test_range / 10; i++) {
          B.insert(i + 1 + "", "test" + i + 1);
        }
        if (A.size() != test_range || B.size() != test_range / 10) return 1;
        for (int i = test_range / 10; i < test_range; i++) {
          B.insert(i + 1 + "", "test" + i + 1);
        }
        if (A.size() != B.size()) return 2;
        for (int i = test_range * 9 / 10; i < test_range; i++) {
          A.delete(i + 1 + "");
        }
        if (A.size() != test_range * 9 / 10 || B.size() != test_range) return 3;
        for (int i = test_range * 9 / 10 - 1; i >= 0; i--) {
          A.delete(i + 1 + "");
        }
        B.makeEmpty();
        if (A.size() != 0 || B.size() != 0) return 4;
      } else if(test == lookup_test1) {
        // should be null
        if (A.lookup("test") != B.lookup("test")) return 1;
        A.insert("test", "123");
        if (!A.lookup("test").equals("123") || B.lookup("test") != null) return 2;
        B.insert("test", "123");
        if (!A.lookup("test").equals(B.lookup("test"))) return 3;
      } else if(test == lookup_test2) {
        for (int i = 0; i < test_range; i++) {
          A.insert(i + 1 + "", "test" + i + 1);
          if (!A.lookup(i + 1 + "").equals("test" + i + 1)) return 1;
        }
        for (int i = 0; i < test_range / 10; i++) {
          B.insert(i + 1 + "", "test" + i + 1);
          if (!B.lookup(i + 1 + "").equals("test" + i + 1)) return 2;
        }
        for (int i = test_range / 10; i < test_range; i++) {
          if (B.lookup(i + 1 + "") != null) return 3;
          B.insert(i + 1 + "", "test" + i + 1);
          if (!B.lookup(i + 1 + "").equals("test" + i + 1)) return 4;
        }
        for (int i = test_range * 9 / 10; i < test_range; i++) {
          A.delete(i + 1 + "");
          if (A.lookup(i + 1 + "") != null) return 5;
        }
        for (int i = test_range * 9 / 10 - 1; i >= 0; i--) {
          A.delete(i + 1 + "");
          if (A.lookup(i + 1 + "") != null) return 6;
        }
        B.makeEmpty();
        for (int i = 0; i < test_range; i++) {
          if (A.lookup(i + 1 + "") != null) return 7;
        }
      } else if(test == lookup_test3) {
        A.insert("repeat", "123");
        if (!A.lookup("repeat").equals("123")) return 1;
        A.delete("repeat");
        if (A.lookup("repeat") != null) return 2;
        A.insert("repeat", "123");
        if (!A.lookup("repeat").equals("123")) return 3;
        for (int i = 0; i < test_range; i++) {
          A.insert("" + (i + 1), "test" + (i + 1));
        }
        A.delete("" + (test_range / 2));
        if (A.lookup("" + (test_range / 2)) != null) return 4;
        if (!A.lookup("" + (test_range / 2 + 1)).equals(
              "test" + (test_range / 2 + 1))) return 5;
        if (!A.lookup("" + (test_range / 2 - 1)).equals(
              "test" + (test_range / 2 - 1))) return 6;
        A.delete("" + test_range);
        if (!A.lookup((test_range - 1) + "").equals(
              "test" + (test_range - 1))) return 7;
      } else if(test == toString_test1) {
        if (!A.toString().equals(B.toString())) return 1;
        A.insert("1", "one");
        B.insert("2", "two");
        A.insert("2", "two");
        B.insert("1", "one");
        if (A.toString().equals(B.toString())) return 2;
        B.delete("2");
        B.insert("2", "two");
        if (!A.toString().equals(B.toString())) return 1;
      } else if(test == exception_test1) {
        boolean failed = true;
        try {
          A.delete("test");
        } catch(KeyNotFoundException k) {
          failed = false;
        }
        if (failed) return 1;
        failed = true;
        A.insert("test", "123");
        try {
          A.insert("test", "123");
        } catch(DuplicateKeyException d) {
          failed = false;
        }
        if (failed) return 2;
        failed = true;
        for (int i = 0; i < test_range; i++) {
          A.insert(i + 1 + "", "test" + i + 1);
        }
        for (int i = test_range - 1; i >= 0; i--) {
          A.delete(i + 1 + "");
          try{
            A.delete(i + 1 + "");
          } catch (KeyNotFoundException k) {
            failed = false;
          }
          if (failed) return 3;
          failed = true;
        }
      }
    } catch (Exception e) {
      System.out.println("\nUnfortunately your program crashed on test " +
          testName(test) + " With an exception of:\n");
      e.printStackTrace();
      System.out.println();
      return 255;
    } catch (StackOverflowError se) {
      System.out.println("\nUnfortunately your program crashed on test " +
          testName(test) + " With a stack overflow error\n");
      return 255;
    }
    return 0;
  }

  public static void main(String args[]) {

    test_count = 0;

    isEmpty_test1 = test_count++;
    size_test1 = test_count++;
    size_test2 = test_count++;
    lookup_test1 = test_count++;
    lookup_test2 = test_count++;
    lookup_test3 = test_count++;
    toString_test1 = test_count++;
    exception_test1 = test_count++;

    int test_status = 0;
    int tests_passed = 0;
    System.out.println("\nList of tests passed/failed:\n");
    for (int i = 0; i < test_count; i++) {
      test_status = runTest(i);
      System.out.printf("%s %s", testName(i),
          test_status == 0 ? "PASSED" : "FAILED");
      if (test_status == 0) {
        System.out.printf("\n");
        tests_passed++;
      } else if (test_status == 255) {
        System.out.printf(": due to exception\n");
        break;
      } else {
        System.out.printf(": in test %d\n", test_status);
      }
    }

    if (test_status != 255) {
      System.out.printf("\n\nPassed %d tests out of %d possible\n",
          tests_passed, test_count);
    } else {
      System.out.printf("\n\nReceiving Charity points due to an exception\n");
    }

    final int maxScore = 80;
    final int charity = 5;
    final int pntspertest = maxScore / test_count;

    int totalPoints = (maxScore - test_count * pntspertest) +
      tests_passed * pntspertest;

    if (test_status == 255) { // your code had an exception
      totalPoints = charity;
    }

    System.out.printf("\nThis gives you a score of %d / %d " +
        "for this component of the assignment\n\n", totalPoints, maxScore);
  }
}

