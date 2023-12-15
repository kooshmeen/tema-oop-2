package app.user;

public class RegularUserFactory implements UserFactory {
    @Override
    public User createUser(String username, int age, String city) {
        return new User(username, age, city);
    }
}