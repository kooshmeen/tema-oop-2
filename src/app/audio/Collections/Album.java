package app.audio.Collections;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
@Getter
public class Album {
    private final String name;
    private final Integer releaseYear;
    private final String description;
    private final ArrayList<Song> songs;
    private final String owner;
    public Album(final String name, final Integer releaseYear, final String description,
                 final ArrayList<Song> songs, final String owner) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
        this.owner = owner;
    }
    /**
     * @return the number of likes of the album
     * by counting the likes of all songs
     */
    public Integer getLikes() {
        Integer likes = 0;
        for (Song song : songs) {
            likes += song.getLikes();
        }
        return likes;
    }
}
