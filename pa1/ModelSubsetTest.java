import java.lang.Throwable;
import java.lang.Integer;
import java.io.*;
import java.util.Scanner;

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
                int b[] = new int[2000];
                int c[] = { 1, 0 };
                
                if (!strcmp(a, "{}"))
                    return 1;
                if (!strcmp(b, "{}"))
                    return 2;
                if (!strcmp(c, "{1}"))
                    return 3;

                b[1999] = 1;

                if (!strcmp(b, "{1998}"))
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
//          System.out.println("lineA: " + lineA);
//          System.out.println("lineB: " + lineB);
            lineA =lineA.trim();
            lineB =lineB.trim();
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
    public static int CheckResult(String file1, String file2) {
        try{
        File f1 = new File(file1);
        File f2 = new File(file2);
       String comparison = compareFiles(new Scanner(f1), new Scanner(f2));
        if(!comparison.equals("")){
            System.out.println("Result is wrong.");
            System.out.println(comparison);
            return 1;
        }else{
            return 0;
        }
    }catch (FileNotFoundException e){
        return 255;

    }
   
    }
public static int runTest_middleStaus(int test) {
        try {
            int B[] = new int[5];
            int B1[] = new int[6];
            int test_status = -1;
            if (test == setToStringEasy_test) {
                PrintStream o = new PrintStream(new File("unit-out1.txt")); 
         //PrintStream console = System.out; 
                System.setOut(o);
         //test1
                B[1]=1;
                Subset.printSubsets( B, 1 , 2);
                o.close();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                // if (!strcmp(a, "{}"))
                //     return 1;
                // if (!strcmp(b, "{1, 2, 3, 4}"))
                //     return 2;
                // if (!strcmp(c, "{1, 3}"))
                //     return 3;
               test_status =  CheckResult("modelunit-out1.txt", "unit-out1.txt");
                    
            } else if (test == setToStringMedium_test) {

                PrintStream o1 = new PrintStream(new File("unit-out2.txt")); 
         //PrintStream console = System.out; 
                System.setOut(o1);
                B[1]=1;
         
                 Subset.printSubsets( B, 1 , 4);
                o1.close();  
                 System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                 test_status =  CheckResult("modelunit-out2.txt", "unit-out2.txt");
                
            } else if (test == setToStringHard_test) {
                // not actually hard. there's nothing to test...
                PrintStream o3 = new PrintStream(new File("unit-out3.txt")); 
             //PrintStream console = System.out; 
                System.setOut(o3);
                B[1]=1;
                B[3]=1;
                Subset.printSubsets( B, 0 , 4);
                o3.close();
                 System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                 test_status =  CheckResult("modelunit-out3.txt", "unit-out3.txt");
                
            } else if (test == setToStringEdge_test) {
                B1[1]=1;
                B1[3]=1;
             
             //test4 base case  print nothing
                
                PrintStream o4 = new PrintStream(new File("unit-out4.txt")); 
                 //PrintStream console = System.out; 
                System.setOut(o4);
                B1[1]=1;
                B1[3]=1;
                Subset.printSubsets( B1, 1 , 6);
                o4.close(); 
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                test_status =  CheckResult("modelunit-out4.txt", "unit-out4.txt");

                
            } else if (test == printSubsets_test) {
                PrintStream o5 = new PrintStream(new File("unit-out5.txt")); 
                 //PrintStream console = System.out; 
                System.setOut(o5);
                B1[1]=1;
                B1[3]=1;
                Subset.printSubsets( B1, 1 , 5);
                o5.close(); 
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                test_status =  CheckResult("modelunit-out5.txt", "unit-out5.txt");

            }
            return test_status;
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
            //test_status = runTest(i);
           test_status = runTest_middleStaus(i);
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
