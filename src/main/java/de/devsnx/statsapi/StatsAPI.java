package de.devsnx.statsapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.devsnx.statsapi.manager.StatsManager;
import de.devsnx.statsapi.utils.UUIDFetcher;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

@Getter
public final class StatsAPI {

    public static StatsAPI statsAPI;
    private StatsManager statsManager;
    private RedissonClient redisson;
    private Gson gson;
    private UUIDFetcher uuidFetcher;

    public StatsAPI(String host, Integer port, String username, String password, Integer database){

        statsAPI = this;
        gson = new GsonBuilder().setPrettyPrinting().create();

        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":"+ port).setUsername(username).setPassword(password).setDatabase(database);
        config.setCodec(new JsonJacksonCodec());
        redisson = Redisson.create(config);

        statsManager = new StatsManager(statsAPI);
        uuidFetcher = new UUIDFetcher(statsAPI);
    }

}