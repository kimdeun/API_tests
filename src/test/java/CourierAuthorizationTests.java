import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierAuthorizationTests extends BaseTest {
    @Test
    @DisplayName("Проверяем, что курьер может авторизоваться")
    public void courierAuthorization() {
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorization)
                .post("/api/v1/courier/login")
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Проверяем, что без логина авторизоваться нельзя")
    public void courierAuthorizationWithoutLogin() {
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
    @DisplayName("Проверяем, что без пароля авторизоваться нельзя")
    public void courierAuthorizationWithoutPassword() {
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
    @DisplayName("Проверяем, что если логин некорректный, запрос возвращает ошибку")
    public void courierAuthorizationWithIncorrectLoginReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithIncorrectLogin)
                .post("/api/v1/courier/login")
                .then().statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверяем, что если пароль некорректный, запрос возвращает ошибку")
    public void courierAuthorizationWithIncorrectPasswordReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingNewCourier)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithIncorrectPassword)
                .post("/api/v1/courier/login")
                .then().statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверяем, что система вернёт ошибку, если авторизоваться без логина")
    public void courierAuthorizationWithoutLoginReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutLogin)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithoutLogin)
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверяем, что система вернёт ошибку, если авторизоваться без пароля")
    public void courierAuthorizationWithoutPasswordReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutPassword)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithoutPassword)
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверяем, что система вернёт ошибку, если авторизоваться без логина и пароля")
    public void courierAuthorizationWithoutLoginAndPasswordReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(creatingCourierWithoutLoginAndPassword)
                .post("/api/v1/courier");
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithoutLoginAndPassword)
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверяем, что система вернёт ошибку, если авторизоваться под несуществующим пользователем")
    public void courierAuthorizationWithIncorrectLoginAndPasswordReturnsAnError() {
        given()
                .header("Content-type", "application/json")
                .body(courierAuthorizationWithIncorrectLoginAndPassword)
                .post("/api/v1/courier/login")
                .then().statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверяем, что успешный запрос возвращает id")
    public void courierAuthorizationReturnsId() {
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
}
