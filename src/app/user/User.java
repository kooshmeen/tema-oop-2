package app.user;

import app.Admin;
import app.audio.Collections.*;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pages.ArtistPage;
import app.pages.HostPage;
import app.pages.HomePage;
import app.pages.LikedContentPage;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User.
 */
public class User {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    @Getter
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    private boolean lastSearchedArtist; // if last searched was an artist
    private boolean lastSearchedAlbum; // if last searched was an album
    private boolean lastSearchedHost; // if last searched was a host
    @Getter
    private boolean connectionOnline;
    private boolean loaded;
    private enum PageType {
        HOME_PAGE,
        LIKED_CONTENT_PAGE,
        ARTIST_PAGE,
        HOST_PAGE
    }
    private PageType currentPage;
    private final HomePage homePage;
    private final LikedContentPage likedContentPage;
    private final ArtistPage artistPage;
    private final HostPage hostPage;
    @Getter
    private Artist selectedArtist = null;
    private List<Artist> lastSearchedArtists;
    private List<Album> lastSearchedAlbums;
    private List<Host> lastSearchedHosts;
    @Getter
    private Album selectedAlbum = null;
    @Getter
    private Host selectedHost = null;
    @Getter
    private Playlist selectedPlaylist = null;
    @Getter
    private Podcast selectedPodcast = null;

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        connectionOnline = true;
        homePage = new HomePage(this);
        likedContentPage = new LikedContentPage(this);
        artistPage = new ArtistPage(this);
        hostPage = new HostPage(this);
        currentPage = PageType.HOME_PAGE;
        lastSearchedArtist = false;
        lastSearchedAlbum = false;
        lastSearchedHost = false;
        lastSearchedArtists = new ArrayList<>();
        lastSearchedAlbums = new ArrayList<>();
        loaded = false;
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type) {
        if (!type.equals("artist") && !type.equals("album")
                && !type.equals("host")) { // search normal (melodii, podcasturi, playlisturi)
            lastSearchedArtists = new ArrayList<>();
            selectedAlbum = null;
            selectedPlaylist = null;
            selectedPodcast = null;
            searchBar.clearSelection();
            player.stop();
            ArrayList<String> results;
            if (connectionOnline) {
                lastSearched = true;
                results = new ArrayList<>();
                List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

                for (LibraryEntry libraryEntry : libraryEntries) {
                    results.add(libraryEntry.getName());
                }
            } else {
                results = null;
            }
            return results;
        } else if (type.equals("artist")) { // search artist
            lastSearchedArtists = new ArrayList<>();
            searchBar.clearSelection();
            player.stop();
            ArrayList<String> results = new ArrayList<>();
            if (connectionOnline) {
                lastSearched = true;
                lastSearchedArtist = true;
                results = new ArrayList<>();
                for (Artist artist : Admin.getArtists()) {
                    if (artist.getUsername().startsWith(filters.getName())) {
                        results.add(artist.getUsername());
                        lastSearchedArtists.add(artist);
                    }
                }
            } else {
                lastSearched = true;
                lastSearchedHost = true;
            }
            return results;
        } else if (type.equals("album")) { // search album
            lastSearchedAlbums = new ArrayList<>();
            selectedAlbum = null;
            searchBar.clearSelection();
            player.stop();
            ArrayList<String> results = new ArrayList<>();
            if (connectionOnline) {
                lastSearched = true;
                lastSearchedAlbum = true;
                results = new ArrayList<>();
                for (Album album : Admin.getAlbums()) {
                    if (filters.getName() != null && album.getName().startsWith(filters.
                            getName())) {
                        results.add(album.getName());
                        lastSearchedAlbums.add(album);
                    }
                    if (filters.getArtist() != null && album.getOwner().startsWith(filters.
                            getOwner())
                            || album.getOwner().equals(filters.getOwner())) {
                        results.add(album.getName());
                        lastSearchedAlbums.add(album);
                    }
                }
            } else {
                results = null;
            }
            return results;
        } else {
            lastSearchedHosts = new ArrayList<>();
            searchBar.clearSelection();
            player.stop();
            ArrayList<String> results = new ArrayList<>();
            if (connectionOnline) {
                lastSearched = true;
                lastSearchedHost = true;
                results = new ArrayList<>();
                for (Host host : Admin.getHosts()) {
                    if (host.getUsername().startsWith(filters.getName())) {
                        results.add(host.getUsername());
                        lastSearchedHosts.add(host);
                    }
                }
            } else {
                results = null;
            }
            return results;
        }
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        selectedAlbum = null;
        selectedPlaylist = null;
        selectedPodcast = null;
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;
        if (lastSearchedArtist) {
            lastSearchedArtist = false;
            if (itemNumber > lastSearchedArtists.size()) {
                return "The selected ID is too high.";
            }
            Artist artist = lastSearchedArtists.get(itemNumber - 1);
            selectedArtist = artist;
            currentPage = PageType.ARTIST_PAGE;
            return "Successfully selected %s".formatted(artist.getUsername() + "'s page.");
        } else if (lastSearchedAlbum) {
            lastSearchedAlbum = false;
            if (itemNumber > lastSearchedAlbums.size()) {
                return "The selected ID is too high.";
            }
            Album album = lastSearchedAlbums.get(itemNumber - 1);
            selectedAlbum = album;
            return "Successfully selected %s".formatted(album.getName() + ".");
        } else if (lastSearchedHost) {
            lastSearchedHost = false;
            if (itemNumber > lastSearchedHosts.size()) {
                return "The selected ID is too high.";
            }
            Host host = lastSearchedHosts.get(itemNumber - 1);
            selectedHost = host;
            currentPage = PageType.HOST_PAGE;
            return "Successfully selected %s".formatted(host.getUsername() + "'s page.");
        } else {
            LibraryEntry selected = searchBar.select(itemNumber);

            if (selected == null) {
                return "The selected ID is too high.";
            }
            if (selected instanceof Playlist) {
                selectedPlaylist = (Playlist) selected;
            }
            if (selected instanceof Podcast) {
                selectedPodcast = (Podcast) selected;
            }
            return "Successfully selected %s.".formatted(selected.getName());
        }
    }

    /**
     * Load string.
     *
     * @return the string
     */
    public String load() {
        loaded = false;
        if (selectedAlbum != null) {

            Playlist albumAsPlaylist = new Playlist(selectedAlbum.getName(),
                    selectedAlbum.getOwner(),0);
            // since loading a playlist is already implemented
            // we can create a playlist object that copies the album's songs
            for (Song song : selectedAlbum.getSongs()) {
                albumAsPlaylist.addSong(song);
            }
            player.setSource(albumAsPlaylist, "playlist");
            searchBar.clearSelection();
            player.pause();
            loaded = true;
            return "Playback loaded successfully.";
        }
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("song")
                && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();
        loaded = true;

        return "Playback loaded successfully.";
    }

    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> {
                repeatStatus = "no repeat";
            }
            case REPEAT_ONCE -> {
                repeatStatus = "repeat once";
            }
            case REPEAT_ALL -> {
                repeatStatus = "repeat all";
            }
            case REPEAT_INFINITE -> {
                repeatStatus = "repeat infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                repeatStatus = "repeat current song";
            }
            default -> {
                repeatStatus = "";
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist")) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (player.getCurrentAudioFile() == null || !loaded) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();
        if (!connectionOnline) {
            return username + " is offline.";
        }
        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }
        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (player.getCurrentAudioFile() == null || !loaded) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Gets player stats.
     *
     * @return the player stats
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Show preferred songs array list.
     *
     * @return the array list
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Gets preferred genre.
     *
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * switches from online to offline
     * @return the message
     */
    public String switchConnectionStatus() {
        if (!connectionOnline) {
            connectionOnline = true;
            player.switchOffline();
        } else {
            connectionOnline = false;
            player.switchOffline();
        }
        return username + " has changed status successfully.";
    }

    /**
     * prints the current page
     * @return the contents of the page
     */
    public String printCurrentPage() {
        if (!connectionOnline) {
            return username + " is offline.";
        }
        if (currentPage == PageType.HOME_PAGE) {
            return homePage.displayContent();
        } else if (currentPage == PageType.LIKED_CONTENT_PAGE) {
            return likedContentPage.displayContent();
        } else if (currentPage == PageType.ARTIST_PAGE) {
            return artistPage.displayContent();
        } else {
            return hostPage.displayContent();
        }
    }

    /**
     * changes the page between homepahe and likedcontentpage, if online
     * @param nextPage the page to change to
     * @return the message
     */
    public String changePage(final String nextPage) {
        selectedHost = null;
        selectedArtist = null;
        if (!connectionOnline) {
            return username + " is offline.";
        }
        if (nextPage.equals("Home")) {
            currentPage = PageType.HOME_PAGE;
            return username + " accessed " + nextPage + " successfully.";
        } else if (nextPage.equals("LikedContent")) {
            currentPage = PageType.LIKED_CONTENT_PAGE;
            return username + " accessed " + nextPage + " successfully.";
        }
        return username + " is trying to access a non-existent page.";
    }

    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(final int time) {
        player.simulatePlayer(time);
    }
}
