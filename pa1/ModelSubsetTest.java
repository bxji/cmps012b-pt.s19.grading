import java.lang.Throwable;
import java.lang.Integer;
import java.io.*;
import java.util.Scanner;

class ModelSubsetTest {

    static int test_count;

    static boolean verbose;

    static int setToString1_test;
    static int setToString2_test;
    static int printSubsets1_test;
    static int printSubsets2_test;
    static int printSubsets3_test;

    static String testName(int test) {

        if (test == setToString1_test)
            return "setToString1_test";
        if (test == setToString2_test)
            return "setToString2_test";
        if (test == printSubsets1_test)
            return "printSubsets1_test";
        if (test == printSubsets2_test)
            return "printSubsets2_test";
        if (test == printSubsets3_test)
            return "printSubsets3_test";

        return "";
    }

    static boolean strcmp(int[] a, String b) {
        return (Subset.setToString(a)).compareTo(b) == 0;
    }

    public static int runTest(int test) {
        int B[] = new int[5];
        int B1[] = new int[6];

        try {
            if (test == setToString1_test) {

                int a[] = { 0, 0, 0, 0 };
                int b[] = { 1, 1, 1, 1 };
                int c[] = { 1, 0, 1, 0 };
                int d[] = new int[1];
                int e[] = new int[2000];
                int f[] = { 0, 1 };

                if (!strcmp(a, "{}") &&
                    !strcmp(a, "{ }"))
                    return 1;
                if (!strcmp(b, "{1, 2, 3}"))
                    return 2;
                if (!strcmp(c, "{2}"))
                    return 3;
                if (!strcmp(d, "{}") && 
                    !strcmp(d, "{ }"))
                    return 4;
                if (!strcmp(e, "{}") &&
                    !strcmp(e, "{ }"))
                    return 5;
                if (!strcmp(f, "{1}"))
                    return 6;

                e[1999] = 1;

                if (!strcmp(e, "{1999}"))
                    return 7;

            } else if (test == setToString2_test) {
                // not actually hard. there's just nothing to test...

                int a[] = { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
                int b[] = { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
                int c[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

                if (!strcmp(a, "{2, 4, 6, 8, 10, 12, 14, 16, 18}"))
                    return 1;
                if (!strcmp(b, "{1, 3, 5, 7, 9, 11, 13, 15, 17}"))
                    return 2;
                if (!strcmp(c, "{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18}"))
                    return 3;
                
            } else if (test == printSubsets1_test) {
                int test_status = -1;

                PrintStream o = new PrintStream(new File("unit-out1.txt"));
                System.setOut(o);
                
                B[1] = 1;
                Subset.printSubsets(B, 1, 2);
                o.close();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                test_status = CheckResult("modelunit-out1.txt", "unit-out1.txt");

                return test_status;

            } else if (test == printSubsets2_test) {
                int test_status = -1;

                PrintStream o4 = new PrintStream(new File("unit-out4.txt"));
                System.setOut(o4);

                B1[1] = 1;
                B1[3] = 1;

                Subset.printSubsets(B1, 1, 6);
                o4.close();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                test_status = CheckResult("modelunit-out4.txt", "unit-out4.txt");

                return test_status;

            } else if (test == printSubsets3_test) {
                int test_status = -1;

                PrintStream o5 = new PrintStream(new File("unit-out5.txt"));
                System.setOut(o5);

                B1[1] = 1;
                B1[3] = 1;
                
                Subset.printSubsets(B1, 1, 5);
                o5.close();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                test_status = CheckResult("modelunit-out5.txt", "unit-out5.txt");

                return test_status;
            }
        } catch (Exception e) {
            if (verbose) {
                System.out.println(
                        "\nUnfortunately your program crashed on test " + testName(test) + " With an exception of:\n");
                e.printStackTrace();
                System.out.println();
            }
            return 255;
        } catch (StackOverflowError s) {
            if (verbose) {
                System.out.println("\nUnfortunately your program crashed on test " + testName(test)
                        + " With a stack overflow error\n");
            }
            return 255;
        }
        return 0;
    }

    public static String compareFiles(Scanner file1, Scanner file2) {
        String lineA;
        String lineB;
        int x = 1;
        String comparison = "";
        while (file1.hasNextLine() || file2.hasNextLine()) {
            lineA = "";
            lineB = "";
            if (file1.hasNextLine())
                lineA = file1.nextLine();
            if (file2.hasNextLine())
                lineB = file2.nextLine();
            lineA = lineA.trim();
            lineB = lineB.trim();
            if (!lineA.equals(lineB)) {
                comparison += "Line " + x;
                x++;
                comparison += "< " + lineA;
                comparison += "> " + lineB + "\n";
            }
        }
        return comparison;
    }

    public static int CheckResult(String file1, String file2) {
        try {
            File f1 = new File(file1);
            File f2 = new File(file2);
            String comparison = compareFiles(new Scanner(f1), new Scanner(f2));
            if (!comparison.equals("")) {
                //System.out.println(comparison);
                return 1;
            } else {
                return 0;
            }
        } catch (FileNotFoundException e) {
            return 255;
        }

    }

    public static void main(String args[]) {

        if (args.length > 1 || (args.length == 1 && !args[0].equals("-v"))) {
            System.err.println("Usage: ./ListTest [-v]");
            System.exit(1);
        }
        verbose = false;
        if (args.length == 1)
            verbose = true;

        test_count = 0;

        // form is TESTCASE_FUNCTION
        setToString1_test = test_count++;
        setToString2_test = test_count++;
        printSubsets1_test = test_count++;
        printSubsets2_test = test_count++;
        printSubsets3_test = test_count++;

        int test_status = 0;
        int tests_passed = 0;
        if (verbose)
            System.out.println("\nList of tests passed/failed:\n");
        for (int i = 0; i < test_count; i++) {
            test_status = runTest(i);
            if (verbose)
                System.out.printf("%s %s", testName(i), test_status == 0 ? "PASSED" : "FAILED");
            if (test_status == 0) {
                if (verbose)
                    System.out.printf("\n");
                tests_passed++;
            } else if (test_status == 255) {
                if (verbose)
                    System.out.printf(": due to exception\n");
                break;
            } else {
                if (verbose)
                    System.out.printf(": in test %d\n", test_status);
            }
        }

        if (verbose && test_status != 255) {
            System.out.printf("\n\nPassed %d tests out of %d possible\n", tests_passed, test_count);
        } else if (verbose) {
            System.out.printf("\n\nReceiving Charity points due to an exception\n");
        }

        final int maxScore = 30;
        final int charity = 10;

        int totalPoints = (maxScore - test_count * 6) + tests_passed * 6;
        if (test_status == 255) { // your code had an exception
            totalPoints = charity;
        }

        System.out.printf("\nThis gives you a score of %d / %d " + "for this component of the assignment\n\n",
                totalPoints, maxScore);
    }
}
