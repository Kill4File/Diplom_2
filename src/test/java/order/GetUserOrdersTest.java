package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import api.*;
import utils.*;
import base.BaseTest;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class GetUserOrdersTest extends BaseTest {


    @Before
    public void setUp() {
        URL();
        user = UserCreation.makeUser();
        Response response = UserApi.createUser(user);
        email = user.getEmail();
        name = user.getName();
        accessToken = response.jsonPath().getString("accessToken");
        response = OrderApi.getIngredients();
        ingredientOne = response.jsonPath().getString("data[0]._id");
        ingredientTwo = response.jsonPath().getString("data[1]._id");
        OrderCreation createOrder = new OrderCreation(new String[]{ingredientOne, ingredientTwo});
        OrderApi.createOrder(createOrder, accessToken);
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void testGetOrders() {
        Response response = OrderApi.getOrders(accessToken);
        response
                .then().assertThat().statusCode(SC_OK).body("success", equalTo(true));
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void testGetOrdersWithoutToken() {
        Response response = OrderApi.getOrdersWithoutToken();
        response
                .then().assertThat().statusCode(SC_UNAUTHORIZED).body("success", equalTo(false));
    }
}