package app.player;

import app.user.AudioListener;

public interface ObservablePlayer {
    public void add (final AudioListener listener);
    public void remove (final AudioListener listener);
    public void notifyListeners ();
}
