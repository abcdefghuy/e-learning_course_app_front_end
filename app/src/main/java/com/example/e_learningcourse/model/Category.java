package com.example.e_learningcourse.model;

public class Category {
    private final int id;
    private final String name;
    private final int iconResId;

    public Category(int id, String name, int iconResId) {
        this.id = id;
        this.name = name;
        this.iconResId = iconResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }
} 