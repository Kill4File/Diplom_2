package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import api.*;
import utils.*;
import base.BaseTest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class CreateUserTest extends BaseTest {

    @Before
    public void setUp() {
        URL();
        user = UserCreation.makeUser();
        email = user.getEmail();
        name = user.getName();

    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void testCreateNewUser() {
        Response response = UserApi.createUser(user);
        response
                .then().assertThat().statusCode(SC_OK).body("success", equalTo(true));
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void testCreateTwiceUser() {
        Response response = UserApi.createUser(user);
        Response responseOne = UserApi.createUser(user);
        responseOne
                .then().assertThat().statusCode(SC_FORBIDDEN).body("message", is("User already exists"));
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    public void testCreateUserWithoutData() {
        UserCreation createUser = new UserCreation(RandomStringUtils.randomAlphabetic(8) + "@yandex.ru".toLowerCase(), null, RandomStringUtils.randomAlphabetic(7));
        Response response = UserApi.createUser(createUser);
        response
                .then().assertThat().statusCode(SC_FORBIDDEN).body("message", is("Email, password and name are required fields"));
        accessToken = response.jsonPath().getString("accessToken");
    }
}