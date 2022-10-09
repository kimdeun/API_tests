import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CourierAuthorizationTests extends BaseTest {
    CourierClient courierClient = new CourierClient();

    @Test
    @DisplayName("Проверяем, что курьер может авторизоваться")
    public void courierAuthorization() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier);
        courierClient.getResponseForCourierAuthorization(courierAuthorization)
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Проверяем, что без логина авторизоваться нельзя")
    public void courierAuthorizationWithoutLogin() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutLogin);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithoutLogin)
                .then().assertThat().body("id", is(nullValue()));
    }

    @Test
    @DisplayName("Проверяем, что без пароля авторизоваться нельзя")
    public void courierAuthorizationWithoutPassword() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutPassword);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithoutPassword)
                .then().assertThat().body("id", is(nullValue()));
    }

    @Test
    @DisplayName("Проверяем, что если логин некорректный, запрос возвращает ошибку")
    public void courierAuthorizationWithIncorrectLoginReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithIncorrectLogin)
                .then().statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверяем, что если пароль некорректный, запрос возвращает ошибку")
    public void courierAuthorizationWithIncorrectPasswordReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithIncorrectPassword)
                .then().statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверяем, что система вернёт ошибку, если авторизоваться без логина")
    public void courierAuthorizationWithoutLoginReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutLogin);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithoutLogin)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверяем, что система вернёт ошибку, если авторизоваться без пароля")
    public void courierAuthorizationWithoutPasswordReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutPassword);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithoutPassword)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверяем, что система вернёт ошибку, если авторизоваться без логина и пароля")
    public void courierAuthorizationWithoutLoginAndPasswordReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutLoginAndPassword);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithoutLoginAndPassword)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверяем, что система вернёт ошибку, если авторизоваться под несуществующим пользователем")
    public void courierAuthorizationWithIncorrectLoginAndPasswordReturnsAnError() {
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithIncorrectLoginAndPassword)
                .then().statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверяем, что успешный запрос возвращает id")
    public void courierAuthorizationReturnsId() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier);
        courierClient.getResponseForCourierAuthorization(courierAuthorization)
                .then().assertThat().body("id", notNullValue());
    }
}
