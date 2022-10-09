import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class CreatingACourierTests extends BaseTest {
    CourierClient courierClient = new CourierClient();

    @Test
    @DisplayName("Проверяем, что курьера можно создать")
    public void createNewCourier() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier);
        courierClient.getResponseForCourierAuthorization(courierAuthorization)
                .then().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверяем, что нельзя создать двух одинаковых курьеров")
    public void creatingTwoIdenticalCouriers() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier);
        int firstCourierId = courierClient.getResponseForCourierAuthorization(courierAuthorization)
                .then().extract().path("id");
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier);
        int secondCourierId = courierClient.getResponseForCourierAuthorization(courierAuthorization)
                .then().extract().path("id");
        assertEquals(firstCourierId, secondCourierId);
    }

    @Test
    @DisplayName("Проверяем, что запрос возвращает правильный код ответа")
    public void returnsTheResponseCode201() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier)
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Проверяем, что успешный запрос возвращает ok: true")
    public void returnsOkTrue() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier)
                .then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверяем, что если нет имени, запрос возвращает код ответа 201")
    public void creatingACourierWithoutFirstNameReturnsTheResponseCode201() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutFirstName)
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Проверяем, что если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void creatingTwoIdenticalCouriersReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier);
        courierClient.getResponseForCreatingNewCourier(creatingNewCourier)
                .then().statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Проверяем, что можно создать курьера без имени")
    public void creatingACourierWithoutFirstName() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutFirstName);
        courierClient.getResponseForCourierAuthorization(courierAuthorization)
                .then().assertThat().body("id", is(notNullValue()));
    }

    @Test
    @DisplayName("Проверяем, что создать курьера без логина нельзя")
    public void creatingACourierWithoutLogin() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutLogin);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithoutLogin)
                .then().assertThat().body("id", is(nullValue()));
    }

    @Test
    @DisplayName("Проверяем, что создать курьера без пароля нельзя")
    public void creatingACourierWithoutPassword() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutPassword);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithoutPassword)
                .then().assertThat().body("id", is(nullValue()));
    }

    @Test
    @DisplayName("Проверяем, что создать курьера без логина и пароля нельзя")
    public void creatingACourierWithoutLoginAndPassword() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutLoginAndPassword);
        courierClient.getResponseForCourierAuthorization(courierAuthorizationWithoutLoginAndPassword)
                .then().assertThat().body("id", is(nullValue()));
    }

    @Test
    @DisplayName("Проверяем, что если нет логина, запрос возвращает ошибку")
    public void creatingACourierWithoutLoginReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutLogin)
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверяем, что если нет пароля, запрос возвращает ошибку")
    public void creatingACourierWithoutPasswordReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutPassword)
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверяем, что если нет логина и пароля, запрос возвращает ошибку")
    public void creatingACourierWithoutLoginAndPasswordReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutLoginAndPassword)
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Проверяем, что если нет логина и имени, запрос возвращает ошибку")
    public void creatingACourierWithoutLoginAndFirstNameReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutLoginAndFirstName)
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверяем, что если нет логина и имени, запрос возвращает ошибку")
    public void creatingACourierWithoutPasswordAndFirstNameReturnsAnError() {
        courierClient.getResponseForCreatingNewCourier(creatingCourierWithoutPasswordAndFirstName)
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}

