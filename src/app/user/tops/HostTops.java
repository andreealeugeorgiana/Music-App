package app.user.tops;

import app.user.UserAbstract;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HostTops{
    private Map<String, Integer> topEpisodes;

    public HostTops() {
        topEpisodes = new HashMap<>();
    }
    public void updateTops(String episodeName) {
        topEpisodes.merge(episodeName, 1, Integer::sum);
    }

    public boolean emptyTops() {
        return topEpisodes.isEmpty();
    }

    public  LinkedHashMap<String, Integer> getTopEpisodes() {
        return topEpisodes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new  // Supplier for the map implementation
                ));
    }
}
