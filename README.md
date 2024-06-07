# cathay-bank-test
Cathay United Bank - JAVA engineer Interview

## Project Structure


In the project require Java and Docker Cointainer for running .

`/src/*` structure follows default Java structure.

## Development

To start your application, run:

```
./mvnw
```

To make currency service maintenance, run:

```
POST http://localhost:8080/api/maintenance/currency
```
Or un-maintain service:
```
PATCH http://localhost:8080/api/maintenance/currency
```

## Swagger

http://localhost:8080/swagger-ui/index.html
