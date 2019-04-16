class QueensUnitTests1 {
   public static void main(String args[]) {
      boolean t1, t2;
      int A1[] = {0, 1, 2, 3, 4, 5};
      int A2[] = {0, 4, 1, 3, 5, 2};

      t1 = Queens.isSolution(A1);
      t2 = Queens.isSolution(A2);
      System.out.println("isSolution() test1: " + (!t1 ? "PASSED" : "FAILED"));
      System.out.println("isSolution() test2: " + (t2 ? "PASSED" : "FAILED"));
   }

   static boolean arrayEquals(int A[], int B[]) {
      if (A.length != B.length) return false;
      for (int i = 1; i < A.length; i++) // ignore index 0
         if (A[i] != B[i]) return false;
      return true;
   }
}
