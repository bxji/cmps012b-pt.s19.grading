# cmps012b-pt.s19/pa2

The following is a set of performance tests to run on your N-Queens program.
We have made this available to you to check your work before making your final
submission.

General performance tests 1-8 give your program 0.5 seconds.
Test 9 give you 2 seconds (because they test 10-Queens). Test 10 gives you 5 seconds (12-Queens).

## Warning

This script should confirm all components of your performance based grade but
doesn't confirm every component of your grade for things like comment blocks,
but it'll definitely get you the majority points.

## Installation

Run the following in your working directory (the directory you wrote your code
in) to download the test script.

```bash
curl https://raw.githubusercontent.com/bxji/cmps012b-pt.s19.grading/master/pa2/pa2.sh > pa2.sh
chmod +x pa2.sh
```
^^^ To be fixed later~

# Usage

After downloading the script, run it with the following command:

```bash
./pa2.sh
```
^^ to be fixed later too

It will print out the difference between your output and the correct output,
using the `diff` command. Lack of any output between the set of "=========="
means that your program is running correctly.

> WARNING: From now on, you will not get a perfect score if there is any output
between any of the pairs of equal signs or if any unit tests fail.

## Removal

You can delete the test and all other downloaded/generated files with:
`rm -f pa5.sh diff*.txt out*.txt model-out*.txt QueensUnitTests.java`.
