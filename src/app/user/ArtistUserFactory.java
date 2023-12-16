package app.user;

public class ArtistUserFactory implements UserFactory {
    /**
     * creates a new artist user
     * @param username the name
     * @param age the age
     * @param city the city
     * @return the new artist user
     */
    @Override
    public User createUser(final String username, final int age, final String city) {
        return new Artist(username, age, city);
    }
}
