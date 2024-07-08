# Requirements Dependancy Matrix
Traceability links can be used to perform impact analysis. This will allow engineers to
answer the question: ”if a given requirement is changed, what other requirements are
impacted?” <br />
Each requirement is given a unique identifier (a string of characters with no spaces). <br />
Then, for each requirement, the list of requirements on which it depends directly is given. <br />
This can be represented textually in a line. The first word is the identifier of a requirement, <br />
and the rest of the words are the identifiers of the requirements on which it depends directly. <br />
## Usage: 
the line <br /> 
Rx Ry Rz <br />
means that the requirement whose identifier is Rx depends on the requirements whose
identifiers are Ry and Rz. <br />
Example of full requirements: <br />
R2 R1 <br />
R3 R1 <br />
R5 R2 R4 <br />
R6 R5 R3 <br />
R7 R3 <br />
R8 R6 R2 R7 <br />
0 <br />

The program reads the dependencies between requirements as shown by the example above (the zero at the end indicates the end of the input).  <br />
Then the program displays the matrix representation of the dependencies.
