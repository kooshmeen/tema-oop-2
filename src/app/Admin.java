package app;

import app.EventMerch.Merch;
import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.*;
import fileio.input.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The type Admin.
 */
public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private Admin() {
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isConnectionOnline() && !(user instanceof Host) && !(user instanceof Artist)) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }
    public static String addUser(final String username, final int age, final String city, final String type) {
        UserFactory factory;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }
        switch (type) {
            case "user":
                factory = new RegularUserFactory();
                break;
            case "artist":
                factory = new ArtistUserFactory();
                break;
            case "host":
                factory = new HostUserFactory();
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + type);
        }
        User user = factory.createUser(username, age, city);
        users.add(user);
        return "The username " + username + " has been added successfully.";
    }
    public static String addAlbum(final String username, final String albumName, final int releaseYear,
                                  final String description, final ArrayList<SongInput> songs) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Artist artist) {
                    ArrayList<Song> newSongs = new ArrayList<>();
                    for (SongInput songInput : songs) {
                        newSongs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                                songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                                songInput.getReleaseYear(), songInput.getArtist()));
                    }
                    Album album = new Album(albumName, releaseYear, description, newSongs);
                    return artist.addAlbum(album);
                } else {
                    return username + " is not an artist.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }
    public static void addSongs(final ArrayList<Song> newSongs) {
        for (Song song : newSongs) {
            songs.add(new Song(song.getName(), song.getDuration(), song.getAlbum(),
                    song.getTags(), song.getLyrics(), song.getGenre(),
                    song.getReleaseYear(), song.getArtist()));
        }
    }
    public static String addEvent(final String username, final String eventName, final DateInput date,
                                  final String description) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Artist artist) {
                    return artist.addEvent(eventName, date, description);
                } else {
                    return username + " is not an artist.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }
    public static ArrayList<Artist> getArtists() {
        ArrayList<Artist> artists = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Artist artist) {
                artists.add(artist);
            }
        }
        return artists;
    }
    public static String addMerch(final String username, final String merchName,
                                     final String description, final Integer price) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Artist artist) {
                    return artist.addMerch(merchName, description, price);
                } else {
                    return username + " is not a host.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }
}
