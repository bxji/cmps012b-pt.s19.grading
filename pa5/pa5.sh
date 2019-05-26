#!/usr/bin/bash
# cmps012b-pt.w18 grading
# usage: pa5.sh
# (run within your pa5 directory to test your code)

SRCDIR=https://raw.githubusercontent.com/legendddhgf/cmps012b-pt.w18.grading/master/pa5
EXE1="pa5-make-check.sh"
EXE2="pa5-dictionary-check.sh"

curl $SRCDIR/$EXE1 > $EXE1
curl $SRCDIR/$EXE2 > $EXE2
chmod +x $EXE1
chmod +x $EXE2
./$EXE1
./$EXE2
rm -f $EXE1
rm -f $EXE2
