package api;

import constants.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.*;
import static io.restassured.RestAssured.given;

public class UserApi extends Base {

    @Step("Создание пользователя")
    public static Response createUser(UserCreation createUser) {
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(createUser)
                .when()
                .post(CREATE_USER);
    }

    @Step("Авторизация пользователя")
    public static Response loginUser(LoginUser loginUser) {
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(loginUser)
                .when()
                .post(LOGIN_USER);
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(DeleteUser deleteUser, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .and()
                .body(deleteUser)
                .when()
                .delete(DELETE_USER);
    }

    @Step("Изменение данных пользователя с токеном")
    public static Response userUpdate(UserUpdate userUpdate, String accessToken) {
        RequestSpecification requestSpecification =
                given().log().all()
                        .header("Content-type", "application/json");
        if (accessToken != null) {requestSpecification.header("Authorization", accessToken);}
        Response response =
                requestSpecification
                        .and()
                        .body(userUpdate)
                        .patch(UPDATE_USER);
        return response;
    }

    @Step("Изменение данных пользователя без токена")
    public static Response userUpdate(UserUpdate userUpdate) {
        return userUpdate(userUpdate, null);
    }
}