package app.user.tops;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HostTops {
    private Map<String, Integer> topEpisodes;
    @Getter
    private Map<String, Integer> topFans;

    /**
     * Constructs an empty {@code HostTops} object with initialized topEpisodes and topFans maps.
     */
    public HostTops() {
        topEpisodes = new HashMap<>();
        topFans = new HashMap<>();
    }
    /**
     * Updates the top statistics for episodes and fans based on provided names.
     *
     * @param episodeName The name of the episode to update.
     * @param username    The username of the fan to update.
     */
    public void updateTops(final String episodeName, final String username) {
        topEpisodes.merge(episodeName, 1, Integer::sum);
        topFans.merge(username, 1, Integer::sum);
    }
    /**
     * Checks if the top episodes statistics are empty.
     *
     * @return {@code true} if top episodes statistics are empty, {@code false} otherwise.
     */
    public boolean emptyTops() {
        return topEpisodes.isEmpty();
    }

    /**
     * Retrieves the top 5 episodes with their corresponding counts.
     *
     * @return A linked hash map containing the top 5 episodes and their counts.
     */
    public  LinkedHashMap<String, Integer> getTop5Episodes() {
        return topEpisodes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }
}
