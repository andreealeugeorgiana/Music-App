package app.audio.Collections.PlaylistFactory;

import app.audio.LibraryEntry;
import app.user.User;

public interface Recommendation {
    LibraryEntry createRecommendation(User user);
}
