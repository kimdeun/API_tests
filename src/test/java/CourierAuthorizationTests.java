import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class CourierAuthorizationTests extends BaseTest {
    @Test
    public void courierAuthorization() {
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

    @Test
    public void
}
