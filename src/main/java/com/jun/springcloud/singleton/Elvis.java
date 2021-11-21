package com.jun.springcloud.singleton;

// Singleton with static factory
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis() {}
    private String profession = "Singer";

    public static Elvis getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "Elvis{" +
                "profession='" + profession + '\'' +
                '}';
    }
}
