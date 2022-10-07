package api.client;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;

public class CourierClient {
    String creatingACourierEndPoint = "/api/v1/courier";
    String courierAuthorizationEndPoint = "/api/v1/courier/login";
    @Step("Get response for creating a courier")
    public Response getResponseForCreatingNewCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(creatingACourierEndPoint);
    }

    @Step("Get response for courier authorization")
    public Response getResponseForCourierAuthorization(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(courierAuthorizationEndPoint);
    }
}
