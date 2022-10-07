import api.client.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreatingAnOrderTests extends BaseTest {
    OrdersClient ordersClient = new OrdersClient();
    private final File creatingAnOrder;
    private final List<String> expected;

    public CreatingAnOrderTests(File creatingAnOrder, List<String> expected) {
        this.creatingAnOrder = creatingAnOrder;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] getColourOfScooter() {
        return new Object[][]{
                {new File("src/test/java/resources/CreatingAnOrderWithGreyColourOfScooter.json"), List.of("GREY")},
                {new File("src/test/java/resources/CreatingAnOrderWithBlackColourOfScooter.json"), List.of("BLACK")},
                {new File("src/test/java/resources/CreatingAnOrderWithBlackAndGreyColourOfScooter.json"), List.of("BLACK", "GREY")},
                {new File("src/test/java/resources/CreatingAnOrderWithoutColourOfScooter.json"), List.of("")}
        };
    }

    @Test
    @DisplayName("Проверяем, что можно создать заказ с разными цветами самокатов")
    public void creatingAnOrderWithDifferentColorsOfScooter() {
        int track = ordersClient.getResponseForCreatingAnOrder(creatingAnOrder)
                .then().extract().path("track");
        ordersClient.getResponseForGettingAnOrderTrack(track)
                .then().assertThat().body("order.color", equalTo(expected));
    }

    @Test
    @DisplayName("Проверяем, что при создании заказа возвращается track номер")
    public void checksThatAfterCreatingOrderTrackIsNotNull() {
        ordersClient.getResponseForCreatingAnOrder(creatingAnOrder)
                .then().body("track", is(notNullValue()));
    }

    @Override
    public void tearDown() {
        int track = ordersClient.getResponseForCreatingAnOrder(creatingAnOrder)
                .then().extract().path("track");
        ordersClient.getResponseForCalcelOrder(track);
        ordersClient.getResponseForGettingAnOrderTrack(track)
                .then().statusCode(404);
    }
}
