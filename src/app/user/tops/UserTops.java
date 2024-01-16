package app.user.tops;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class UserTops {
    private Map<String, Integer> topArtists;
    private Map<String, Integer> topGenres;
    private Map<String, Integer> topSongs;
    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topEpisodes;
    private static int topArtistsSize = 5;

    /**
     * Constructs an empty {@code UserTops} object
     * with initialized topArtists, topGenres, topSongs, topAlbums, and topEpisodes maps.
     */
    public UserTops() {
        topArtists = new HashMap<>();
        topGenres = new HashMap<>();
        topSongs = new HashMap<>();
        topAlbums = new HashMap<>();
        topEpisodes = new HashMap<>();
    }
    /**
     * Updates the top statistics for songs based on provided artist, genre, song, and album names.
     *
     * @param artistName The name of the artist associated with the song.
     * @param genreName  The name of the genre associated with the song.
     * @param songName   The name of the song to update.
     * @param albumName  The name of the album associated with the song.
     */
    public void updateTopsSong(final String artistName, final String genreName,
                               final String songName, final String albumName) {
        topArtists.merge(artistName, 1, Integer::sum);
        topGenres.merge(genreName, 1, Integer::sum);
        topSongs.merge(songName, 1, Integer::sum);
        topAlbums.merge(albumName, 1, Integer::sum);
    }

    /**
     * Updates the top statistics for episodes based on provided episode name.
     *
     * @param episodeName The name of the episode to update.
     */
    public void updateTopsEpisode(final String episodeName) {
        topEpisodes.merge(episodeName, 1, Integer::sum);
    }

    /**
     * Checks if the top statistics for artists, genres, songs, albums, and episodes are empty.
     *
     * @return {@code true} if all top statistics are empty, {@code false} otherwise.
     */
    public boolean emptyTops() {
        return topArtists.isEmpty() && topGenres.isEmpty()
                && topSongs.isEmpty() && topAlbums.isEmpty() && topEpisodes.isEmpty();
    }

    /**
     * Retrieves the top 5 artists with their corresponding counts.
     *
     * @return A linked hash map containing the top 5 artists and their counts.
     */
    public LinkedHashMap<String, Integer> getTop5Artists() {
        return topArtists.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(topArtistsSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    /**
     * Retrieves the top 5 artists with their corresponding counts.
     *
     * @return A linked hash map containing the top 5 artists and their counts.
     */
    public  LinkedHashMap<String, Integer> getTop5Genres() {
        return topGenres.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(topArtistsSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    /**
     * Retrieves the top 5 songs with their corresponding counts.
     *
     * @return A linked hash map containing the top 5 songs and their counts.
     */
    public  LinkedHashMap<String, Integer> getTop5Songs() {
        return topSongs.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(topArtistsSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    /**
     * Retrieves the top 5 albums with their corresponding counts.
     *
     * @return A linked hash map containing the top 5 albums and their counts.
     */
    public LinkedHashMap<String, Integer> getTop5Albums() {
        return topAlbums.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(topArtistsSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    /**
     * Retrieves the top 5 episodes with their corresponding counts.
     *
     * @return A linked hash map containing the top 5 episodes and their counts.
     */
    public LinkedHashMap<String, Integer> getTop5Episodes() {
        return topEpisodes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(topArtistsSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

}
