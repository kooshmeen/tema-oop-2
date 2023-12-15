package app.user;

public class ArtistUserFactory implements UserFactory {
    @Override
    public User createUser(String username, int age, String city) {
        return new Artist(username, age, city);
    }
}