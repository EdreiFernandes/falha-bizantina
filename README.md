# falha-bizantina

Projeto de Sistemas distribuídos com o objetivo de utilizar sockets para efetuar comunicação cliente-servidor em uma aplicação que represente o tolerância de falhas bizantinas.

## Gerar .jar

### manifest exemplo

- Manifest-Version: 1.0
- Class-Path: .
- Main-Class: app.App

### Compiling

- javac -d ./bin src/config/\*.java

- javac -cp ./bin src/helper/\*.java

- move .class from 'src/helper' to 'bin/helper'

- javac -d ./bin src/rsa/\*.java

- javac -cp ./bin src/app/\*.java

- move .class from 'src/app' to 'bin/app'

- create manifest.txt in bin folder

- jar -cvmf bin/manifest.txt bin/App.jar bin/app/\*.class

- java -jar bin/App.jar
