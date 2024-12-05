package base;

import org.junit.After;
import utils.*;
import api.*;
import io.restassured.RestAssured;
import static org.apache.http.HttpStatus.*;

public class BaseTest {

    public void URL() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    public String email;
    public String name;
    public String password;
    public String accessToken;
    public String ingredientOne;
    public String ingredientTwo;
    public UserCreation user;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            UserApi.deleteUser(new DeleteUser(email, name), accessToken).then().statusCode(SC_ACCEPTED);
        }
    }
}
