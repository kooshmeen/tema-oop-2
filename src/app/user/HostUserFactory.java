package app.user;

public class HostUserFactory implements UserFactory {
    /**
     * Create a new host user
     * @param username the username
     * @param age the age
     * @param city the city
     * @return the new host user
     */
    @Override
    public User createUser(final String username, final int age, final String city) {
        return new Host(username, age, city);
    }
}
