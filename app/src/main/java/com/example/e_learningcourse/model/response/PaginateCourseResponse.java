package com.example.e_learningcourse.model.response;

import java.util.List;

public class PaginateCourseResponse {
    private List<CourseDetailResponse> courses;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean last;

    public List<CourseDetailResponse> getCourse() {
        return courses;
    }

    public void setCourse(List<CourseDetailResponse> course) {
        this.courses = course;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
