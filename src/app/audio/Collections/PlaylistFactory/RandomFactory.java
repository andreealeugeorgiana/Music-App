package app.audio.Collections.PlaylistFactory;

public class RandomFactory {
    /**
     * Creates a recommendation object based on the specified type.
     *
     * @param type The type of recommendation to be created.
     * @return A Recommendation object corresponding to the
     * given type or null if the type is invalid.
     */
    public Recommendation createRecommendation(final String type) {
        return switch (type) {
            case "random_song" -> new RandomSong();
            case "fans_playlist" -> new FansPlaylist();
            case "random_playlist" -> new RandomPlaylist();
            default -> null;
        };
    }
}
