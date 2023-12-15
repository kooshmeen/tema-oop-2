package app.audio.Collections;
import lombok.Getter;

import java.util.ArrayList;

public class AlbumOutput {
    @Getter
    private String name;
    @Getter
    private ArrayList<String> songs;

    public AlbumOutput(String name, ArrayList<String> songs) {
        this.name = name;
        this.songs = songs;
    }
}