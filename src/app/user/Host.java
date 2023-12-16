package app.user;

import app.Admin;
import app.EventMerch.Announcement;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;

@Getter
public class Host extends User {
    public Host(final String username, final int age, final String city) {
            super(username, age, city);
        }
    private ArrayList<Podcast> podcasts = new ArrayList<>();
    private ArrayList<Announcement> announcements = new ArrayList<>();

    /**
     * since the host is njot a regular user it can't switch the connection status
     * @return
     */
    @Override
    public String switchConnectionStatus() {
        return null;
    }

    /**
     * adds a new podcast to the host's list of podcasts
     * @param podcast the podcast to be added
     * @return the messahe
     */
    public String addPodcast(final Podcast podcast) {
        if (podcasts == null) {
            podcasts = new ArrayList<>();
        }
        for (Podcast podcast1 : podcasts) {
            if (podcast1.getName().equals(podcast.getName())) {
                return getUsername() + " has another podcast with the same name.";
            }
        }
        HashSet<String> uniqueEpisodes = new HashSet<>();
        for (Episode episode : podcast.getEpisodes()) {
            if (!uniqueEpisodes.add(episode.getName())) {
                return getUsername() + " has the same episode at least twice in this podcast.";
            }
        }
        podcasts.add(podcast);
        return getUsername() + " has added new podcast successfully.";
    }

    /**
     * adds a new announcement to the host's list of announcements
     * @param podcastName the name of the podcast
     * @param message the message of the announcement
     * @return the message
     */
    public String addAnnouncement(final String podcastName, final String message) {
        if (announcements == null) {
            announcements = new ArrayList<>();
        }
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(podcastName)) {
                return getUsername() + " has another announcement with the same name.";
            }
        }
        announcements.add(new Announcement(podcastName, message));
        return getUsername() + " has successfully added new announcement.";
    }

    /**
     * removes an announcement from the host's list of announcements
     * @param podcastName the name of the podcast
     * @return the message
     */
    public String removeAnnouncement(final String podcastName) {
        if (announcements == null) {
            announcements = new ArrayList<>();
        }
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(podcastName)) {
                announcements.remove(announcement);
                return getUsername() + " has successfully deleted the announcement.";
            }
        }
        return getUsername() + " has no announcement with the given name.";
    }

    /**
     * shows the podcasts of the host
     * @return
     */
    public ArrayList<Podcast> showPodcasts() {
        if (podcasts == null) {
            podcasts = new ArrayList<>();
        }
        return podcasts;
    }

    /**
     * removes a podcast from the host's list of podcasts
     * @param podcastName the name of the podcast
     * @return the message
     */
    public String removePodcast(final String podcastName) {
        if (podcasts == null) {
            podcasts = new ArrayList<>();
        }
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(podcastName)) {
                for (User user : Admin.getUsers()) {
                    if (user.getPlayer().getSource() != null
                            && user.getPlayer().getSource().getAudioCollection().getName()
                                    .equals(podcastName)) {
                        return getUsername() + " can't delete this podcast.";
                    }
                }
                podcasts.remove(podcast);
                return getUsername() + " deleted the podcast successfully.";
            }
        }
        return getUsername() + " doesn't have a podcast with the given name.";
    }
}
