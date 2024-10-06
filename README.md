# RUSTEEK

RUSTEEK is a an open-source programming language. It's extremely simple because it has just one purpose : to construct strings of characters.
Main caracteristics :
* Written in java
* It's a procedural programming language (no object, no structure)
* It has been simplified to the extreme without conditionnal instruction (if, then, else...), whithout loop instruction (for, do, while...), without arithmetical operation (+, -, *, /, %...)
* The learning takes place almost instantaneously 
* 0 compilation error, 0 execution error ! (that's not a promise but an intention - feel free to contact me if you find any issue)
* It came with a set of core behavior and the model allow to add your own custom functions.

So why this name ? Rusteek ? *Basic* was already taken, *Robust* was OK but a bit too pretentious, so I throught rustic was a perfect definition of this project (move rustic to rusteek because it was the only rustic variation available) 

## What does it do ?
Rusteek will help you if you had to display a sentence containing data stored somewhere and data resulting from processing. 
Let's take the BMI usecase (body mass index) to illustrate what can be done with Rusteek.
The BMI is calculated with your height (H) and your weight (W) and the formula is : BMI = W / (H*H)
That said, our need is to display the sentence : 

> "Hello ! your weight : **[WEIGHT]** kg, your height : **[HEIGHT]** meter so your BMI is **[BMI CALCULATION]**"

[WEIGHT], [HEIGHT]  are variables which we assume pre-exist somewhere and are accessible to us\
[BMI CALCULATION] is the result of a calculation that take [WEIGHT] and [HEIGHT] as input.

In Rusteek, the sentence  would be writtent like this :
 
> Hello ! your weight : **${weight}** kg, your height : **${height}** meter so your BMI is **@calculateBMI**(**${weight}**, **${height}**)

### Variable
In the world of "Configuration software" you can find a lot of tools to manage "placeholder". 
Small definition of "placeholder" : this is a piece of a sentence which will be replaced in due time by a specific value.\
Exemple : <http://${server.name}/uri>

In this exemple, we want to set a value for the "server.name" parameter but as this may depend on a number of factor, we dont know yet this value. We use the special syntax - ${...} -  to indicate that it is a VARIALBLE that must be replaced at the right time and in the right context.

That's pretty classic and if you're already familiar with the placeholder principle, good news : Rusteek work exactly the same. We won't talk about a Placeholder but about a Variable instead. 

### Function


#### Core Function
Remember : a function return always a string..\ The function can take 0 to n parameters.\ Each parameter is a string.\
Here is a list of the function currently available in the core language.

Function                                                       | Description
---------------------------------------------------------------| -------------------------------
**uuidGeneraror.generateUUID()**                                 | return a [UUID](https://www.ietf.org/rfc/rfc4122.txt) (Universally Unique Identifier) version 4
**uuidGeneraror.anonymiseUUID(input [stringToReplaceByAnUUID])**       | return a UUID (version 4) and keep in memory the couple [stringToReplaceByAnUUID, newUUIDgenerated] in order to retrieve 
**hash.SHA3256(input [stringToHash])**                                     | return a SHA3-256 hash of the input
**hash.SHA3256(input [stringToHash], salt [stringSalt])**                    | return a SHA3-256 hash of the input with the salt



## Authors
- [ben kacimi](https://github.com/benKacimi)
<a target="_blank" href="https://www.linkedin.com/in/ben-kacimi/"><img height="20" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a>

## Required configuration ###
* JDK 21 - Temurin distribution
* Maven 3.6.3
* Git

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=benKacimi_Rusteek&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=benKacimi_Rusteek)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=benKacimi_Rusteek&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=benKacimi_Rusteek)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=benKacimi_function-engine&metric=bugs)](https://sonarcloud.io/summary/new_code?id=benKacimi_Rusteek)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=benKacimi_Rusteek&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=benKacimi_Rusteek)
[![Known Vulnerabilities](https://snyk.io/test/github/benKacimi/Rusteek/badge.svg)](https://snyk.io/test/github/benKacimi/Rusteek)

## Technologies ####
* Language programming: Java 21
* UnitTests: [JUnit](https://junit.org/junit5/)
* Code generation: [Lombok](https://projectlombok.org)
* Github Action : [Travis CI](https://travis-ci.com)
* Code analysis: [sonarcloud](https://sonarcloud.io)



## Tech Stack
benKacimi/interpreter is built on the following main stack:

- <img width='25' height='25' src='https://img.stackshare.io/service/995/K85ZWV2F.png' alt='Java'/> [Java](https://www.java.com) – Languages
- <img width='25' height='25' src='https://img.stackshare.io/service/11563/actions.png' alt='GitHub Actions'/> [GitHub Actions](https://github.com/features/actions) – Continuous Integration
- <img width='25' height='25' src='https://img.stackshare.io/service/2020/874086.png' alt='JUnit'/> [JUnit](http://junit.org/) – Testing Frameworks
- <img width='25' height='25' src='https://img.stackshare.io/service/2805/05518ecaa42841e834421e9d6987b04f_400x400.png' alt='SLF4J'/> [SLF4J](http://slf4j.org/) – Log Management
- <img width='25' height='25' src='https://pitest.org/images/pit-black-150x152.png' alt='Pitest'/> [Pitest](https://pitest.org/) - Mutation testing


## Compilation ###
```
./mvn clean install
```
