package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    String creatingAnOrderEndPoint = "/api/v1/orders";
    String gettingAnOrderTrackEndPoint = "/api/v1/orders/track";
    String cancelOrderEndPoint = "/api/v1/orders/cancel";

    @Step("Get response for creating an order")
    public Response getResponseForCreatingAnOrder(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(creatingAnOrderEndPoint);
    }

    @Step("Get response for get an order track")
    public Response getResponseForGettingAnOrderTrack(int track) {
        return given()
                .queryParam("t", track)
                .get(gettingAnOrderTrackEndPoint);
    }

    @Step("Get response for cancel order")
    public Response getResponseForCalcelOrder(int track) {
        return given()
                .queryParam("track", track)
                .put(cancelOrderEndPoint);
    }
}
