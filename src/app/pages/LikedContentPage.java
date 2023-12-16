package app.pages;

import app.user.User;

import java.util.List;

public class LikedContentPage extends Page {
    public LikedContentPage(final User user) {
        super(user);
    }
    /**
     * @return the content of the page
     */
    @Override
    public String displayContent() {
        List<String> top5Songs = user.getLikedSongs().stream()
                .map(song -> song.getName() + " - " + song.getArtist())
                .toList();

        List<String> top5Playlists = user.getFollowedPlaylists().stream()
                .map(playlist -> playlist.getName() + " - " + playlist.getOwner())
                .toList();
        String songsOutput = String.join(", ", top5Songs);
        String playlistsOutput = String.join(", ", top5Playlists);
        return String.format("Liked songs:\n\t[%s]\n\nFollowed playlists:\n\t[%s]",
                songsOutput, playlistsOutput);
    }
}
