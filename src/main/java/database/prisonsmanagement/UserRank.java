package com.sda.alina.exercises.prisonsmanagement;

public enum UserRank {
    LIEUTENANT("Lieutenant"),
    CAPTAIN("Captain"),
    MAJOR("Major"),
    COLONEL("Colonel"),
    BRIGADIER("Brigadier"),
    GENERAL("General");

    private String rank;

    UserRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }
}
