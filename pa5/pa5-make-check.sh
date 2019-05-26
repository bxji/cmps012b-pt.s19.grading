#!/usr/bin/bash

SRCDIR=https://raw.githubusercontent.com/legendddhgf/cmps012b-pt.w18.grading/master/pa4


if [ ! -e backup ]; then
  echo "WARNING: a backup has been created for you in the \"backup\" folder"
  mkdir backup
fi

cp *.c *.h Makefile backup   # copy all files of importance into backup

curl $SRCDIR/ModelDictionaryTest.c > ModelDictionaryTest.c

echo ""
echo ""

make

if [ ! -e DictionaryClient ] || [ ! -x DictionaryClient ]; then # exist and executable
  echo ""
  echo "Makefile doesn't correctly create Executable!!!"
  echo ""
  rm -f *.o
  gcc -std=c99 -Wall *.c -o DictionaryClient >> garbage &>> garbage
  cat garbage
else
  echo ""
  echo "Makefile seems operational"
  echo ""
fi

echo ""
echo ""

make clean

if [ -e DictionaryClient ] || [ -e *.o ]; then
  echo "WARNING: Makefile didn't successfully clean all files"
  rm -f DictionaryClient *.class
fi

echo ""
echo ""

