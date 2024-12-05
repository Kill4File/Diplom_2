package constants;

public class Base {
    //Ручки пользователя
    public static final String CREATE_USER = "/api/auth/register";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String DELETE_USER = "/api/auth/user";
    public static final String UPDATE_USER = "/api/auth/user";
    //Ручки заказа
    public static final String CREATE_ORDER = "/api/orders";
    public static final String GET_ORDERS = "/api/orders";
    public static final String GET_INGREDIENTS = "/api/ingredients";

    public String email;
    public String password;
    public String name;

}
