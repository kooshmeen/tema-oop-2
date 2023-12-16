package app.user;

import app.Admin;
import app.EventMerch.Event;
import app.EventMerch.Merch;
import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import fileio.input.DateInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;

public class Artist extends User {
    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Event> events;
    @Getter
    private ArrayList<Merch> merch;
    private final static int MAX_DAYS = 31;
    private final static int MAX_MONTHS = 12;
    private final static int MAX_DAYS_FEBRUARY = 28;
    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merch = new ArrayList<>();
    }

    /**
     * Switch connection status.
     * because an artist can't switch his connection status
     * it overrides the method from User
     * @return the failed message
     */
    @Override
    public String switchConnectionStatus() {
        return getUsername() + " is not a regular user.";
    }

    /**
     * adds an album to the artist's albums
     * @param album the album to be added
     * @return a message
     */
    public String addAlbum(final Album album) {
        if (albums == null) {
            albums = new ArrayList<>();
        }
        for (Album album1 : albums) {
            if (album1.getName().equals(album.getName())) {
                return getUsername() + " has another album with the same name.";
            }
        }
        // gpt
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

    /**
     * adds an event to the artist's events
     * @param eventName the name of the event
     * @param date the date of the event
     * @param description the description of the event
     * @return a message
     */
    public String addEvent(final String eventName, final DateInput date,
                           final String description) {
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
        if (day < 1 || day > MAX_DAYS || month < 1 || month > MAX_MONTHS
                || year < 0 || (month == 2 && day > MAX_DAYS_FEBRUARY)) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }
        events.add(new Event(eventName, date, description));
        return getUsername() + " has added new event successfully.";
    }

    /**
     * adds a merch to the artist's merch
     * @param merchName the name of the merch
     * @param description the description of the merch
     * @param price the price of the merch
     * @return a message
     */
    public String addMerch(final String merchName, final String description,
                           final Integer price) {
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

    /**
     * removes an album from the artist's albums
     * @param username the username of the artist
     * @param albumName the name of the album
     * @return a message
     */
    public String removeAlbum(final String username, final String albumName) {
        if (albums == null) {
            return username + " doesn't have an album with the given name.";
        }
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                for (User user : Admin.getUsers()) {
                    if (user.getSelectedAlbum() != null &&
                            user.getSelectedAlbum().getName().equals(albumName)) {
                        return username + " can't delete this album.";
                    }
                    if (user.getPlayer().getCurrentAudioFile() != null &&
                            user.getPlayer().getCurrentAudioFile().getName().equals(albumName)) {
                        return username + " can't delete this album.";
                    }
                    for (Playlist playlist : user.getPlaylists()) {
                        for (Song song : playlist.getSongs()) {
                            if (song.getAlbum().equals(albumName)) {
                                return username + " can't delete this album.";
                            }
                        }
                    }
                    if (user.getSelectedAlbum() != null) {
                        return username + " can't delete this album.";
                    }
                }
                albums.remove(album);
                return username + " deleted the album successfully.";
            }
        }
        return username + " doesn't have an album with the given name.";
    }

    /**
     * removes an event from the artist's events
     * @param username the username of the artist
     * @param eventName the name of the event
     * @return a message
     */
    public String removeEvent(final String username, final String eventName) {
        if (events == null) {
            return username + " doesn't have an event with the given name.";
        }
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                events.remove(event);
                return username + " deleted the event successfully.";
            }
        }
        return username + " doesn't have an event with the given name.";
    }

    /**
     * gets the number of likes of the artist
     * by adding the likes of all his albums
     * @return the number of likes
     */
    public int getLikes() {
        int likes = 0;
        for (Album album : albums) {
            likes += album.getLikes();
        }
        return likes;
    }
}