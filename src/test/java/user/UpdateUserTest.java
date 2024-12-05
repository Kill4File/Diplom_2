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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest extends BaseTest {

    @Before
    public void setUp() {
        URL();
        user = UserCreation.makeUser();
        Response response = UserApi.createUser(user);
        email = user.getEmail();
        name = user.getName();
        password = user.getPassword();
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    public void testUpdateUser() {
        UserUpdate updateUser = new UserUpdate(RandomStringUtils.randomAlphanumeric(8) + "@test.io", RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(7));
        email = updateUser.getEmail();
        name = updateUser.getName();
        password = updateUser.getPassword();
        Response response = UserApi.userUpdate(updateUser, accessToken);
        response
                .then().assertThat().statusCode(SC_OK).body("success", equalTo(true)).body("user.email", is(email.toLowerCase())).body("user.name", is(name));
        LoginUser loginUser = new LoginUser(email, password);
        UserApi.loginUser(loginUser).then().assertThat().statusCode(SC_OK);

    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void testUpdateUserWithoutToken() {
        UserUpdate userUpdate = new UserUpdate(RandomStringUtils.randomAlphanumeric(8) + "@test.io", RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(7));
        Response response = UserApi.userUpdate(userUpdate);
        response
                .then().assertThat().statusCode(SC_UNAUTHORIZED).body("success", equalTo(false));
    }
}