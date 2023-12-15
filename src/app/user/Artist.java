package app.user;

import app.Admin;
import app.EventMerch.Event;
import app.EventMerch.Merch;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import fileio.input.DateInput;
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
    @Getter
    private ArrayList<Event> events;
    @Getter
    private ArrayList<Merch> merch;
    public Artist(String username, int age, String city) {
        super(username, age, city);
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merch = new ArrayList<>();
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
        for (Song song : album.getSongs()) {
            if (!uniqueSongs.add(song.getName())) {
                return getUsername() + " has the same song at least twice in this album.";
            }
        }
        albums.add(album);
        Admin.addSongs(album.getSongs());
        return getUsername() + " has added new album successfully.";
    }
    public String addEvent(final String eventName, final DateInput date, final String description) {
        if (events == null) {
            events = new ArrayList<>();
        }
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return getUsername() + " has another event with the same name.";
            }
        }
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 0 || (month == 2 && day > 28)) {
            return "Event for " + getUsername() + " does not have a valid date";
        }
        events.add(new Event(eventName, date, description));
        return getUsername() + " has added new event successfully.";
    }
    public String addMerch(final String merchName, final String description, final Integer price) {
        if (merch == null) {
            merch = new ArrayList<>();
        }
        for (Merch merch1 : merch) {
            if (merch1.getName().equals(merchName)) {
                return getUsername() + " has merchandise with the same name.";
            }
        }
        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }
        merch.add(new Merch(merchName, description, price));
        return getUsername() + " has added new merchandise successfully.";
    }
}
