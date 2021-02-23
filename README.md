# MAPA Scrapper API

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ or read instructions bellow.

## Informações gerais

O projeto por default sobe na porta 5000. Ou seja, para acessar as APIs, basta usar o endereço `http://localhost:5000` antes de acessar as APIs.

As APIs disponíveis são:
- `/menus`: parâmetros de pesquisa disponíveis para a API de consulta
- `/consultar`: API de consulta dos documentos disponíveis no site (retorna os nomes dos documentos e os índices)
- `/consultar/{indice}`: API de retorno do conteúdo do documento em formato HTML conforme o índice fornecido

## Running the application in dev mode

First of all, you should install Java Development Kit (JDK) version 11. You can download it here: https://adoptopenjdk.net/releases.html.

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `scrapper-api-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/scrapper-api-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/scrapper-api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

# RESTEasy JSON serialisation using Jackson

<p>This example demonstrate RESTEasy JSON serialisation by letting you list, add and remove quark types from a list.</p>
<p><b>Quarked!</b></p>

Guide: https://quarkus.io/guides/rest-json
