# ZipCode API
This project was built with **Java 8**, **Spring Boot** and **Spring Data JPA**


## Requirements
* Gradle 2.12+
* Java 8+
* [Project Lombok](https://projectlombok.org/features/index.html)

## Building and Running

To build the API just run:
```
$ ./gradlew build
```
To run, just do the following:
:
```
$ cd build/libs
$ java -jar spotippos-real-0.0.1.jar
```


## Usage
With the API running you can test the end-points using curl or something else:

The API consumes and produces JSON.

## end-points
- [GET] /address/{zipcode} - receive a zipcode without '-';
- [GET] /address?id={id} - Get an Address by its ID;
- [PUT] /address/ - Update the address;
- [POST] /address/ - Create new address;
- [DELETE] /address/{id} - Remove address by its ID

The Following filds are required:
**street** | **number** | **zipcode** | **city** | **state**

Get an address by its zipcode
```
curl -H "Content-Type: application/json" http://localhost:8080/address/01415000
```
Response:
```
{
  "id": 1,
  "street": "Rua Haddock Lobo",
  "number": 1738,
  "zipcode": "01415000",
  "city": "São Paulo",
  "state": "SP",
  "district": "Jardim Paulista",
  "complement": null
}

```


To get an Address by its ID:
```
curl -H "Content-Type: application/json" http://localhost:8080/address?id=1
```
Reponse:
```
{
  "id": 1,
  "street": "Rua Haddock Lobo",
  "number": 1738,
  "zipcode": "01415000",
  "city": "São Paulo",
  "state": "SP",
  "district": "Jardim Paulista",
  "complement": null
}

```

To delete an Address::
```
curl -X DELETE -H "Content-Type: application/json" http://localhost:8080/address/2 
```

To save an Address::
```
curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/address/ -d '{"street": "Rua Turmalina", "number":1000, "zipcode":"06410020", "city":"Barueri", "state":"Sao Paulo"}'
```
Response:
```
{
  "id": 23,
  "street": "Rua Turmalina",
  "number": 1000,
  "zipcode": "06410020",
  "city": "Barueri",
  "state": "Sao Paulo",
  "district": null,
  "complement": null
}
```

To update an Address::
```
curl -X PUT -H "Content-Type:application/json" http://localhost:8080/address/ -d '{"id": 23, "street": "Rua Turmalina", "number": 1000, "zipcode": "06410020", "city": "Barueri", "state": "Sao Paulo", "district": "Jardim dos Camargos", "complement": null }'
```
Response:
```
{
  "id": 23,
  "street": "Rua Turmalina",
  "number": 1000,
  "zipcode": "06410020",
  "city": "Barueri",
  "state": "Sao Paulo",
  "district": "Jardim dos Camargos",
  "complement": null
}
```
## Testing

To run the tests:
```
$ ./gradlew test
```

