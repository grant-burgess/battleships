# Ship Shooting Game

The Ship Shooting Game is a file based game that takes an input file and produces an output file of the result.


## What was used to ensure quality?
* The project was developed using TDD
* [SonarQube](https://www.sonarqube.org/) was used to discover bugs, vulnerabilities and code smells.


## Design
Class diagram
![Class diagram]([https://github.com/grant-burgess/battleships/blob/master/img/class-diagram.png)


## Project dependencies
This project uses Java 8. Tests were written with JUnit4


## Running the application
To build the project, run the following command:

```shell
gradlew clean build
```

There is an `input.txt` file included in the root of the project, edit this to change the output.

```
10                  # board size
(0, 0, N) (9, 2, E) # ships, initial coordinates followed by the ships orientation (north, east, south, west)
(0, 0) MRMLMM       # coordinates of ship to move followed by move instructions
(9, 2)              # ship to shoot at
```


To run the application, from the root of the project run:
```shell
java -jar build/libs/battleships-1.0-SNAPSHOT.jar
```

Inspect the `output.txt` file for the result

e.g
```
(1, 3, N) 
(9, 2, E) SUNK
```
