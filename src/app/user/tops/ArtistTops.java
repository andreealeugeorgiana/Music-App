package app.user.tops;

import app.user.UserAbstract;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class ArtistTops{
    @Getter
    private Map<String, Integer> topAlbums;
    @Getter
    private Map<String, Integer> topSongs;
    private Map<String, Integer> topFans;

    public ArtistTops() {
        topAlbums = new HashMap<>();
        topSongs = new HashMap<>();
        topFans = new HashMap<>();
    }
    public void updateTops(String albumName, String songName, String username) {
        topAlbums.merge(albumName, 1, Integer::sum);
        topSongs.merge(songName, 1, Integer::sum);
        topFans.merge(username, 1, Integer::sum);
    }

    public boolean emptyTops() {
        return topAlbums.isEmpty() || topSongs.isEmpty() || topFans.isEmpty();
    }

    public LinkedHashMap<String, Integer> getTopAlbums() {
        return topAlbums.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    public LinkedHashMap<String, Integer> getTopSongs() {
        return topSongs.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    public List<String> getTopFans() {
        return topFans.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
