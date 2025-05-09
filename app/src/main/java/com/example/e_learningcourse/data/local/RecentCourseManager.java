package com.example.e_learningcourse.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.e_learningcourse.model.response.CourseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecentCourseManager {
    private static final String PREF_NAME = "recent_courses_pref";
    private static final String KEY_COURSES = "recent_courses";
    private static final String KEY_SEARCHES = "recent_searches";
    private static final int MAX_COURSE_SIZE = 5;
    private static final int MAX_SEARCH_SIZE = 10;

    // ===========================
    // COURSE - Đã xem gần đây
    // ===========================
    public static void saveCourse(Context context, CourseResponse course) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString(KEY_COURSES, "[]");
        Type type = new TypeToken<List<CourseResponse>>() {}.getType();
        List<CourseResponse> courses = gson.fromJson(json, type);

        if (courses == null) courses = new ArrayList<>();

        // Xoá nếu đã tồn tại
        courses.removeIf(c -> c.getCourseId() == course.getCourseId());

        // Thêm vào đầu
        courses.add(0, course);

        // Giới hạn số lượng
        if (courses.size() > MAX_COURSE_SIZE) {
            courses = courses.subList(0, MAX_COURSE_SIZE);
        }

        prefs.edit().putString(KEY_COURSES, gson.toJson(courses)).apply();
    }

    public static List<CourseResponse> getRecentCourses(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_COURSES, "[]");
        Type type = new TypeToken<List<CourseResponse>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void clearRecentCourses(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_COURSES, "[]")
                .apply();
    }

    // ===========================
    // SEARCH - Từ khoá đã tìm
    // ===========================
    public static void saveSearchKeyword(Context context, String keyword) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString(KEY_SEARCHES, "[]");
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> keywords = gson.fromJson(json, type);

        if (keywords == null) keywords = new ArrayList<>();

        // Xoá nếu đã tồn tại
        keywords.removeIf(k -> k.equalsIgnoreCase(keyword.trim()));

        // Thêm lên đầu
        keywords.add(0, keyword.trim());

        // Giới hạn số lượng
        if (keywords.size() > MAX_SEARCH_SIZE) {
            keywords = keywords.subList(0, MAX_SEARCH_SIZE);
        }

        prefs.edit().putString(KEY_SEARCHES, gson.toJson(keywords)).apply();
    }

    public static List<String> getRecentSearches(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_SEARCHES, "[]");
        Type type = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void clearSearches(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().remove(KEY_SEARCHES).apply();
    }

    public static void removeSearch(Context context, String keyword) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        List<String> currentList = getRecentSearches(context);
        if (currentList != null && !currentList.isEmpty()) {
            String keywordToRemove = keyword.trim().toLowerCase();
            currentList.removeIf(k -> k.trim().equalsIgnoreCase(keywordToRemove));

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_SEARCHES, new Gson().toJson(currentList));
            editor.apply();
        }
    }
}
