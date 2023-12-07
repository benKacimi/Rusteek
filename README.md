# INTERPRETER

Provide an engine the analyse and execute a simple langage.

Exemple : @aFunction(${aVar})aTexte
In this exemple the analysze (tockenizer or lexer) that the instruction is composed in 2 parts : 
- a function named aFunction that take one parameter. The parameter in this case is a variable named aVar
- a literal (aText

### Required configuration ###
* JDK 21
* Maven 3.6.3
* Git

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=benKacimi_function-engine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=benKacimi_function-engine)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=benKacimi_function-engine&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=benKacimi_function-engine)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=benKacimi_function-engine&metric=bugs)](https://sonarcloud.io/summary/new_code?id=benKacimi_function-engine)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=benKacimi_function-engine&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=benKacimi_function-engine)

### Technologies ####
* Language programming: Java 21
* UnitTests: [JUnit](https://junit.org/junit5/)
* Code generation: [Lombok](https://projectlombok.org)
* CI : [Travis CI](https://travis-ci.com)
* Code analysis: [sonarcloud](https://sonarcloud.io)

### Compilation ###
```
./mvn clean install
```
