This calculator I wrote in the subject Formalne jazyky.
Here I created a calculator with its own grammar.
I also wrote tests to check the functionality of the calculator

It's my calculator's grammar:

A -> B [("^" | "POWER") A]
B -> C {("+" | "SUM" | "-" | "YY") C}
C -> D [("*" | "***") C]
D -> ["-"] E
E -> NUMBER | ( "("A")" ) | ( "{"A"}" )