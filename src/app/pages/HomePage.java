package app.pages;

import app.user.User;
import app.audio.Files.Song;
import app.audio.Collections.Playlist;

import java.util.Comparator;
import java.util.List;
public class HomePage extends Page {
    public HomePage(final User user) {
        super(user);
    }
    private static final int LIMIT = 5;
    /**
     * @return the content of the page
     */
    @Override
    public String displayContent() {
        // gpt
        List<String> top5Songs = user.getLikedSongs().stream()
                .sorted(Comparator.comparing(Song::getLikes).reversed())
                .limit(LIMIT)
                .map(Song::getName)
                .toList();

        List<String> top5Playlists = user.getFollowedPlaylists().stream()
                .sorted(Comparator.comparing(Playlist::getTotalLikes).reversed())
                .limit(LIMIT)
                .map(Playlist::getName)
                .toList();

        String songsOutput = String.join(", ", top5Songs);
        String playlistsOutput = String.join(", ", top5Playlists);
        return String.format("Liked songs:\n\t[%s]\n\nFollowed playlists:\n\t[%s]",
                songsOutput, playlistsOutput);
    }
}
