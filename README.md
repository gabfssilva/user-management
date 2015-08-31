# user-management


###Cloning the project

```
git clone https://github.com/gabfssilva/user-management.git
cd user-management
```

###Build
```
./gradlew build
```

###Running the tests
```
./gradlew test --info
```

###Integration test class
```
br.com.user.management.api.v1.UserEndpointITest
```

###Used tools

####Gradle (build tool)
#####JAX-RS (Jersey)
#####CDI (Weld)
#####Undertow (embedded servlet container)
#####Morphia (Official MongoDB Object Document Mapper)
#####RestAssured for testing


###API V1 - Main services

####User creation


Request:
```
HTTP/1.1 POST
Location: http://localhost:8941/user-management/api/v1/users
Content-Type: application/json

{
   "name":"Gabriel",
   "address":{
      "zipcode":"01304000",
      "street":"Rua Augusta",
      "number":404,
      "complementary_address":"apto X",
      "district":"Consolacao",
      "city":"Sao Paulo",
      "state":"SP"
  }
}
```

Response:
```
HTTP/1.1 201 Created
Connection: keep-alive
Location: http://localhost:8941/user-management/api/v1/users/55e4ae2140c8a6adaaf7d5fd
Content-Length: 249
Content-Type: application/json
Date: Mon, 31 Aug 2015 19:42:25 GMT

{
    "item": {
        "id": "55e4ae2140c8a6adaaf7d5fd",
        "name": "Gabriel",
        "address": {
            "id": "55e4ae2140c8a6adaaf7d5fd",
            "zipcode": "01304000",
            "street": "Rua Augusta",
            "number": 404,
            "complementary_address": "apto X",
            "district": "Consolacao",
            "city": "Sao Paulo",
            "state": "SP"
        }
    }
}
```

####Update user

Request:
```
HTTP/1.1 PUT
Location: http://localhost:8941/user-management/api/v1/users/55e4ae2140c8a6adaaf7d5fd
Content-Type: application/json

{
   "name":"New gabriel",
   "address":{
      "id":"55e4ae2140c8a6adaaf7d5fd",
      "zipcode":"01304000",
      "street":"Rua Augusta",
      "number":404,
      "complementary_address":"apto X",
      "district":"Consolacao",
      "city":"Sao Paulo",
      "state":"SP"
  }
}
```

Response:
```
HTTP/1.1 200 OK
Connection: keep-alive
Location: http://localhost:8941/user-management/api/v1/users/55e4ae2140c8a6adaaf7d5fd
Content-Length: 249
Content-Type: application/json
Date: Mon, 31 Aug 2015 19:42:25 GMT

{
    "item": {
        "id": "55e4ae2140c8a6adaaf7d5fd",
        "name": "New gabriel",
        "address": {
            "id": "55e4ae2140c8a6adaaf7d5fd",
            "zipcode": "01304000",
            "street": "Rua Augusta",
            "number": 404,
            "complementary_address": "apto X",
            "district": "Consolacao",
            "city": "Sao Paulo",
            "state": "SP"
        }
    }
}
```

####User search

Request

```
HTTP/1.1 GET http://localhost:8941/user-management/api/v1/users/55e4ae2140c8a6adaaf7d5fd
```

Response

```
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 249
Content-Type: application/json
Date: Mon, 31 Aug 2015 19:42:26 GMT

{
    "item": {
        "id": "55e4ae2140c8a6adaaf7d5fe",
        "name": "Gabriel",
        "address": {
            "id": "55e4ae2140c8a6adaaf7d5fe",
            "zipcode": "01304000",
            "street": "Rua Augusta",
            "number": 404,
            "complementary_address": "apto X",
            "district": "Consolacao",
            "city": "Sao Paulo",
            "state": "SP"
        }
    }
}
```

####Create address for an existent user (if the user does not have an address)

Request

```
HTTP/1.1 POST
Location: http://localhost:8941/user-management/api/v1/users/55e4ae2340c8a6adaaf7d603/address
Content-Type: application/json

{
   "zipcode":"01304000",
   "street":"Rua Augusta",
   "number":404,
   "complementary_address":"apto X",
   "district":"Consolacao",
   "city":"Sao Paulo",
   "state":"SP"
}
```

Response

```
HTTP/1.1 201 Created
Connection: keep-alive
Content-Length: 188
Content-Type: application/json
Date: Mon, 31 Aug 2015 19:42:27 GMT

{
    "item": {
        "id": "55e4ae2340c8a6adaaf7d603",
        "zipcode": "01304000",
        "street": "Rua Augusta",
        "number": 404,
        "complementary_address": "apto X",
        "district": "Consolacao",
        "city": "Sao Paulo",
        "state": "SP"
    }
}
```

####Update user address
Request

```
HTTP/1.1 PUT
Location: http://localhost:8941/user-management/api/v1/users/55e4ae2340c8a6adaaf7d603/address
Content-Type: application/json

{
   "zipcode":"01304001",
   "street":"New Street",
   "number":404,
   "complementary_address":"apto X",
   "district":"Consolacao",
   "city":"Sao Paulo",
   "state":"SP"
}
```

Response

```
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 188
Content-Type: application/json
Date: Mon, 31 Aug 2015 19:42:27 GMT

{
    "item": {
        "id": "55e4ae2340c8a6adaaf7d603",
        "zipcode": "01304001",
        "street": "New Street",
        "number": 404,
        "complementary_address": "apto X",
        "district": "Consolacao",
        "city": "Sao Paulo",
        "state": "SP"
    }
}
```


####User address search

Request

```
HTTP/1.1 GET http://localhost:8941/user-management/api/v1/users/55e4ae2340c8a6adaaf7d603/address
```

Response

```
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 188
Content-Type: application/json
Date: Mon, 31 Aug 2015 19:42:27 GMT

{
    "item": {
        "id": "55e4ae2340c8a6adaaf7d603",
        "zipcode": "01304001",
        "street": "New Street",
        "number": 404,
        "complementary_address": "apto X",
        "district": "Consolacao",
        "city": "Sao Paulo",
        "state": "SP"
    }
}
```

####Delete address

Request

```
HTTP/1.1 DELETE http://localhost:8941/user-management/api/v1/users/55e4ae2340c8a6adaaf7d603/address
```

Response

```
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 0
```


####Delete user

Request

```
HTTP/1.1 DELETE http://localhost:8941/user-management/api/v1/users/55e4ae2340c8a6adaaf7d603
```

Response

```
HTTP/1.1 200 OK
Connection: keep-alive
Content-Length: 0
```
