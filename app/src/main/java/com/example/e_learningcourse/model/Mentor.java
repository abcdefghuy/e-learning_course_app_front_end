package com.example.e_learningcourse.model;

public class Mentor {
    private final int id;
    private final String name;
    private final int avatarResId;

    public Mentor(int id, String name, int avatarResId) {
        this.id = id;
        this.name = name;
        this.avatarResId = avatarResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvatarResId() {
        return avatarResId;
    }
} 