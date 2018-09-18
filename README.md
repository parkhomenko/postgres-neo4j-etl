## Create a jar
```shell
./mvnw clean package
```

## Start data uploader
```shell
java -jar ./target/etl-0.0.1-SNAPSHOT.jar \
    --spring.postgres.datasource.jdbcUrl=jdbc:postgresql://localhost:5432/postgres \
    --spring.postgres.datasource.username=secret \
    --spring.postgres.datasource.password=secret \
    --spring.neo4j.datasource.jdbcUrl=jdbc:neo4j:bolt://localhost/ \
    --spring.neo4j.datasource.username=neo4j \
    --spring.neo4j.datasource.password=secret \
    --com.data.etl.batchSize=1000
```
