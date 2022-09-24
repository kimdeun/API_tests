import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class ListOfOrdersTests extends BaseTest {

    @Test
    @DisplayName("Проверяем, что в тело ответа возвращается список заказов")
    public void getListOfOrders() {
        List<Orders> list;
        list = given()
                .get("/api/v1/orders")
                .body().as(ListOfOrders.class).getOrders();
        assertNotEquals(new ArrayList<>(), list);
    }

    @Override
    public void tearDown() {}
}
