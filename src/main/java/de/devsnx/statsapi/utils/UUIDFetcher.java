package de.devsnx.statsapi.utils;

import de.devsnx.statsapi.StatsAPI;
import org.redisson.api.RedissonClient;
import java.util.UUID;

/**
 * @author Marvin Hänel (DevSnx)
 * @since 28.02.2024 23:36
 */

public class UUIDFetcher {

    private StatsAPI statsAPI;
    private RedissonClient redis;

    public UUIDFetcher(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
        this.redis = statsAPI.getRedisson();
    }

    public void saveUUID (UUID uuid, String name) {
        String mapKey = "fetcher:UUIDtoName";
        redis.getMap(mapKey).put(uuid.toString(), name);
    }

    public void saveName (String name, UUID uuid) {
        String mapKey = "fetcher:nameToUUID";
        redis.getMap(mapKey).put(name.toLowerCase(), uuid.toString());
    }

    public UUID getUUIDFromName (String name) {
        String mapKey = "fetcher:nameToUUID";
        return (redis.getMap(mapKey).get(name) != null) ? UUID.fromString(redis.getMap(mapKey).get(name).toString()) : null;
    }

    public String getNameFromUUID (UUID uuid) {
        String mapKey = "fetcher:UUIDtoName";
        return (redis.getMap(mapKey).get(uuid) != null) ? redis.getMap(mapKey).get(uuid).toString() : null;
    }
}