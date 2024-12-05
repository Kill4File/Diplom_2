package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class UserCreation {

    private String email;
    private String password;
    private String name;

    public UserCreation(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public static UserCreation makeUser() {
        final String email = RandomStringUtils. randomAlphanumeric(8) + "@test.io".toLowerCase();
        final String password = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        final String name = RandomStringUtils.randomAlphanumeric(10);
        return new UserCreation(email, password, name);
    }
}