package app.user;

import app.EventMerch.Announcement;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;

@Getter
public class Host extends User{
    public Host(String username, int age, String city) {
            super(username, age, city);
        }
    private ArrayList<Podcast> podcasts = new ArrayList<>();
    private ArrayList<Announcement> announcements = new ArrayList<>();
    @Override
    public String switchConnectionStatus() {
        return null;
    }
    public String addPodcast(Podcast podcast) {
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
    public String addAnnouncement(String podcastName, String message) {
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
    public String removeAnnouncement(String podcastName) {
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
    public ArrayList<Podcast> showPodcasts() {
        if (podcasts == null) {
            podcasts = new ArrayList<>();
        }
        return podcasts;
    }
    public String removePodcast(String podcastName) {
        if (podcasts == null) {
            podcasts = new ArrayList<>();
        }
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(podcastName)) {
                podcasts.remove(podcast);
                return getUsername() + " deleted the podcast successfully.";
            }
        }
        return getUsername() + " doesn't have a podcast with the given name.";
    }
}
