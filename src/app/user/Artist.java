package app.user;

import java.util.ArrayList;
import java.util.List;

import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Files.Song;
import app.pages.ArtistPage;
import app.user.contentCreatorSpecifics.Event;
import app.user.contentCreatorSpecifics.Merchandise;
import app.user.tops.ArtistTops;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Artist.
 */
public final class Artist extends ContentCreator implements ObservableContentCreator {
    private ArrayList<Album> albums;
    private ArrayList<Merchandise> merch;
    private ArrayList<Event> events;
    @Getter
    @Setter
    private ArtistTops artistTops;
    @Getter
    @Setter
    private int ranking;
    @Getter
    @Setter
    private Double songRevenue;
    @Getter
    @Setter
    private Double merchRevenue;
    @Getter
    @Setter
    private String mostProfitableSong;
    @Getter
    @Setter
    private List<AudioListener> subscribers;


    /**
     * Instantiates a new Artist.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
        artistTops = new ArtistTops();
        ranking = 0;
        songRevenue = 0.0;
        merchRevenue = 0.0;
        mostProfitableSong = "N/A";
        subscribers = new ArrayList<>();

        super.setPage(new ArtistPage(this));
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * Gets merch.
     *
     * @return the merch
     */
    public ArrayList<Merchandise> getMerch() {
        return merch;
    }

    /**
     * Gets events.
     *
     * @return the events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Gets event.
     *
     * @param eventName the event name
     * @return the event
     */
    public Event getEvent(final String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }

        return null;
    }

    /**
     * Gets album.
     *
     * @param albumName the album name
     * @return the album
     */
    public Album getAlbum(final String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }

        return null;
    }

    /**
     * Gets all songs.
     *
     * @return the all songs
     */
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        albums.forEach(album -> songs.addAll(album.getSongs()));

        return songs;
    }

    /**
     * Show albums array list.
     *
     * @return the array list
     */
    public ArrayList<AlbumOutput> showAlbums() {
        ArrayList<AlbumOutput> albumOutput = new ArrayList<>();
        for (Album album : albums) {
            albumOutput.add(new AlbumOutput(album));
        }

        return albumOutput;
    }

    /**
     * Get user type
     *
     * @return user type string
     */
    public String userType() {
        return "artist";
    }
    /**
     * Adds the specified revenue to the merchandise revenue.
     *
     * @param revenue The revenue to be added to the merchandise revenue.
     */
    public void addToMerchRevenue(final Double revenue) {
        merchRevenue += revenue;
    }
    /**
     * Updates the most profitable song based on the current revenue of each song.
     */
    public void changeMostProfitableSong() {
        Double max = 0.0;
        for (Song song : getAllSongs()) {
            if (song.getRevenue() > max) {
                setMostProfitableSong(song.getName());
                max = song.getRevenue();
            }
        }
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
