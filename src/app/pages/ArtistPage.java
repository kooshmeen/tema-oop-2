package app.pages;

import app.audio.Collections.Album;
import app.user.Artist;
import app.user.User;

import java.util.List;

public class ArtistPage extends Page {
    public ArtistPage(final User user) {
        super(user);
    }
    /**
     * @return the content of the page
     */
    @Override
    public String displayContent() {
        Artist selectedArtist = user.getSelectedArtist();
        if (selectedArtist == null) {
            return "No artist selected.";
        }
        // gpt
        List<String> albums = selectedArtist.getAlbums().stream()
                .map(Album::getName)
                .toList();
        List<String> merch = selectedArtist.getMerch().stream()
                .map(merch1 -> merch1.getName() + " - " + merch1.getPrice()
                        + ":\n\t" + merch1.getDescription())
                .toList();
        List<String> events = selectedArtist.getEvents().stream()
                .map(event -> event.getName() + " - " + event.getDayWithZero()
                        + "-" + event.getMonthWithZero()
                        + "-" + event.getDate().getYear()
                        + ":\n\t" + event.getDescription())
                .toList();
        String albumsOutput = String.join(", ", albums);
        String merchOutput = String.join(", ", merch);
        String eventsOutput = String.join(", ", events);
        return String.format("Albums:\n\t[%s]\n\nMerch:\n\t[%s]\n\nEvents:\n\t[%s]",
                albumsOutput, merchOutput, eventsOutput);
    }
}
