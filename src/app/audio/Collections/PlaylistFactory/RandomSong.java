package app.audio.Collections.PlaylistFactory;

import app.Admin;
import app.audio.Files.Song;
import app.user.User;

import java.util.List;
import java.util.Random;

public class RandomSong implements Recommendation {

    @Override
    public Song createRecommendation(final User user) {
        int seed = user.getPlayer().getCurrentAudioFile()
                .getDuration() - user.getPlayerStats().getRemainedTime();

        if (seed >= 30) {
            String genre = ((Song) user.getPlayer().getCurrentAudioFile()).getGenre();

            List<Song> songsInGenre = Admin.getInstance().getSongs().stream()
                    .filter(song -> song.getGenre().equals(genre))
                    .toList();

            if (!songsInGenre.isEmpty()) {
                Random random = new Random(seed);
                int randomIndex = random.nextInt(songsInGenre.size());

                return songsInGenre.get(randomIndex);
            }
        }

        return null;
    }
}
