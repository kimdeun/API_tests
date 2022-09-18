import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class BaseTest {
    CourierAuthorization courierAuthorization = new CourierAuthorization("shakira@gmail.com", "p@sSworD");
    CreatingACourier newCourier = new CreatingACourier("shakira@gmail.com", "p@sSworD", "shakira shakira");
    CreatingACourier courierWithoutFirstName = new CreatingACourier("shakira@gmail.com", "p@sSworD", "");
    CourierAuthorization courierAuthorizationWithoutLogin = new CourierAuthorization("", "p@sSworD");
    CourierAuthorization courierAuthorizationWithoutPassword = new CourierAuthorization("shakira@gmail.com", "");
    CreatingACourier courierWithoutLogin = new CreatingACourier("", "p@sSworD", "shakira shakira");
    CreatingACourier courierWithoutPassword = new CreatingACourier("shakira@gmail.com", "", "shakira shakira");
    CreatingACourier courierWithoutLoginAndPassword = new CreatingACourier("", "", "shakira shakira");
    CreatingACourier courierWithoutLoginAndFirstName = new CreatingACourier("", "p@sSworD", "shakira shakira");
    CreatingACourier courierWithoutPasswordAndFirstName = new CreatingACourier("shakira@gmail.com", "", "");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void deleteCourierById() {
        try{
            int id = given()
                    .header("Content-type", "application/json")
                    .body(courierAuthorization)
                    .post("/api/v1/courier/login")
                    .then().extract().path("id");
            given()
                    .delete((String.format("/api/v1/courier/%d", id)));
        } catch (Exception NullPointerException) {
            System.out.println("Курьер не был создан");
        }
    }
}
