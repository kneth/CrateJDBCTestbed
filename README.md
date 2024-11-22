# Crate JDBC Testbed

A simple Java application to experiment with CrateDB and JDBC.

In `app/build.gradle` you can change the JDBC driver to use.

To run the application, you need to start CrateDB:

```shell
  docker run --publish=4200:4200 --publish=5432:5432 --env CRATE_HEAP_SIZE=1g --pull=always crate
```

and `./gradlew run` will run the application.