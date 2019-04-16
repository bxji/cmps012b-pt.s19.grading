import java.lang.Throwable;
import java.lang.Integer;

class ModelSubsetTest {

    static int test_count;

    static boolean verbose;

    static int setToStringEasy_test;
    static int setToStringMedium_test;
    static int setToStringHard_test;
    static int setToStringEdge_test;
    static int printSubsets_test;

    static String testName(int test) {

        if (test == setToStringEasy_test) return "setToStringEasy_test";
        if (test == setToStringMedium_test) return "setToStringMedium_test";
        if (test == setToStringHard_test) return "setToStringHard_test";
        if (test == setToStringEdge_test) return "setToStringEdge_test";
        if (test == printSubsets_test) return "printSubsets_test";

        return "";
    }

    static boolean strcmp(int[] a, String b) { return (Subset.setToString(a)).compareTo(b) == 0; }

    public static int runTest(int test) {
        try {
            if (test == setToStringEasy_test) {
                
                int a[] = { 0, 0, 0, 0 };
                int b[] = { 1, 1, 1, 1 };
                int c[] = { 1, 0, 1, 0 };
                
                if (!strcmp(a, "{}"))
                    return 1;
                if (!strcmp(b, "{1, 2, 3, 4}"))
                    return 2;
                if (!strcmp(c, "{1, 3}"))
                    return 3;
                
                    
            } else if (test == setToStringMedium_test) {

                int a[] = { 1, 0, 1, 0, 1, 0, 1, 0, 1 };
                int b[] = { 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1 };
                // arrays are initialized to 0.
                int c[] = new int[1000];
                
                if (!strcmp(a, "{1, 3, 5, 7, 9}"))
                    return 1;
                if (!strcmp(b, "{5, 9, 10, 13}"))
                    return 2;
                if (!strcmp(c, "{}"))
                    return 3;                
                
            } else if (test == setToStringHard_test) {
                // not actually hard. there's nothing to test...

                int a[] = { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
                int b[] = { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
                int c[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

                if (!strcmp(a, "{1, 3, 5, 7, 9, 11, 13, 15, 17, 19}"))
                    return 1;
                if (!strcmp(b, "{2, 4, 6, 8, 10, 12, 14, 16, 18}"))
                    return 2;
                if (!strcmp(c, "{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}"))
                    return 3;
            } else if (test == setToStringEdge_test) {
                int a[] = new int[0];
                int b[] = new int[Integer.MAX_VALUE];
                int c[] = { 1, 0 };
                
                if (!strcmp(a, "{}"))
                    return 1;
                if (!strcmp(b, "{}"))
                    return 2;
                if (!strcmp(c, "{1}"))
                    return 3;

                b[Integer.MAX_VALUE - 1] = 1;

                if (!strcmp(b, "{2147483646}"))
                    return 4;
                
            } else if (test == printSubsets_test) {
                // free points for now
            }
        } catch (Exception e) {
            if (verbose) {
                System.out.println("\nUnfortunately your program crashed on test " +
                                   testName(test) + " With an exception of:\n");
                e.printStackTrace();
                System.out.println();
            }
            return 255;
        } catch (StackOverflowError s) {
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
        setToStringEasy_test = test_count++;
        setToStringMedium_test = test_count++;
        setToStringHard_test = test_count++;
        setToStringEdge_test = test_count++;
        printSubsets_test = test_count++;

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

        final int maxScore = 30;
        final int charity = 10;

        int totalPoints = (maxScore - test_count * 6) + tests_passed * 6;
        if (test_status == 255) { // your code had an exception
            totalPoints = charity;
        }

        System.out.printf("\nThis gives you a score of %d / %d " +
                          "for this component of the assignment\n\n", totalPoints, maxScore);
    }
}
