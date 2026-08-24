package gy.ug.ite2200.model;

import java.time.LocalDate;

public class ServiceRequest {
    private final int id;
    private String title;
    private String category;
    private String description;
    private RequestStatus status;
    private final LocalDate createdDate;

    public ServiceRequest(int id, String title, String category, String description, RequestStatus status, LocalDate createdDate) {
        this.id = id; this.title = title; this.category = category; this.description = description;
        this.status = status; this.createdDate = createdDate;
    }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    public LocalDate getCreatedDate() { return createdDate; }
}
