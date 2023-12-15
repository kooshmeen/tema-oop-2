package app.audio.Collections;
import app.audio.Files.Song;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
@Getter
public class Album {
    private final String name;
    private final Integer releaseYear;
    private final String description;
    private final ArrayList<Song> songs;
    public Album(final String name, final Integer releaseYear, final String description,
                 final ArrayList<Song> songs) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }
}
