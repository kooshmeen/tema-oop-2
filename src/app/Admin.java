package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.user.UserFactory;
import app.user.ArtistUserFactory;
import app.user.HostUserFactory;
import app.user.RegularUserFactory;
import fileio.input.DateInput;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The type Admin.
 */
public final class Admin {
    @Getter
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
        ArrayList<Song> sortedSongs = new ArrayList<>(songs);
        for (User user : users) {
            for (Song song : user.getLikedSongs()) {
                for (Song song2 : sortedSongs) {
                    if (song.getName().equals(song2.getName())) {
                        song2.like();
                    }
                }
            }
        }
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
    /**
     * Gets online users.
     *
     * @return the online users
     */
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isConnectionOnline() && !(user instanceof Host) && !(user instanceof Artist)) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }
    /**
     * Gets all users.
     *
     * @return the all users
     */
    public static List<String> getAllUsers() {
        List<String> allUsers = new ArrayList<>();
        for (User user : users) {
            if (!(user instanceof Host) && !(user instanceof Artist)) {
                allUsers.add(user.getUsername());
            }
        }
        for (User user : users) {
            if (user instanceof Artist) {
                allUsers.add(user.getUsername());
            }
        }
        for (User user : users) {
            if (user instanceof Host) {
                allUsers.add(user.getUsername());
            }
        }
        return allUsers;
    }
    /**
     * adds a user
     *
     *  @param username the username
     *  @param age the age
     *  @param city the city
     *  @param type the type (regular, artist or host)
     *  @return the string
     */
    public static String addUser(final String username, final int age,
                                 final String city, final String type) {
        UserFactory factory;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }
        factory = switch (type) {
            case "user" -> new RegularUserFactory();
            case "artist" -> new ArtistUserFactory();
            case "host" -> new HostUserFactory();
            default -> throw new IllegalArgumentException("Invalid user type: " + type);
        };
        User user = factory.createUser(username, age, city);
        users.add(user);
        return "The username " + username + " has been added successfully.";
    }

    /**
     * adds an album
     * @param username the username
     * @param albumName the album name
     * @param releaseYear the release year
     * @param description the description
     * @param albumSongs the songs in the album
     * @return the string
     */
    public static String addAlbum(final String username, final String albumName,
                                  final int releaseYear, final String description,
                                  final ArrayList<SongInput> albumSongs) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Artist artist) {
                    ArrayList<Song> newSongs = new ArrayList<>();
                    for (SongInput songInput : albumSongs) {
                        newSongs.add(new Song(songInput.getName(),
                                songInput.getDuration(),
                                songInput.getAlbum(),
                                songInput.getTags(), songInput.getLyrics(),
                                songInput.getGenre(),
                                songInput.getReleaseYear(),
                                songInput.getArtist()));
                    }
                    Album album = new Album(albumName, releaseYear,
                            description, newSongs, artist.getUsername());
                    return artist.addAlbum(album);
                } else {
                    return username + " is not an artist.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }
    /**
     * adds a podcast
     * @param username the username
     * @param podcastName the podcast name
     * @param episodes the episodes
     * @return the string
     */
    public static String addPodcast(final String username, final String podcastName,
                                    final ArrayList<EpisodeInput> episodes) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Host host) {
                    ArrayList<Episode> newEpisodes = new ArrayList<>();
                    for (EpisodeInput episodeInput : episodes) {
                        newEpisodes.add(new Episode(episodeInput.getName(),
                                episodeInput.getDuration(),
                                episodeInput.getDescription()));
                    }
                    Podcast podcast = new Podcast(podcastName, host.getUsername(), newEpisodes);
                    podcasts.add(podcast);
                    return host.addPodcast(podcast);
                } else {
                    return username + " is not a host.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }

    /**
     * adds songs to the list of songs (library)
     * @param newSongs the new songs
     */

    public static void addSongs(final ArrayList<Song> newSongs) {
        for (Song song : newSongs) {
            songs.add(new Song(song.getName(), song.getDuration(), song.getAlbum(),
                    song.getTags(), song.getLyrics(), song.getGenre(),
                    song.getReleaseYear(), song.getArtist()));
        }
    }

    /**
     *
     * @param username the username
     * @param eventName the event name
     * @param date the date
     * @param description the description
     * @return the message string
     */
    public static String addEvent(final String username, final String eventName,
                                  final DateInput date, final String description) {
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
    /**
     * Gets just artists from all users.
     *
     * @return the artists
     */
    public static ArrayList<Artist> getArtists() {
        ArrayList<Artist> artists = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Artist artist) {
                artists.add(artist);
            }
        }
        return artists;
    }
    /**
     * Gets albums from artist type users.
     *
     * @return the albums
     */
    public static ArrayList<Album> getAlbums() {
        ArrayList<Album> albums = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Artist artist) {
                albums.addAll(artist.getAlbums());
            }
        }
        return albums;
    }
    /**
     * Gets hosts from all users.
     *
     * @return the hosts
     */
    public static ArrayList<Host> getHosts() {
        ArrayList<Host> hosts = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Host host) {
                hosts.add(host);
            }
        }
        return hosts;
    }

    /**
     * adds merch to an artists merch list
     * @param username the username
     * @param merchName the merch name
     * @param description the description
     * @param price the price
     * @return the message string
     */
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

    /**
     * deletes a user
     * @param username the username of the user to be deleted
     * @return the message string
     */
    public static String deleteUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (!(user instanceof Host) && !(user instanceof Artist)) {
                    // regular users can be deleted
                    for (Song song : songs) {
                        if (user.getLikedSongs().contains(song)) {
                            song.dislike();
                        }
                    }
                    for (User user2 : users) {
                        // removes the user from the followed playlists of the other users
                        for (Playlist followedPlaylist : user2.getFollowedPlaylists()) {
                            followedPlaylist.decreaseFollowers();
                            if (followedPlaylist.getOwner().equals(username)) {
                                user2.getFollowedPlaylists().remove(followedPlaylist);

                                break;
                            }
                        }
                        if (user2.getPlayer().getCurrentAudioFile() != null
                                && user2.getPlayer().getCurrentAudioFile().
                                matchesOwner(username)) {
                            return username + " can't be deleted.";
                        }
                    }
                    for (Song song : songs) {
                        if (user.showPreferredSongs().contains(song.getName())) {
                            song.dislike();
                        }
                    }
                    users.remove(user);
                    return username + " was successfully deleted.";
                } else {
                    if (user instanceof Artist) {
                        for (User user2 : users) {
                            // checks to see if any user is listening to the artist
                            assert user2.getPlayer().getCurrentAudioFile() != null;
                            if ((user2.getPlayer().getCurrentAudioFile() != null
                                    && user2.getPlayer().getCurrentAudioFile()
                                    .matchesArtist(username))) {
                                return username + " can't be deleted.";
                            }
                        }
                        songs.removeIf(song -> song.getArtist().equals(username));
                        for (User user2 : users) {
                            // removes the songs from the liked songs of the users
                            for (Song likedSong : user2.getLikedSongs()) {
                                if (likedSong.getArtist().equals(username)) {
                                    user2.getLikedSongs().remove(likedSong);
                                    break;
                                }
                            }
                        }
                        users.remove(user);
                        return username + " was successfully deleted.";
                    } else {
                        for (User user2 : users) {
                            assert user2.getPlayer().getCurrentAudioFile() != null;
                            if (user2.getSelectedHost() != null
                                    && user2.getSelectedHost().getUsername().equals(username)) {
                                return username + " can't be deleted.";
                            }
                            if (user2.getSelectedPodcast() != null
                                    && user2.getSelectedPodcast().getOwner().equals(username)) {
                                return username + " can't be deleted.";
                            }
                        }
                        users.remove(user);
                        return username + " was successfully deleted.";
                    }
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }

    /**
     * adds an anouncement to a host's announcement list
     * @param username the username of the host
     * @param podcastName the podcast name
     * @param message the message
     * @return the message string
     */
    public static String addAnnouncement(final String username, final String podcastName,
                                         final String message) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Host host) {
                    return host.addAnnouncement(podcastName, message);
                } else {
                    return username + " is not a host.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }

    /**
     * removes an anouncement from a host's announcement list
     * @param username the username of the host
     * @param podcastName the podcast name
     * @return the message string
     */
    public static String removeAnnouncement(final String username, final String podcastName) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Host host) {
                    return host.removeAnnouncement(podcastName);
                } else {
                    return username + " is not a host.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }

    /**
     * shows the podcasts of a host
     * @param username the username of the host
     * @return the list of podcasts
     */
    public static ArrayList<Podcast> showPodcasts(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Host host) {
                    return host.showPodcasts();
                }
            }
        }
        return null;
    }

    /**
     * removes an album
     * @param username the username of the artist
     * @param albumName the album name
     * @return the message string
     */
    public static String removeAlbum(final String username, final String albumName) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Artist artist) {
                    return artist.removeAlbum(username, albumName);
                } else {
                    return username + " is not an artist.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }
    /**
     * removes a podcast
     * @param username the username of the host
     * @param podcastName the podcast name
     * @return the message string
     */
    public static String removePodcast(final String username, final String podcastName) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Host host) {
                    return host.removePodcast(podcastName);
                } else {
                    return username + " is not a host.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }
    /**
     * removes an event
     * @param username the username of the artist
     * @param eventName the event name
     * @return the message string
     */
    public static String removeEvent(final String username, final String eventName) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user instanceof Artist artist) {
                    return artist.removeEvent(username, eventName);
                } else {
                    return username + " is not an artist.";
                }
            }
        }
        return "The username " + username + " doesn't exist.";
    }

    /**
     * gets top 5 albums with most likes between all songs
     * @return the list of albums
     */
    public static ArrayList<String> getTop5Albums() {
        ArrayList<Album> albums = getAlbums();
        albums.sort(Comparator.comparingInt(Album::getLikes).reversed()
                .thenComparing(Album::getName));
        ArrayList<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : albums) {
            if (count >= LIMIT) {
                break;
            }
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }

    /**
     * gets top 5 artists with most likes between all songs
     * @return the list of artists
     */
    public static ArrayList<String> getTop5Artists() {
        ArrayList<Artist> artists = getArtists();
        artists.sort(Comparator.comparingInt(Artist::getLikes).reversed());
        ArrayList<String> topArtists = new ArrayList<>();
        int count = 0;
        for (Artist artist : artists) {
            if (count >= LIMIT) {
                break;
            }
            topArtists.add(artist.getUsername());
            count++;
        }
        return topArtists;
    }
}
