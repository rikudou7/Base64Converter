# Base64Converter
Converts strings from ascii to base 64 encoding and vice versa.

This program was coded using java version 1.8.0_291 (otherwise known as Java 8).
This program can be compiled and run in the command line.
To compile, just enter the command:
> javac Base64Converter.java

Subsequently, to run, enter:
> java Base64Converter

From there, the program guides you step by step.

Usage Examples (The places that have the '>' marker are where you would type some input:



For Encoding to base 64:

> java Base64Converter
Do you want to encode from ascii to base 64 or decode from base 64 to ascii? (Enter 'A' or 'B' without the quotes.)
A. Encode
B. Decode
> A
What character are you using to represent the '+' character?
> +
What character are you using to represent the '/' character?
> /
What character are you using to represent the '=' character?
> =
Enter the string that you wish to encode:
> ANDE
The base 64 encoding of the string you entered is:
QU5ERQ==

For decoding from base 64:
> java Base64Converter
Do you want to encode from ascii to base 64 or decode from base 64 to ascii? (Enter 'A' or 'B' without the quotes.)
A. Encode
B. Decode
> B
What character are you using to represent the '+' character?
> +
What character are you using to represent the '/' character?
> /
What character are you using to represent the '=' character?
> =
Enter the string that you wish to encode:
> QU5ERQ==
The decoding of the string you entered is:
ANDE
In hex bytes this is:
414e4445
