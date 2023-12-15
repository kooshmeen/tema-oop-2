package app.user;

public interface UserFactory {
    User createUser(String username, int age, String city);
}