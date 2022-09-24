import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class CreatingACourierTests extends BaseTest {
    @Test
    @DisplayName("Проверяем, что курьера можно создать")
    public void createNewCourier() {
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверяем, что нельзя создать двух одинаковых курьеров")
    public void creatingTwoIdenticalCouriers() {
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier");
        int firstCourierId = given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().extract().path("id");
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier");
        int secondCourierId = given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().extract().path("id");
        assertEquals(firstCourierId, secondCourierId);
    }

    @Test
    @DisplayName("Проверяем, что запрос возвращает правильный код ответа")
    public void returnsTheResponseCode201() {
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier")
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Проверяем, что успешный запрос возвращает ok: true")
    public void returnsOkTrue() {
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier")
                .then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверяем, что если нет имени, запрос возвращает код ответа 201")
    public void creatingACourierWithoutFirstNameReturnsTheResponseCode201() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutFirstName)
                .post("/api/v1/courier")
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Проверяем, что если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void creatingTwoIdenticalCouriersReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier")
                .then().statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Проверяем, что можно создать курьера без имени")
    public void creatingACourierWithoutFirstName() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutFirstName)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", is(notNullValue()));
    }

    @Test
    @DisplayName("Проверяем, что создать курьера без логина нельзя")
    public void creatingACourierWithoutLogin() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutLogin)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithoutLogin)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", is(nullValue()));
    }

    @Test
    @DisplayName("Проверяем, что создать курьера без пароля нельзя")
    public void creatingACourierWithoutPassword() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutPassword)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithoutPassword)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", is(nullValue()));
    }

    @Test
    @DisplayName("Проверяем, что создать курьера без логина и пароля нельзя")
    public void creatingACourierWithoutLoginAndPassword() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutLoginAndPassword)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithoutLoginAndPassword)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", is(nullValue()));
    }

    @Test
    @DisplayName("Проверяем, что если нет логина, запрос возвращает ошибку")
    public void creatingACourierWithoutLoginReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutLogin)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверяем, что если нет пароля, запрос возвращает ошибку")
    public void creatingACourierWithoutPasswordReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutPassword)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверяем, что если нет логина и пароля, запрос возвращает ошибку")
    public void creatingACourierWithoutLoginAndPasswordReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutLoginAndPassword)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверяем, что если нет логина и имени, запрос возвращает ошибку")
    public void creatingACourierWithoutLoginAndFirstNameReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutLoginAndFirstName)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверяем, что если нет логина и имени, запрос возвращает ошибку")
    public void creatingACourierWithoutPasswordAndFirstNameReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutPasswordAndFirstName)
                .post("/api/v1/courier")
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}

