package app.user;

import app.Admin;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pages.HomePage;
import app.pages.LikedContentPage;
import app.pages.Page;
import app.pages.navigation.PageNavigation;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.user.tops.UserTops;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * The type User.
 */
public final class User extends UserAbstract implements AudioListener {
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    @Getter
    private ArrayList<Playlist> recommendedPlaylists;
    @Getter
    private ArrayList<Song> recommendedSongs;
    @Getter
    private final Player player;
    @Getter
    private boolean status;
    private final SearchBar searchBar;
    private boolean lastSearched;
    @Getter
    @Setter
    private Page currentPage;
    @Getter
    @Setter
    private HomePage homePage;
    @Getter
    @Setter
    private LikedContentPage likedContentPage;
    @Getter
    @Setter
    private UserTops userTops;
    @Getter
    @Setter
    private boolean premium;
    @Getter
    private List<LinkedHashMap<String, String>> notifications;
    @Getter
    private List<String> myMerch;
    @Getter
    private PageNavigation pageNavigation;
    @Getter
    @Setter
    private LibraryEntry lastRecommended;

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public User(final String username, final int age, final String city) {
        super(username, age, city);
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        player.add(this); // adaug user-ul care observa schimbarea player-ului
        searchBar = new SearchBar(username);
        lastSearched = false;
        status = true;
        userTops = new UserTops();
        premium = false;
        notifications = new ArrayList<>();
        myMerch = new ArrayList<>();
        recommendedPlaylists = new ArrayList<>();
        recommendedSongs = new ArrayList<>();
        lastRecommended = null;

        homePage = new HomePage(this);
        currentPage = homePage;
        likedContentPage = new LikedContentPage(this);
        pageNavigation = new PageNavigation(this);
    }

    @Override
    public String userType() {
        return "user";
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();

        if (type.equals("artist") || type.equals("host")) {
            List<ContentCreator> contentCreatorsEntries =
            searchBar.searchContentCreator(filters, type);

            for (ContentCreator contentCreator : contentCreatorsEntries) {
                results.add(contentCreator.getUsername());
            }
        } else {
            List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

            for (LibraryEntry libraryEntry : libraryEntries) {
                results.add(libraryEntry.getName());
            }
        }
        return results;
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        if (searchBar.getLastSearchType().equals("artist")
            || searchBar.getLastSearchType().equals("host")) {
            ContentCreator selected = searchBar.selectContentCreator(itemNumber);

            if (selected == null) {
                return "The selected ID is too high.";
            }

            currentPage = selected.getPage();
            return "Successfully selected %s's page.".formatted(selected.getUsername());
        } else {
            LibraryEntry selected = searchBar.select(itemNumber);

            if (selected == null) {
                return "The selected ID is too high.";
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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
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
        updateListens();
        player.pause();

        return "Playback loaded successfully.";
    }

    public String loadRecommendations() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (lastRecommended == null) {
            return "No recommendations available.";
        }

        if (lastRecommended.getName().contains("playlist")) {
            player.setSource(recommendedPlaylists.
                    get(recommendedPlaylists.size() - 1), "playlist");
        } else {
            player.setSource(recommendedSongs.
                    get(recommendedPlaylists.size()), "song");
        }
        updateListens();
        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist")
            && !player.getType().equals("album")) {
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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")
            && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, getUsername(), timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

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
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(getUsername())) {
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
     * Switch status.
     */
    public void switchStatus() {
        status = !status;
    }

    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(final int time) {
        if (!status) {
            return;
        }

        player.simulatePlayer(time);
    }
    /**
     * Calculates revenue for the given artist based on user's top statistics.
     *
     * @param artist The artist for whom revenue is calculated.
     */
    private void calculateRevenue(final Artist artist) {
        if (premium) {
            double artistSongs = 0.0;
            for (Map.Entry<String, Integer> entry : userTops.getTopArtists().entrySet()) {
                if (artist.equals(Admin.getInstance().getArtist(entry.getKey()))) {
                    artistSongs += entry.getValue();
                }
            }
            double totalSongs = 0.0;
            for (Map.Entry<String, Integer> entry : userTops.getTopSongs().entrySet()) {
                totalSongs += entry.getValue();
            }
            Double value = 1000000.0 * (double) (artistSongs / totalSongs);
            ((Song) player.getCurrentAudioFile()).setRevenue(value);
            artist.setSongRevenue(value);
            artist.changeMostProfitableSong();
        }
    }
    /**
     * Updates the revenue based on the current player state and source type.
     */
    public void updateRevenue() {
        if (player.getSource().getType().equals(Enums.PlayerSourceType.LIBRARY)
                || player.getSource().getType().equals(Enums.PlayerSourceType.ALBUM)
                || player.getSource().getType().equals(Enums.PlayerSourceType.PLAYLIST)) {
            Artist artist = Admin.getInstance()
                    .getArtist(((Song) player.getCurrentAudioFile()).getArtist());
            calculateRevenue(artist);
        }
    }

    @Override
    public void updateListens() {
        if (player.getCurrentAudioFile() == null) {
            return;
        } else if (player.getSource().getType().equals(Enums.PlayerSourceType.LIBRARY)
                || player.getSource().getType().equals(Enums.PlayerSourceType.ALBUM)
                || player.getSource().getType().equals(Enums.PlayerSourceType.PLAYLIST)) {
            Artist artist = Admin.getInstance()
                    .getArtist(((Song) player.getCurrentAudioFile()).getArtist());
            String songName = player.getCurrentAudioFile().getName();
            String albumName = ((Song) player.getCurrentAudioFile()).getAlbum();

            userTops.updateTopsSong(artist.getUsername(),
                    ((Song) player.getCurrentAudioFile()).getGenre(), songName, albumName);
            artist.getArtistTops().updateTops(albumName, songName, getUsername());

            if (!Admin.getInstance().getListenedArtists().contains(artist)) {
                Admin.getInstance().getListenedArtists().add(artist);
            }
            updateRevenue();
            artist.setListeners(artist.getArtistTops().getTopFans().size());

        } else if (player.getSource().getType().equals(Enums.PlayerSourceType.PODCAST)) {
            Host host = Admin.getInstance()
                    .getHost(player.getSource().getAudioCollection().getOwner());

            userTops.updateTopsEpisode(player.getCurrentAudioFile().getName());
            if (host != null) {
                host.getHostTops()
                        .updateTops(player.getCurrentAudioFile().getName(), getUsername());
                host.setListeners(host.getHostTops().getTopFans().size());
            }
        }
    }

    @Override
    public void updateNotifications(final String name, final String description) {
        LinkedHashMap<String, String> notificationMap = new LinkedHashMap<>();
        notificationMap.put("name", name);
        notificationMap.put("description", description);
        notifications.add(notificationMap);
    }
    /**
     * Clears all notifications associated with the user.
     */
    public void clearNotifications() {
        notifications.clear();
    }
    /**
     * Adds a merchandise item to the user's collection.
     *
     * @param merch The merchandise item to add.
     */
    public void addMerch(final String merch) {
        myMerch.add(merch);
    }

    /**
     * Adds a recommended playlist to the user's recommended playlists.
     *
     * @param playlist The recommended playlist to add.
     */
    public void addRecommendedPlaylist(final Playlist playlist) {
        if (playlist != null) {
            recommendedPlaylists.add(playlist);
        }
    }
    /**
     * Adds a recommended song to the user's recommended songs.
     *
     * @param song The recommended song to add.
     */
    public void addRecommendedSong(final Song song) {
        if (song != null) {
            recommendedSongs.add(song);
        }
    }

}
