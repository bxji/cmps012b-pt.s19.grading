class QueensUnitTests2 {
   public static void main(String args[]) {
      boolean t3, t4;
      int A2[] = {0, 4, 1, 3, 5, 2};
      int A3[] = {0, 4, 1, 5, 2, 3};
      int A4[] = {0, 5, 4, 3, 2, 1};
      int A5[] = {0, 1, 2, 3, 4, 5};

      Queens.nextPermutation(A2);
      t3 = arrayEquals(A2, A3);
      Queens.nextPermutation(A4);
      t4 = arrayEquals(A4, A5);
      System.out.println("nextPermutation() test1: " + (t3 ? "PASSED" : "FAILED"));
      System.out.println("nextPermutation() test2: " + (t4 ? "PASSED" : "FAILED"));
   }

   static boolean arrayEquals(int A[], int B[]) {
      if (A.length != B.length) return false;
      for (int i = 1; i < A.length; i++) // ignore index 0
         if (A[i] != B[i]) return false;
      return true;
   }
}
