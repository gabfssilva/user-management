package br.com.user.management.api.v1;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.jayway.restassured.http.ContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletException;

import java.io.IOException;
import java.util.Random;

import static br.com.user.management.Application.startContainer;
import static br.com.user.management.Application.stopContainer;
import static br.com.user.management.test.producers.MongoDBStarter.startMongoDB;
import static br.com.user.management.test.producers.MongoDBStarter.stopMongoDB;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class UserEndpointITest {
    private static final int PORT = new Random(5000).nextInt(9999);
    private static WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8080));

    @BeforeClass
    public static void before() throws ServletException, IOException {
        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/cep-repository/api/v1/cep/01304000"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"item\": {\n" +
                                "    \"cep\": \"01304000\",\n" +
                                "    \"logradouro\": \"Rua Augusta\",\n" +
                                "    \"bairro\": \"Consolacao\",\n" +
                                "    \"cidade\": \"Sao Paulo\",\n" +
                                "    \"uf\": \"SP\"\n" +
                                "  }\n" +
                                "}")));

        wireMockServer.stubFor(get(urlEqualTo("/cep-repository/api/v1/cep/0130400000"))
                .willReturn(aResponse()
                        .withStatus(400)));

        startMongoDB();
        startContainer(PORT);
    }

    @AfterClass
    public static void after() throws IOException {
        wireMockServer.stop();
        stopMongoDB();
        stopContainer();
    }

    @Test
    public void assertThatUserCreationIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\"\n" +
                "}";

        given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"));
    }

    @Test
    public void assertThatUserUpdateIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\"\n" +
                "}";

        final String location = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .extract().header("Location");

        String anotherSimpleBody = "{\n" +
                "    \"name\": \"Another Gabriel\"\n" +
                "}";

        given().body(anotherSimpleBody)
                .contentType(ContentType.JSON)
                .when()
                .put(location)
                .then().log().everything(true)
                .assertThat()
                .statusCode(200)
                .and()
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Another Gabriel"));
    }

    @Test
    public void assertThatUserAddressCreationIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\"\n" +
                "}";

        final String location = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .extract().header("Location");


        String simpleBodyWithAddress = "{\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"district\": \"Consolacao\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }";

        final String addressLocation = given().body(simpleBodyWithAddress).contentType(ContentType.JSON)
                .when()
                .post(location + "/address")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", notNullValue())
                .and()
                .body("item.id", notNullValue())
                .and()
                .body("item.street", equalTo("Rua Augusta"))
                .and()
                .body("item.city", equalTo("Sao Paulo"))
                .and()
                .body("item.number", equalTo(404))
                .and()
                .body("item.district", equalTo("Consolacao"))
                .and()
                .body("item.zipcode", equalTo("01304000"))
                .and()
                .body("item.state", equalTo("SP"))
                .and()
                .body("item.complementary_address", equalTo("apto X"))
                .extract().header("Location");


        when().get(addressLocation)
                .then().log().everything(true)
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("item.id", notNullValue())
                .and()
                .body("item.street", equalTo("Rua Augusta"))
                .and()
                .body("item.city", equalTo("Sao Paulo"))
                .and()
                .body("item.number", equalTo(404))
                .and()
                .body("item.district", equalTo("Consolacao"))
                .and()
                .body("item.zipcode", equalTo("01304000"))
                .and()
                .body("item.state", equalTo("SP"))
                .and()
                .body("item.complementary_address", equalTo("apto X"));
    }

    @Test
    public void assertThatUserSearchingIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\"\n" +
                "}";

        final String header = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .extract().header("Location");

        when().get(header)
                .then().log().everything(true)
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"));
    }

    @Test
    public void assertThatUserAddressSearchingIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        final String header = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .extract().header("Location");

        when().get(header + "/address")
                .then().log().everything(true)
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("item.id", notNullValue())
                .and()
                .body("item.street", equalTo("Rua Augusta"))
                .and()
                .body("item.city", equalTo("Sao Paulo"))
                .and()
                .body("item.number", equalTo(404))
                .and()
                .body("item.district", equalTo("Consolacao"))
                .and()
                .body("item.zipcode", equalTo("01304000"))
                .and()
                .body("item.state", equalTo("SP"))
                .and()
                .body("item.complementary_address", equalTo("apto X"));
    }

    @Test
    public void assertThatUserWithAddressCreationIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .body("item.address", notNullValue())
                .and()
                .body("item.address.street", equalTo("Rua Augusta"))
                .and()
                .body("item.address.city", equalTo("Sao Paulo"))
                .and()
                .body("item.address.number", equalTo(404))
                .and()
                .body("item.address.district", equalTo("Consolacao"))
                .and()
                .body("item.address.zipcode", equalTo("01304000"))
                .and()
                .body("item.address.state", equalTo("SP"))
                .and()
                .body("item.address.complementary_address", equalTo("apto X"));
    }

    @Test
    public void assertThatUserAddressUpdatingIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        final String location = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .body("item.address", notNullValue())
                .and()
                .body("item.address.street", equalTo("Rua Augusta"))
                .and()
                .body("item.address.city", equalTo("Sao Paulo"))
                .and()
                .body("item.address.number", equalTo(404))
                .and()
                .body("item.address.district", equalTo("Consolacao"))
                .and()
                .body("item.address.zipcode", equalTo("01304000"))
                .and()
                .body("item.address.state", equalTo("SP"))
                .and()
                .body("item.address.complementary_address", equalTo("apto X"))
                .extract().header("Location");


        String simpleBodyWithAddress = "{\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"district\": \"Consolacao\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }";


        given().body(simpleBodyWithAddress)
                .and()
                .contentType(ContentType.JSON)
                .when()
                .put(location + "/address")
                .then()
                .log().everything(true)
                .and()
                .assertThat()
                .statusCode(200)
                .and()
                .body("item.id", notNullValue())
                .and()
                .body("item.street", equalTo("Rua Augusta"))
                .and()
                .body("item.city", equalTo("Sao Paulo"))
                .and()
                .body("item.number", equalTo(404))
                .and()
                .body("item.district", equalTo("Consolacao"))
                .and()
                .body("item.zipcode", equalTo("01304000"))
                .and()
                .body("item.state", equalTo("SP"))
                .and()
                .body("item.complementary_address", equalTo("apto X"));
    }

    @Test
    public void assertThatUserWithAddressSearchIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        final String header = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .body("item.address", notNullValue())
                .and()
                .body("item.address.street", equalTo("Rua Augusta"))
                .and()
                .body("item.address.city", equalTo("Sao Paulo"))
                .and()
                .body("item.address.number", equalTo(404))
                .and()
                .body("item.address.district", equalTo("Consolacao"))
                .and()
                .body("item.address.zipcode", equalTo("01304000"))
                .and()
                .body("item.address.state", equalTo("SP"))
                .and()
                .body("item.address.complementary_address", equalTo("apto X"))
                .and().extract().header("Location");

        when().get(header)
                .then().log().everything(true)
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .body("item.address", notNullValue())
                .and()
                .body("item.address.street", equalTo("Rua Augusta"))
                .and()
                .body("item.address.city", equalTo("Sao Paulo"))
                .and()
                .body("item.address.number", equalTo(404))
                .and()
                .body("item.address.district", equalTo("Consolacao"))
                .and()
                .body("item.address.zipcode", equalTo("01304000"))
                .and()
                .body("item.address.state", equalTo("SP"))
                .and()
                .body("item.address.complementary_address", equalTo("apto X"));
    }

    @Test
    public void assertThatUserDeletionIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        final String header = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .body("item.address", notNullValue())
                .and()
                .body("item.address.street", equalTo("Rua Augusta"))
                .and()
                .body("item.address.city", equalTo("Sao Paulo"))
                .and()
                .body("item.address.number", equalTo(404))
                .and()
                .body("item.address.district", equalTo("Consolacao"))
                .and()
                .body("item.address.zipcode", equalTo("01304000"))
                .and()
                .body("item.address.state", equalTo("SP"))
                .and()
                .body("item.address.complementary_address", equalTo("apto X"))
                .and().extract().header("Location");

        when().delete(header)
                .then().log().everything(true)
                .assertThat()
                .statusCode(200);

        when().get(header)
                .then().log().everything(true)
                .and().assertThat().statusCode(404);
    }


    @Test
    public void assertThatUserAddressDeletionIsWorking() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        final String header = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .body("item.address", notNullValue())
                .and()
                .body("item.address.street", equalTo("Rua Augusta"))
                .and()
                .body("item.address.city", equalTo("Sao Paulo"))
                .and()
                .body("item.address.number", equalTo(404))
                .and()
                .body("item.address.district", equalTo("Consolacao"))
                .and()
                .body("item.address.zipcode", equalTo("01304000"))
                .and()
                .body("item.address.state", equalTo("SP"))
                .and()
                .body("item.address.complementary_address", equalTo("apto X"))
                .and().extract().header("Location");

        when().delete(header + "/address")
                .then().log().everything(true)
                .assertThat()
                .statusCode(200);

        when().get(header)
                .then().log().everything(true)
                .and().assertThat().statusCode(200);

        when().get(header + "/address")
                .then().log().everything(true)
                .and().assertThat().statusCode(404);
    }

    @Test
    public void assertThatServerReturns400IfItGetsAnInvalidRequestOnUserCreation() {
        String simpleBody = "{}";

        given().body(simpleBody)
                .contentType(ContentType.JSON)
                .port(PORT)
                .baseUri("http://localhost")
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .and()
                .assertThat().statusCode(400);
    }

    @Test
    public void assertThatServerReturns400IfItGetsAnInvalidRequestOnUserAddressCreation() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\"\n" +
                "}";

        final String location = given()
                .body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and()
                .header("Location", containsString("http://localhost:" + PORT + "/user-management/api/v1/users"))
                .body("item.id", notNullValue())
                .and()
                .body("item.name", equalTo("Gabriel"))
                .and()
                .extract().header("Location");

        String addressBody = "{}";

        given().body(addressBody)
                .contentType(ContentType.JSON)
                .when()
                .post(location + "/address")
                .then().log().everything(true)
                .and()
                .assertThat().statusCode(400);
    }

    @Test
    public void assertThatServerReturns400IfCepIsInvalid() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"0130400000\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        given().body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void assertThatServerReturns400IfCepDoesNotExist() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"98667459\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        given().body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void assertThatServerReturns409IfTriesToInsertAddressTwice() {
        String simpleBody = "{\n" +
                "    \"name\": \"Gabriel\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"district\":\"Consolacao\",\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }\n" +
                "}";

        final String location = given().body(simpleBody)
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(PORT)
                .when()
                .post("/user-management/api/v1/users")
                .then().log().everything(true)
                .assertThat()
                .statusCode(201)
                .and().extract().header("Location");


        String simpleBodyWithAddress = "{\n" +
                "        \"street\": \"Rua Augusta\",\n" +
                "        \"city\": \"Sao Paulo\",\n" +
                "        \"number\": 404,\n" +
                "        \"zipcode\": \"01304000\",\n" +
                "        \"district\": \"Consolacao\",\n" +
                "        \"state\": \"SP\",\n" +
                "        \"complementary_address\": \"apto X\"\n" +
                "    }";

        given().body(simpleBodyWithAddress).contentType(ContentType.JSON)
                .when()
                .post(location + "/address")
                .then().log().everything(true)
                .and().assertThat().statusCode(409);
    }
}