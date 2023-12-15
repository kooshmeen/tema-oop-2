package fileio.input;

import lombok.Getter;

import java.util.ArrayList;

public final class CommandInput {
    private String command;
    private String username;
    private Integer timestamp;
    private String type; // song / playlist / podcast
    private FiltersInput filters; // pentru search
    private Integer itemNumber; // pentru select
    private Integer repeatMode; // pentru repeat
    private Integer playlistId; // pentru add/remove song
    private String playlistName; // pentru create playlist
    private Integer seed; // pentru shuffle
    private Integer age;
    private String city;
    private String name;
    @Getter
    private Integer releaseYear;
    @Getter
    private String description;
    @Getter
    private ArrayList<SongInput> songs;
    @Getter
    private DateInput date;
    @Getter
    private Integer price;
    private ArrayList<EpisodeInput> episodes;
    private String nextPage;

    public CommandInput() {
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Integer timestamp) {
        this.timestamp = timestamp;
    }

    public FiltersInput getFilters() {
        return filters;
    }

    public void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(final Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Integer getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(final Integer repeatMode) {
        this.repeatMode = repeatMode;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(final Integer playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(final Integer seed) {
        this.seed = seed;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(final Integer age) {
        this.age = age;
    }
    public String getCity() {
        return city;
    }
    public void setCity(final String city) {
        this.city = city;
    }
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public Integer getReleaseYear(final Integer releaseYear) {
        return releaseYear;
    }
    public void setReleaseYear(final Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
    public String getDescription(final String description) {
        return description;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    public ArrayList<SongInput> getSongs(final ArrayList<SongInput> songs) {
        return songs;
    }
    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }
    public DateInput getDate(final DateInput date) {
        return date;
    }
    public void setDate(final DateInput date) {
        this.date = date;
    }
    public Integer getPrice(final Integer price) {
        return price;
    }
    public void setPrice(final Integer price) {
        this.price = price;
    }
    public ArrayList<EpisodeInput> getEpisodes(final ArrayList<EpisodeInput> episodes) {
        return episodes;
    }
    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }
    public String getNextPage(final String nextPage) {
        return nextPage;
    }
    public void setNextPage(final String nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public String toString() {
        return "CommandInput{"
                + "command='" + command + '\''
                + ", username='" + username + '\''
                + ", timestamp=" + timestamp
                + ", type='" + type + '\''
                + ", filters=" + filters
                + ", itemNumber=" + itemNumber
                + ", repeatMode=" + repeatMode
                + ", playlistId=" + playlistId
                + ", playlistName='" + playlistName + '\''
                + ", seed=" + seed
                + '}';
    }
}
