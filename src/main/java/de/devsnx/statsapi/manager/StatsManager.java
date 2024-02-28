package de.devsnx.statsapi.manager;

import de.devsnx.statsapi.StatsAPI;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author Marvin Hänel (DevSnx)
 * @since 28.02.2024 23:33
 */

public class StatsManager {

    private StatsAPI statsAPI;
    private RedissonClient redis;

    public StatsManager(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
        this.redis = statsAPI.getRedisson();
    }

    public void getUser(UUID uuid, Consumer<Stats> consumer) {

        RBucket<String> future = redis.getBucket("stats:" + uuid);
        future.getAsync().thenAcceptAsync((userJson -> consumer.accept(statsAPI.getGson().fromJson(userJson, Stats.class))));

    }

    public void updateUser(Stats stats) {
        stats.update();
    }

}
