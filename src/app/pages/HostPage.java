package app.pages;

import app.EventMerch.Announcement;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.user.Host;
import app.user.User;

import java.util.List;

public class HostPage extends Page {
    public HostPage(final User user) {
        super(user);
    }
    /**
     * Displays the content of the page.
     *
     * @return the content of the page
     */
    @Override
    public String displayContent() {
        Host selectedHost = user.getSelectedHost();
        if (selectedHost == null) {
            return "No host selected.";
        }
        // gpt
        StringBuilder podcastsOutput = new StringBuilder();
        List<Podcast> podcasts = selectedHost.getPodcasts();
        for (int i = 0; i < podcasts.size(); i++) {
            Podcast podcast = podcasts.get(i);
            podcastsOutput.append(podcast.getName()).append(":\n\t[");
            for (Episode episode : podcast.getEpisodes()) {
                podcastsOutput.append(episode.getName()).append(" - ").
                        append(episode.getDescription()).append(", ");
            }
            if (!podcastsOutput.isEmpty()) {
                podcastsOutput.setLength(podcastsOutput.length() - 2);
            }
            // Check if it's the last podcast
            if (i == podcasts.size() - 1) {
                podcastsOutput.append("]\n");
            } else {
                podcastsOutput.append("]\n, ");
            }
        }

        StringBuilder announcementsOutput = new StringBuilder();
        for (Announcement announcement : selectedHost.getAnnouncements()) {
            announcementsOutput.append(announcement.getName()).append(":\n\t").
                    append(announcement.getDescription()).append("\n");
        }

        return String.format("Podcasts:\n\t[%s]\n\nAnnouncements:\n\t[%s]",
                podcastsOutput, announcementsOutput);
    }
}
