package app.audio.Collections;
import app.audio.Files.Song;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
public class Album {
    @Getter
    private final String name;
    @Getter
    private final Integer releaseYear;
    @Getter
    private final String description;
    @Getter
    private final ArrayList<Song> songs;
    public Album(final String name, final Integer releaseYear, final String description,
                 final ArrayList<Song> songs) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }
}
