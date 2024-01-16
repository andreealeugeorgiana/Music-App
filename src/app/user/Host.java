package app.user;

import app.audio.Collections.Podcast;
import app.pages.HostPage;
import app.user.contentCreatorSpecifics.Announcement;
import app.user.tops.HostTops;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Host.
 */
public final class Host extends ContentCreator implements ObservableContentCreator {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;
    @Getter
    @Setter
    private HostTops hostTops;
    @Getter
    @Setter
    private List<AudioListener> subscribers;

    /**
     * Instantiates a new Host.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Host(final String username, final int age, final String city) {
        super(username, age, city);
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
        hostTops = new HostTops();
        subscribers = new ArrayList<>();

        super.setPage(new HostPage(this));
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * Sets podcasts.
     *
     * @param podcasts the podcasts
     */
    public void setPodcasts(final ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    /**
     * Gets announcements.
     *
     * @return the announcements
     */
    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Sets announcements.
     *
     * @param announcements the announcements
     */
    public void setAnnouncements(final ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }

    /**
     * Gets podcast.
     *
     * @param podcastName the podcast name
     * @return the podcast
     */
    public Podcast getPodcast(final String podcastName) {
        for (Podcast podcast: podcasts) {
            if (podcast.getName().equals(podcastName)) {
                return podcast;
            }
        }

        return null;
    }

    /**
     * Gets announcement.
     *
     * @param announcementName the announcement name
     * @return the announcement
     */
    public Announcement getAnnouncement(final String announcementName) {
        for (Announcement announcement: announcements) {
            if (announcement.getName().equals(announcementName)) {
                return announcement;
            }
        }

        return null;
    }

    @Override
    public String userType() {
        return "host";
    }

    @Override
    public void add(final AudioListener listener) {
        subscribers.add(listener);
    }

    @Override
    public void remove(final AudioListener listener) {
        subscribers.remove(listener);
    }

    @Override
    public void notifyListeners(final String name, final String description) {
        for (AudioListener subscriber : subscribers) {
            subscriber.updateNotifications(name, description);
        }
    }
}
