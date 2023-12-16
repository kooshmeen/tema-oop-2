package app.user;

public interface UserFactory {
    /**
     * Creates a user.
     * @param username the username of the user
     * @param age the age of the user
     * @param city the city of the user
     * @return the created user
     */
    User createUser(String username, int age, String city);
}
