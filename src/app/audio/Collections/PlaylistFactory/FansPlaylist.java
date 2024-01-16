package app.audio.Collections.PlaylistFactory;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.User;

import java.util.Comparator;
import java.util.List;

public class FansPlaylist implements Recommendation {

    @Override
    public Playlist createRecommendation(final User user) {
        Artist artist = Admin.getInstance().getArtist(((Song) user.getPlayer()
                .getSource().getAudioFile()).getArtist());
        Playlist playlist = new Playlist("%s Fan Club recommendations"
                .formatted(artist.getUsername()), user.getUsername());
            List<Song> top5Songs = user.getLikedSongs()
                    .stream()
                    .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                    .limit(5)
                    .toList();
            top5Songs.forEach(playlist::addSong);
        return playlist;
    }
}
