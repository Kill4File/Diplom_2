package user;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import api.UserApi;
import utils.*;
import base.BaseTest;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends BaseTest {


    @Before
    public void setUp() {
        URL();
        user = UserCreation.makeUser();
        UserApi.createUser(user);
        email = user.getEmail();
        name = user.getName();
        password = user.getPassword();
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void testLoginUser() {
        LoginUser loginUser = new LoginUser(email, password);
        Response response = UserApi.loginUser(loginUser);
        response
                .then().assertThat().statusCode(SC_OK).body("success", equalTo(true));
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным логином и паролем")
    public void testWrongLoginUser() {
        LoginUser loginUser = new LoginUser(RandomStringUtils.randomAlphabetic(8) + "@yandex.ru", RandomStringUtils.randomAlphabetic(5));
        Response response = UserApi.loginUser(loginUser);
        response
                .then().assertThat().statusCode(SC_UNAUTHORIZED).body("success", equalTo(false));
        accessToken = response.jsonPath().getString("accessToken");
    }


    
}