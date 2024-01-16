package app.player;

import app.user.AudioListener;

public interface ObservablePlayer {
    /**
     * Adds an audio listener to the list of observers.
     *
     * @param listener The audio listener to be added.
     */
    void add(AudioListener listener);
    /**
     * Removes an audio listener from the list of observers.
     *
     * @param listener The audio listener to be removed.
     */
    void remove(AudioListener listener);
    /**
     * Notifies all registered audio listeners about player events.
     */
    void notifyListeners();
}
