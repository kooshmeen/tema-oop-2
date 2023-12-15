package app.user;

public class HostUserFactory implements UserFactory {
    @Override
    public User createUser(String username, int age, String city) {
        return new Host(username, age, city);
    }
}