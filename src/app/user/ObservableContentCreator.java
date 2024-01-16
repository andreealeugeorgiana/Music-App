package app.user;

public interface ObservableContentCreator {
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
     * Notifies all registered audio listeners about content-related events.
     *
     * @param name        The name associated with the event.
     * @param description The description of the event.
     *                    Implementing classes should use or display this information as needed.
     */
    void notifyListeners(String name, String description);
}
