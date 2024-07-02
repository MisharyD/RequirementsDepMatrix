# Requirements Dependancy Matrix
Traceability links can be used to perform impact analysis. This will allow engineers to
answer the question: ”if a given requirement is changed, what other requirements are
impacted?”
Each requirement is given a unique identifier (a string of characters with no spaces).
Then, for each requirement, the list of requirements on which it depends directly is given.
This can be represented textually in a line. The first word is the identifier of a requirement,
and the rest of the words are the identifiers of the requirements on which it depends directly.
For example, the line
Rx Ry Rz
means that the requirement whose identifier is Rx depends on the requirements whose
identifiers are Ry and Rz.
Example of full requirements:
R2 R1
R3 R1
R5 R2 R4
R6 R5 R3
R7 R3
R8 R6 R2 R7
0

The program reads the dependencies between requirements as
shown by the example above (the zero at the end indicates the end of the
input). 
Then the program displays the matrix representation of the dependencies.
