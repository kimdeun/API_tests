import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class BaseTest {
    CourierAuthorization courierAuthorization = new CourierAuthorization("shakira@gmail.com", "p@sSworD");
    CreatingACourier creatingNewCourier = new CreatingACourier("shakira@gmail.com", "p@sSworD", "shakira shakira");
    CreatingACourier creatingCourierWithoutFirstName = new CreatingACourier("shakira@gmail.com", "p@sSworD", "");
    CourierAuthorization courierAuthorizationWithoutLogin = new CourierAuthorization("", "p@sSworD");
    CourierAuthorization courierAuthorizationWithoutPassword = new CourierAuthorization("shakira@gmail.com", "");
    CourierAuthorization courierAuthorizationWithoutLoginAndPassword = new CourierAuthorization("", "");
    CourierAuthorization courierAuthorizationWithIncorrectLogin = new CourierAuthorization("shakiraWrongMail@gmail.com", "p@sSworD");
    CourierAuthorization courierAuthorizationWithIncorrectPassword = new CourierAuthorization("shakira@gmail.com", "shakiraWrongPassword");
    CourierAuthorization courierAuthorizationWithIncorrectLoginAndPassword = new CourierAuthorization("shakiraWrongMail@gmail.com", "shakiraWrongPassword");
    CreatingACourier creatingCourierWithoutLogin = new CreatingACourier("", "p@sSworD", "shakira shakira");
    CreatingACourier creatingCourierWithoutPassword = new CreatingACourier("shakira@gmail.com", "", "shakira shakira");
    CreatingACourier creatingCourierWithoutLoginAndPassword = new CreatingACourier("", "", "shakira shakira");
    CreatingACourier creatingCourierWithoutLoginAndFirstName = new CreatingACourier("", "p@sSworD", "shakira shakira");
    CreatingACourier creatingCourierWithoutPasswordAndFirstName = new CreatingACourier("shakira@gmail.com", "", "");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void tearDown() {
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
