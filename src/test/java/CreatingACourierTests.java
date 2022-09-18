import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class CreatingACourierTests extends BaseTest {
    //Проверяем, что курьера можно создать
    @Test
    public void createNewCourier() {
        given()
                .header("Content-type", "application/json")
                .body(newCourier)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", notNullValue());
    }

    //Проверяем, что нельзя создать двух одинаковых курьеров
    @Test
    public void creatingTwoIdenticalCouriers() {
        given()
                .header("Content-type", "application/json")
                .body(newCourier)
                .post("/api/v1/courier");
        int firstCourierId = given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().extract().path("id");
        given()
                .header("Content-type", "application/json")
                .body(newCourier)
                .post("/api/v1/courier");
        int secondCourierId = given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().extract().path("id");
        assertEquals(firstCourierId, secondCourierId);
    }

    //Проверяем, что запрос возвращает правильный код ответа
    @Test
    public void returnsTheResponseCode201() {
        given()
                .header("Content-type", "application/json")
                .body(newCourier)
                .post("/api/v1/courier")
                .then().statusCode(201);
    }

    //Проверяем, что успешный запрос возвращает ok: true
    @Test
    public void returnsOkTrue() {
        given()
                .header("Content-type", "application/json")
                .body(newCourier)
                .post("/api/v1/courier")
                .then().assertThat().body("ok", equalTo(true));
    }

    //Проверяем, что если нет имени, запрос возвращает код ответа 201
    @Test
    public void creatingACourierWithoutFirstNameReturnsTheResponseCode201() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutFirstName)
                .post("/api/v1/courier")
                .then().statusCode(201);
    }

    //Проверяем, что если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
    public void creatingTwoIdenticalCouriersReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(newCourier)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(newCourier)
                .post("/api/v1/courier")
                .then().statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    //Проверяем, что можно создать курьера без имени
    @Test
    public void creatingACourierWithoutFirstName() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutFirstName)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", is(notNullValue()));
    }

    //Проверяем, что создать курьера без логина нельзя
    @Test
    public void creatingACourierWithoutLogin() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutLogin)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithoutLogin)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", is(nullValue()));
    }

    //Проверяем, что создать курьера без пароля нельзя
    @Test
    public void creatingACourierWithoutPassword() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutPassword)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithoutPassword)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", is(nullValue()));
    }

    //Проверяем, что если нет логина, запрос возвращает ошибку
    @Test
    public void creatingACourierWithoutLoginReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutLogin)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    //Проверяем, что если нет пароля, запрос возвращает ошибку
    @Test
    public void creatingACourierWithoutPasswordReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutPassword)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    //Проверяем, что если нет логина и пароля, запрос возвращает ошибку
    @Test
    public void creatingACourierWithoutLoginAndPasswordReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutLoginAndPassword)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    //Проверяем, что если нет логина и имени, запрос возвращает ошибку
    @Test
    public void creatingACourierWithoutLoginAndFirstNameReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutLoginAndFirstName)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    //Проверяем, что если нет логина и имени, запрос возвращает ошибку
    @Test
    public void creatingACourierWithoutPasswordAndFirstNameReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(courierWithoutPasswordAndFirstName)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}

