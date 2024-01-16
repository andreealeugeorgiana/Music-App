package app.user.tops;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class ArtistTops {
    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topSongs;
    private Map<String, Integer> topFans;
    static private int topAlbumsSize = 5;

    /**
     * Constructs an empty {@code ArtistTops} object
     * with initialized topAlbums, topSongs, and topFans maps.
     */
    public ArtistTops() {
        topAlbums = new HashMap<>();
        topSongs = new HashMap<>();
        topFans = new HashMap<>();
    }
    /**
     * Updates the top statistics for albums, songs, and fans based on provided names.
     *
     * @param albumName The name of the album to update.
     * @param songName  The name of the song to update.
     * @param username  The username of the fan to update.
     */
    public void updateTops(final String albumName, final String songName, final String username) {
        topAlbums.merge(albumName, 1, Integer::sum);
        topSongs.merge(songName, 1, Integer::sum);
        topFans.merge(username, 1, Integer::sum);
    }

    /**
     * Checks if all top statistics (albums, songs, and fans) are empty.
     *
     * @return {@code true} if all top statistics are empty, {@code false} otherwise.
     */
    public boolean emptyTops() {
        return topAlbums.isEmpty() && topSongs.isEmpty() && topFans.isEmpty();
    }

    /**
     * Retrieves the top 5 albums with their corresponding counts.
     *
     * @return A linked hash map containing the top 5 albums and their counts.
     */
    public LinkedHashMap<String, Integer> getTop5Albums() {
        return topAlbums.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(topAlbumsSize)
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
    public LinkedHashMap<String, Integer> getTop5Songs() {
        return topSongs.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(topAlbumsSize)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    /**
     * Retrieves the top 5 fans based on their corresponding counts.
     *
     * @return A list containing the usernames of the top 5 fans.
     */
    public List<String> getTop5Fans() {
        return topFans.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(topAlbumsSize).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
