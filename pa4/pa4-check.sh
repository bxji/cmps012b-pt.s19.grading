#!/usr/bin/bash

SRCDIR=https://raw.githubusercontent.com/legendddhgf/cmps012b-pt.w18.grading/master/pa4
NUMTESTS=11
PNTSPERTEST=5
let MAXPTS=$NUMTESTS*$PNTSPERTEST

if [ ! -e backup ]; then
  echo "WARNING: a backup has been created for you in the \"backup\" folder"
  mkdir backup
fi

cp *.java Makefile backup   # copy all files of importance into backup

for NUM in $(seq 1 $NUMTESTS); do
  curl $SRCDIR/infile$NUM.txt > infile$NUM.txt
  curl $SRCDIR/model-rpt$NUM.txt > model-rpt$NUM.txt
  curl $SRCDIR/model-trc$NUM.txt > model-trc$NUM.txt
done

curl $SRCDIR/ModelQueueTest.java > ModelQueueTest.java

echo ""
echo ""

make

if [ ! -e Simulation ] || [ ! -x Simulation ]; then # exist and executable
  echo ""
  echo "Makefile doesn't correctly create Executable!!!"
  echo ""
  rm -f *.class
  javac -Xlint *.java
  echo "Main-class: Simulation" > Manifest
  jar cvfm Simulation Manifest *.class
  rm Manifest
  chmod +x Simulation
fi

echo ""
echo ""

simulationtestspassed=$(expr 0)
echo "Please be warned that the following tests discard all output to stdout/stderr"
echo "Simulation tests: If nothing between '=' signs, then test is passed"
echo "Press enter to continue"
read verbose
for NUM in $(seq 1 $NUMTESTS); do
  if [ $NUM -eq 11 ]; then
    # students never cease to dissapoint me
    let simulationtestspassed+=1
    continue
  fi
  rm -f outfile$NUM.txt
  timeout 5 Simulation infile$NUM.txt &> garbage >> garbage
  diff -bBwu infile$NUM.txt.rpt model-rpt$NUM.txt &> diff-rpt$NUM.txt >> diff-rpt$NUM.txt
  diff -bBwu infile$NUM.txt.trc model-trc$NUM.txt &> diff-trc$NUM.txt >> diff-trc$NUM.txt
  echo "Report $NUM Test:"
  echo "=========="
  cat diff-rpt$NUM.txt
  echo "=========="
  echo "Trace $NUM Test:"
  echo "=========="
  cat diff-trc$NUM.txt
  echo "=========="

  cat diff-rpt$NUM.txt diff-trc$NUM.txt > diff$NUM.txt

  if [ -e diff$NUM.txt ] && [[ ! -s diff$NUM.txt ]]; then
    let simulationtestspassed+=1
  fi

  rm -f infile$NUM.txt *.rpt *.trc diff*$NUM* model-rpt$NUM.txt model-trc$NUM.txt

done

echo ""
echo ""

let simulationtestpoints=$PNTSPERTEST*$simulationtestspassed

echo "Passed $simulationtestspassed / $NUMTESTS Simulation tests"
echo "This gives a total of $simulationtestpoints / $MAXPTS points"

echo ""
echo ""

make clean

if [ -e Simulation ] || [ -e *.class ]; then
  echo "WARNING: Makefile didn't successfully clean all files"
  rm -f Simulation *.class
fi

echo ""
echo ""

echo "Press Enter To Continue with QueueTest Results"
read verbose

javac *.java >> garbage &>> garbage
cat garbage

timeout 5 java ModelQueueTest -v > QueueTest-out.txt &>> QueueTest-out.txt
cat QueueTest-out.txt

rm -f *out.txt

rm -f *.class ModelQueueTest* garbage*
