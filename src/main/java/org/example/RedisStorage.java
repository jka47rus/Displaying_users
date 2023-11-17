package org.example;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Collection;
import java.util.Date;

import static java.lang.System.out;

public class RedisStorage {

    private RedissonClient redisson;
    private final static String KEY = "Date_Users";
    private RScoredSortedSet<String> users;

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        RKeys rKeys = redisson.getKeys();
        users = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void shutdown() {
        redisson.shutdown();
    }

    //ZADD
    void add(int totalUsers) {
        for (int user = 1; user <= totalUsers; user++) {
            users.add(new Date().getTime(), String.valueOf(user));
        }
    }

    //ZRANGE
    void showBy(int id) {
        Collection<String> list = users.valueRange(id, id);
        list.forEach((s) -> out.println("> Пользователь " + s + " оплатил платную услугу"));
    }

    void showByTurn(int user) {
        Collection<String> list = users.valueRange(user, user);
        list.forEach((s) -> out.println("— На главной странице показываем пользователя " + s));
    }

}
