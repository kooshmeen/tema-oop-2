package app.pages;

import app.user.User;
import app.audio.Files.Song;
import app.audio.Collections.Playlist;

import java.util.Comparator;
import java.util.List;

public class LikedContentPage extends Page{
    public LikedContentPage(User user) {
        super(user);
    }
    @Override
    public String displayContent() {
        List<String> top5Songs = user.getLikedSongs().stream()
                .sorted(Comparator.comparing(Song::getLikes).reversed())
                .limit(5)
                .map(Song::getName)
                .toList();

        List<String> top5Playlists = user.getFollowedPlaylists().stream()
                .sorted(Comparator.comparing(Playlist::getTotalLikes).reversed())
                .limit(5)
                .map(playlist -> playlist.getName() + " - " + playlist.getOwner())
                .toList();
        String songsOutput = String.join(", ", top5Songs);
        String playlistsOutput = String.join(", ", top5Playlists);
        return String.format("Liked songs:\n\t[%s]\n\nFollowed playlists:\n\t[%s]", songsOutput, playlistsOutput);
    }
}
