#!/usr/bin/bash
# cmps012b-pt.s19 grading
# usage: pa1.sh
# (run within your pa1 directory to test your code)

#SRCDIR=https://raw.githubusercontent.com/bxji/cmps012b-pt.s19.grading/master/pa1/
SRCDIR=/afs/cats.ucsc.edu/users/i/zecheng/cs12b/pa1
EXE="pa1-check.sh"
SCRIPT=$(mktemp)

#curl $SRCDIR/$EXE > $EXE
cp $SRCDIR/pa1-check.sh ./pa1-check.sh
chmod +x $EXE
./$EXE
rm -f $EXE
