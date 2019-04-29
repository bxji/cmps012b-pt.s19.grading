#!/bin/bash
# cmps012b-pt.s19 grading
# usage: pa2.sh
# (run within your pa2 directory to test your code)

SRCDIR=https://raw.githubusercontent.com/bxji/cmps012b-pt.s19.grading/tree/master/pa2/

if [ ! -e backup ]; then
  echo "WARNING: a backup has been created for you in the \"backup\" folder"
  mkdir backup
fi

cp *.java Makefile backup   # copy all files of importance into backup

for num in 1 2 3 4 5 6 7 8 9 10
do
   curl $SRCDIR/model-out$num.txt > model-out$num.txt
done

curl $SRCDIR/ModelQueensTest.java > ModelQueensTest.java
echo ""
echo ""

make

if [ ! -e Queens ] || [ ! -x Queens ]; then # exist and executable
   echo ""
   echo "Makefile doesn't correctly create Queens!!!"
   echo ""
   rm -f *.class
   javac Queens.java
   echo "Main-class: Queens" > Manifest
   jar cvfm Queens Manifest *.class
   rm Manifest
   chmod +x Queens
fi


TEST1=""
TEST2="x"
TEST3="-v"
TEST4="-v x"
TEST5="5"
TEST6="-v 5"
TEST7="8"
TEST8="9"
TEST9="10"
# Fix n = 15 because they were a pain to grade (~1:30 mins wait)
# we will see how 5 seconds fair
TEST10="12"

# Run tests
echo "If nothing between '=' signs, then test is passed::"
echo "Test 1:"
echo "=========="
timeout 0.5 Queens $TEST1 >& out1.txt
diff -bBwu out1.txt model-out1.txt > diff1.txt
cat diff1.txt
echo "=========="

echo "Test 2:"
echo "=========="
timeout 0.5 Queens $TEST2 >& out2.txt
diff -bBwu out2.txt model-out2.txt > diff2.txt
cat diff2.txt
echo "=========="

echo "Test 3:"
echo "=========="
timeout 0.5 Queens $TEST3 >& out3.txt
diff -bBwu out3.txt model-out3.txt > diff3.txt
cat diff3.txt
echo "=========="

echo "Test 4:"
echo "=========="
timeout 0.5 Queens $TEST4 >& out4.txt
diff -bBwu out4.txt model-out4.txt > diff4.txt
cat diff4.txt
echo "=========="

echo "Test 5:"
echo "=========="
timeout 0.5 Queens $TEST5 > out5.txt
diff -bBwu out5.txt model-out5.txt > diff5.txt
cat diff5.txt
echo "=========="

echo "Test 6:"
echo "=========="
timeout 0.5 Queens $TEST6 > out6.txt
diff -bBwu out6.txt model-out6.txt > diff6.txt
cat diff6.txt
echo "=========="

echo "Test 7:"
echo "=========="
timeout 0.5 Queens $TEST7 > out7.txt
diff -bBwu out7.txt model-out7.txt > diff7.txt
cat diff7.txt
echo "=========="

echo "Test 8:"
echo "=========="
timeout 0.5 Queens $TEST8 > out8.txt
diff -bBwu out8.txt model-out8.txt > diff8.txt
cat diff8.txt
echo "=========="

echo "Test 9:"
echo "=========="
timeout 2 Queens $TEST9 > out9.txt
diff -bBwu out9.txt model-out9.txt > diff9.txt
cat diff9.txt
echo "=========="

echo "Test 10:"
echo "=========="
# Let's try 5 for now
timeout 5 Queens $TEST10 > out10.txt
diff -bBwu out10.txt model-out10.txt > diff10.txt
cat diff10.txt
echo "=========="


make clean

echo ""

if [ -e Queens ] || [ -e *.class ]; then
   echo "WARNING: Makefile didn't successfully clean all files"
   rm -f Queens *.class
fi

echo ""
echo ""

echo "Press Enter To Continue with QueensTest Results"
read verbose

javac *.java >> garbage &>> garbage
cat garbage

timeout 5 java ModelQueensTest -v > QueensTest-out.txt &>> QueensTest-out.txt
cat QueensTest-out.txt

#rm -f *out[0-9].txt

rm -f *.class ModelQueensTest* garbage*
