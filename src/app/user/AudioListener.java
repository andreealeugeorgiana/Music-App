package app.user;

public interface AudioListener {
    /**
     * Notifies the listener about updates in listens.
     * Implementing classes should define their specific behavior for handling listen updates.
     */
    void updateListens();
    /**
     * Notifies the listener about new notifications.
     *
     * @param name        The name associated with the notification.
     * @param description The description of the notification.
     *                     Implementing classes should use or display this information as needed.
     */
    void updateNotifications(String name, String description);
}
