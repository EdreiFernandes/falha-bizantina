# falha-bizantina

Projeto de Sistemas distribuídos com o objetivo de utilizar sockets para efetuar comunicação cliente-servidor em uma aplicação que represente o tolerância de falhas bizantinas.

## Gerar .jar

### manifest exemplo

- Manifest-Version: 1.0
- Class-Path: .
- Main-Class: app.App

### Comandos

- javac -d ./bin src/app/\*.java

- javac -cp ./bin src/app/\*.java (caso tenha dependências)

- jar -cvmf manifest.txt App.jar app/\*.class

- java -jar App.jar
