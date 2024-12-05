package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import api.*;
import utils.*;
import base.BaseTest;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateOrderTest extends BaseTest {

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
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    public void testCreateOrder() {
        OrderCreation createOrder = new OrderCreation(new String[]{ingredientOne, ingredientTwo});
        Response response = OrderApi.createOrder(createOrder, accessToken);
        response
                .then().assertThat().statusCode(SC_OK).body("order.ingredients[0]._id", is(ingredientOne))
                .body("order.ingredients[1]._id", is(ingredientTwo)).body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void testCreateOrderWithoutToken() {
        OrderCreation createOrder = new OrderCreation(new String[]{ingredientOne, ingredientTwo});
        Response response = OrderApi.createOrder(createOrder);
        response
                .then().assertThat().statusCode(SC_OK).body("success", equalTo(true));

    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void testCreateOrderWithoutIngredients() {
        OrderCreation createOrder = new OrderCreation(new String[]{});
        Response response = OrderApi.createOrder(createOrder, accessToken);
        response
                .then().assertThat().statusCode(SC_BAD_REQUEST).body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание заказа с неверным хэшем ингредиентов")
    public void testCreateOrderWithWrongIngredients() {
        String wrongIngredient = RandomStringUtils.randomAlphabetic(24);
        OrderCreation createOrder = new OrderCreation(new String[]{wrongIngredient});
        Response response = OrderApi.createOrder(createOrder, accessToken);
        response
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}