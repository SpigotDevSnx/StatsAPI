package de.devsnx.statsapi.manager;

import de.devsnx.statsapi.StatsAPI;

import java.util.UUID;

/**
 * @author Marvin Hänel (DevSnx)
 * @since 28.02.2024 23:34
 */

public class Stats {

    private UUID uuid;
    private String name;
    private Integer kills;
    private Integer deaths;
    private Integer rank;
    private Integer wins;
    private Integer games;
    private Integer openChests;

    public Stats(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.kills = 0;
        this.deaths = 0;
        this.rank = -1;
        this.wins = 0;
        this.games = 0;
        this.openChests = 0;
    }

    public void update() {
        String userJson = StatsAPI.statsAPI.getGson().toJson(this);
        StatsAPI.statsAPI.getRedisson().getBucket("stats:" + uuid).setAsync(userJson);
    }

    public void delete() {
        String key = "stats:" + uuid;
        StatsAPI.statsAPI.getRedisson().getBucket(key).deleteAsync();
    }

}