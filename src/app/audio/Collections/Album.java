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
    private final ArrayList<SongInput> songs;
    public Album(final String name, final Integer releaseYear, final String description,
                 final ArrayList<SongInput> songs) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }
    public ArrayList<Song> getSongsAsSongs() {
        ArrayList<Song> songs2 = new ArrayList<>();
        for (SongInput song : songs) {
            songs2.add(new Song(song.getName(), song.getDuration(), name, song.getTags(),
                    song.getLyrics(), song.getGenre(), releaseYear, song.getArtist()));
        }
        return songs2;
    }
}
