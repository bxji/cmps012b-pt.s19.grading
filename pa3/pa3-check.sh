#!/usr/bin/bash

SRCDIR=https://raw.githubusercontent.com/legendddhgf/cmps012b-pt.w18.grading/master/pa3

if [ ! -e backup ]; then
  echo "WARNING: a backup has been created for you in the \"backup\" folder"
  mkdir backup
fi

cp *.java Makefile backup   # copy all files of importance into backup

curl $SRCDIR/ModelDictionaryTest.java > ModelDictionaryTest.java

echo ""
echo ""

make

if [ ! -e DictionaryClient ] || [ ! -x DictionaryClient ]; then # exist and executable
  echo ""
  echo "Makefile doesn't correctly create Executable!!!"
  echo ""
  rm -f *.class DictionaryClient
fi

echo ""
echo ""

make clean

if [ -e DictionaryClient ] || [ -e *.class ]; then
  echo "WARNING: Makefile didn't successfully clean all files"
fi

echo ""
echo ""

echo "Press Enter To Continue with DictionaryTest Results"
read verbose

javac ModelDictionaryTest.java Dictionary*.java >> garbage &>> garbage
cat garbage

echo ""
echo ""

timeout 5 java ModelDictionaryTest -v > DictionaryTest-out.txt &>> DictionaryTest-out.txt
cat DictionaryTest-out.txt

echo ""
echo ""

rm -f *out.txt

rm -f *.class ModelDictionaryTest.java garbage*
