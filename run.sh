./Subset 4 2 > 1.out 
./Subset 10 3 > 2.out
./Subset 20 15 > 3.out
./Subset 9 4 > 4.out
./Subset 5 3 > 5.out
./Subset 4 0 > 6.out 
./Subset 0 0 > 7.out
./Subset 4 4 > 8.out

diff -ywBZbi --suppress-common-lines 1.out std-1.out
diff -ywBZbi --suppress-common-lines 2.out std-2.out
diff -ywBZbi --suppress-common-lines 3.out std-3.out
diff -ywBZbi --suppress-common-lines 4.out std-4.out
diff -ywBZbi --suppress-common-lines 5.out std-5.out
diff -ywBZbi --suppress-common-lines 6.out std-6.out
diff -ywBZbi --suppress-common-lines 7.out std-7.out
diff -ywBZbi --suppress-common-lines 8.out std-8.out
