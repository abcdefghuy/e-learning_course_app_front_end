package com.example.e_learningcourse.model;

public class Course {
    private final int id;
    private String title;
    private String instructor;
    private final int instructorAvatar;
    private int thumbnailResId;
    private final float rating;
    private double price;
    private final int progress;
    private boolean isBestSeller;
    private boolean isBookmarked;

    public Course(int id, String title, String instructor, int instructorAvatar, int thumbnailResId, float rating, double price, int progress, boolean isBestSeller, boolean isBookmarked) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.instructorAvatar = instructorAvatar;
        this.thumbnailResId = thumbnailResId;
        this.rating = rating;
        this.price = price;
        this.progress = progress;
        this.isBestSeller = isBestSeller;
        this.isBookmarked = isBookmarked;
    }

    public Course(int id, String title, String instructor, int instructorAvatar,
                  int thumbnailResId, float rating, double price) {
        this(id, title, instructor, instructorAvatar, thumbnailResId, rating, price, 0, false, false);
    }

    public Course(String title, String instructor, double price, int thumbnailResId, boolean isBestSeller) {
        this(0, title, instructor, 0, thumbnailResId, 0, price, 0, isBestSeller, false);
    }

    public Course(int id, String title, String instructor, int instructorAvatar, int thumbnailResId, float rating, double price, int progress) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.instructorAvatar = instructorAvatar;
        this.thumbnailResId = thumbnailResId;
        this.rating = rating;
        this.price = price;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getInstructorAvatar() {
        return instructorAvatar;
    }

    public int getThumbnailResId() {
        return thumbnailResId;
    }

    public void setThumbnailResId(int thumbnailResId) {
        this.thumbnailResId = thumbnailResId;
    }

    public float getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProgress() {
        return progress;
    }

    public boolean isBestSeller() {
        return isBestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        isBestSeller = bestSeller;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public Course setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
        return null;
    }
}