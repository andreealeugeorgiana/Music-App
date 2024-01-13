package app.user.tops;

import app.user.User;
import app.user.UserAbstract;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class UserTops{
    private Map<String, Integer> topArtists;
    private Map<String, Integer> topGenres;
    private Map<String, Integer> topSongs;
    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topEpisodes;

    public UserTops() {
        topArtists = new HashMap<>();
        topGenres = new HashMap<>();
        topSongs = new HashMap<>();
        topAlbums = new HashMap<>();
        topEpisodes = new HashMap<>();
    }
    public void updateTopsSong(String artistName, String genreName, String songName, String albumName) {
        topArtists.merge(artistName, 1, Integer::sum);
        topGenres.merge(genreName, 1, Integer::sum);
        topSongs.merge(songName, 1, Integer::sum);
        topAlbums.merge(albumName, 1, Integer::sum);
    }

    public void updateTopsEpisode(String episodeName) {
        topEpisodes.merge(episodeName, 1, Integer::sum);
    }

    public boolean emptyTops() {
        return topArtists.isEmpty() || topGenres.isEmpty() || topSongs.isEmpty() || topAlbums.isEmpty() || topEpisodes.isEmpty();
    }

    public LinkedHashMap<String, Integer> getTopArtists() {
        return topArtists.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    public  LinkedHashMap<String, Integer> getTopGenres() {
        return topGenres.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

    public  LinkedHashMap<String, Integer> getTopSongs() {
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

    public LinkedHashMap<String, Integer> getTopEpisodes() {
        return topEpisodes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }

}
