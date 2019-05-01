import java.lang.Throwable;
import java.lang.Integer;
import java.io.*;
import java.util.Scanner;

class ModelQueensTest {

    static int test_count;

    static boolean verbose;

    static int printBoard_test;
    static int placeQueen_test;
    static int removeQueen_test;
    static int findSolution_test;
    static int baseCase_test;

    static String testName(int test) {

        if (test == printBoard_test)
            return "printBoard_test";
        if (test == placeQueen_test)
            return "placeQueen_test";
        if (test == removeQueen_test)
            return "removeQueen_test";
        if (test == findSolution_test)
            return "findSolution_test";
        if (test == baseCase_test)
            return "baseCase_test";

        return "";
    }

    // static boolean strcmp(int[] a, String b) {
    //     return (Queens.setToString(a)).compareTo(b) == 0;
    // }

    static String printArray(int [][]B) {
        String result="{";
        for(int i=0;i<B.length;i++) {
            result +="{";
            for(int j=0;j<B[i].length;j++) {
                if(j!=B[i].length-1)
                result= result+B[i][j]+",";
                else {
                    result= result+B[i][j];
                }
            }
            result +="}";
            if(i!=B.length-1)
                result +=",";
        }
        result +="}";
        return result;
    }

    public static int runTest(int test) {

        try {
            if (test == printBoard_test) {
                //test1 print board
                int test_status = -1;
                int  B1[][]= {{0,0,0,0,0},{2,0,1,0,0},{4,-1,-1,-1,1},{1,1,-1,-1,-2},{3,-1,-3,1,-1}};
                // set the output to unit file
                PrintStream o1 = new PrintStream(new File("unit-out1.txt")); 
                System.setOut(o1);
                // function being tested
                Queens.printBoard(B1);
                o1.close();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                //check if outputs are the same
                test_status = CheckResult("modelunit-out1.txt", "unit-out1.txt");
                return test_status;

            } else if (test == placeQueen_test) {
                //test2 place queen
                //two possible result since students may update all the board, in the instruction Pat only update cells below the current rows
                int test_status = -1;
                int  B2[][]= {{0,0,0,0,0},{2,0,1,0,0},{4,-1,-1,-1,1},{0,0,-1,-1,-2},{0,0,-2,0,-1}};
                
                PrintStream o2 = new PrintStream(new File("unit-out2.txt")); 
                System.setOut(o2);
                Queens.placeQueen(B2,3,1);   
                System.out.println(printArray(B2));
                o2.close();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

                test_status = CheckResult("modelunit-out2-1.txt", "unit-out2.txt");
                if (test_status == 1) {
                    test_status = CheckResult("modelunit-out2-2.txt", "unit-out2.txt");
                } 

                return test_status;

            } else if (test == removeQueen_test) {
                //test3 remove queen
                int test_status = -1;
                int  B3[][]= {{0,0,0,0,0},{2,0,1,0,0},{4,-1,-1,-1,1},{1,1,-1,-1,-2},{3,-1,-3,1,-1}};
                
                PrintStream o3 = new PrintStream(new File("unit-out3.txt")); 
                System.setOut(o3);
                Queens.removeQueen( B3, 4 , 3);
                System.out.println(B3[4][3]+":"+B3[4][0]);
                o3.close();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

                test_status = CheckResult("modelunit-out3.txt", "unit-out3.txt");
                return test_status;

            } else if (test == findSolution_test) {
                //test4 findsolution 
                int test_status = -1;
                int  B4[][]= {{0,0,0,0,0},{2,0,1,0,0},{4,-1,-1,-1,1},{1,1,-1,-1,-2},{0,-1,-3,0,-1}};

                PrintStream o4 = new PrintStream(new File("unit-out4.txt")); 
                System.setOut(o4);
                
                System.out.println(Queens.findSolutions( B4, 4 , "")); //don't use verbose since some student will use -v some use verbose
                o4.close();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

                test_status = CheckResult("modelunit-out4.txt", "unit-out4.txt");
                return test_status;

            } else if (test == baseCase_test) {
                //test5 base case  print nothing
                int test_status = -1;
                int  B5[][]= {{0,0,0,0,0},{2,0,1,0,0},{0,-1,-1,-1,0},{0,0,-1,0,-1},{0,0,-1,0,0}};

                PrintStream o5 = new PrintStream(new File("unit-out5.txt"));
                System.setOut(o5);
                System.out.println(Queens.findSolutions( B5, 2 , ""));
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

    public static String compareFiles (Scanner file1, Scanner file2) {
        String lineA ;
        String lineB ;
        int x = 1;
        String comparison = "";
        while (file1.hasNextLine() || file2.hasNextLine()) {
            lineA ="";
            lineB ="";
            if(file1.hasNextLine())
                lineA = file1.nextLine();
            if(file2.hasNextLine())
                lineB = file2.nextLine();
        System.out.println("lineA: " + lineA);
        System.out.println("lineB: " + lineB);
            lineA = lineA.replace(" ","");
            lineB = lineB.replace(" ","");
            if (!lineA.equals(lineB)) {
                
                comparison+= "Line " + x;
                x++;
                comparison+= "< " + lineA;
                comparison+= "> " + lineB + "\n";
                //System.out.println("Line " + x++);
                //System.out.println("< " + lineA);
                //System.out.println("> " + lineB + "\n");
                
            }
        }
        return comparison; 
    }
    public static int CheckResult(String a, String b) throws FileNotFoundException {
        try {
        File f1 = new File(a);
        File f2 = new File(b);
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
        printBoard_test = test_count++;
        placeQueen_test = test_count++;
        removeQueen_test = test_count++;
        findSolution_test = test_count++;
        baseCase_test = test_count++;

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

        final int maxScore = 25;
        final int charity = 10;

        int totalPoints = (maxScore - test_count * 5) + tests_passed * 5;
        if (test_status == 255) { // your code had an exception
            totalPoints = charity;
        }

        System.out.printf("\nThis gives you a score of %d / %d " + "for this component of the assignment\n\n",
                totalPoints, maxScore);
    }
}
