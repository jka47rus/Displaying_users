package org.example;
import java.util.Random;


public class Main {

    // Колличество человек
    private static final int USERS = 20;


    public static void main(String[] args) throws InterruptedException {
        RedisStorage redis = new RedisStorage();
        redis.init();
        redis.add(USERS);
//        int i = 10;

        while (true) {
            for (int user = 0; user <= USERS; user++) {
                if (user != 0 && user % 9 == 0) {
                    int user_id = new Random().nextInt(20);  // в одном из 10 случаев
                    boolean userNotUserId = user_id == user;
                    if (userNotUserId == true) {
                        user_id = new Random().nextInt(20);
                    }
                    if (user_id != user) {
                        redis.showBy(user_id);
                    }
                }
                redis.showByTurn(user);
            }

            Thread.sleep(1000);
            System.out.println();

        }
    }


}