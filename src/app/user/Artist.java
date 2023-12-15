package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;

public class Artist extends User {

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    @Getter
    private ArrayList<Album> albums;
    public Artist(String username, int age, String city) {
        super(username, age, city);
    }
    @Override
    public String switchConnectionStatus() {
        return getUsername() + " is not a regular user.";
    }
    public String addAlbum(Album album) {
        if (albums == null) {
            albums = new ArrayList<>();
        }
        for (Album album1 : albums) {
            if (album1.getName().equals(album.getName())) {
                return getUsername() + " has another album with the same name.";
            }
        }
        HashSet<String> uniqueSongs = new HashSet<>();
        for (SongInput song : album.getSongs()) {
            if (!uniqueSongs.add(song.getName())) {
                return getUsername() + " has the same song at least twice in this album.";
            }
        }
        albums.add(album);
        Admin.addSongs(album.getSongsAsSongs());
        return getUsername() + " has added new album successfully.";
    }
}
