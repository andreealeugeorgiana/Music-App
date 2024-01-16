package app.audio.Collections.PlaylistFactory;

import app.audio.Collections.Playlist;
import app.user.User;

import java.util.*;
import java.util.stream.Collectors;

import app.audio.Files.Song;

public class RandomPlaylist implements Recommendation {
    private static List<String> determineTopGenres(final User user) {
        Map<String, Long> genreCounts = user.getLikedSongs().stream()
                .collect(Collectors.groupingBy(Song::getGenre, Collectors.counting()));

        user.getPlaylists().stream()
                .flatMap(playlist -> playlist.getSongs().stream())
                .forEach(song -> genreCounts.merge(song.getGenre(), 1L, Long::sum));

        user.getFollowedPlaylists().stream()
                .flatMap(playlist -> playlist.getSongs().stream())
                .forEach(song -> genreCounts.merge(song.getGenre(), 1L, Long::sum));

        return genreCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Playlist createRecommendation(final User user) {
        Playlist playlist = new Playlist("%s's recommendations"
                .formatted(user.getUsername()), user.getUsername());
        List<String> topGenres = determineTopGenres(user);

        if (topGenres.isEmpty()) {
            return playlist;
        }

        String firstGenre = topGenres.get(0);

        List<Song> topSongsFromFirstGenre = user.getLikedSongs().stream()
                .filter(song -> song.getGenre().equals(firstGenre))
                .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                .limit(5)
                .toList();

        topSongsFromFirstGenre.forEach(playlist::addSong);

        if (topGenres.size() > 1) {
            String secondGenre = topGenres.get(1);

            List<Song> topSongsFromSecondGenre = user.getLikedSongs().stream()
                    .filter(song -> song.getGenre().equals(secondGenre))
                    .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                    .limit(3)
                    .toList();

            topSongsFromSecondGenre.forEach(playlist::addSong);
        }

        if (topGenres.size() > 2) {
            String thirdGenre = topGenres.get(2);

            List<Song> topSongsFromThirdGenre = user.getLikedSongs().stream()
                    .filter(song -> song.getGenre().equals(thirdGenre))
                    .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                    .limit(2)
                    .toList();

            topSongsFromThirdGenre.forEach(playlist::addSong);
        }

        return playlist;
    }
}
