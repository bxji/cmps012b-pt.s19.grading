# cmps012b-pt.w18/pa4

The following is a set of performance tests to run on your Simulation program.

## Installation

Run the following in your working directory (the directory you wrote your code
in) to get the test script and example input files:

```bash
curl https://raw.githubusercontent.com/legendddhgf/cmps012b-pt.w18.grading/master/pa4/pa4.sh > pa4.sh
chmod +x pa4.sh
```

## Usage

After installation, you can run the script with this line:

```bash
./pa4.sh
```

The tests will first print out the difference between your output and the
correct output, using the `diff -bBwu` command. Lack of any output between the
set of "==========" means that your program is running correctly. Any lines
prefixed with `-` are from your own output, and are incorrect. Any lines
prefixed with `+` are from the correct output, and are missing in your output.

Next the tests will include x unit tests from ModelQueueTest.java which will
provide information on which tests were passed / whether you had an exception
in your code / your final score for the unit tests.

## Removal

Everything that the script generates or downloads should automatically be
deleted by the script so if you would like to delete all files related to the
script, use the following. Note that the script leaves the backup folder for you
and you may choose to delete it if you like.

```bash
rm -rf pa4.sh backup
```
