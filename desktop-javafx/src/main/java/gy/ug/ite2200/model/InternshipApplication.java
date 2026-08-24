package gy.ug.ite2200.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InternshipApplication {

    private int id;
    private String companyName;
    private String positionTitle;
    private InternshipCategory category;
    private LocalDate applicationDate;
    private LocalDate closingDate;
    private ApplicationStatus status;
    private final List<StatusHistory> statusHistory =
            new ArrayList<>();
    private Priority priority;
    private String contactPerson;
    private String contactEmail;
    private LocalDate interviewDate;
    private String notes;


    public InternshipApplication(
            int id,
            String companyName,
            String positionTitle,
            InternshipCategory category,
            LocalDate applicationDate,
            LocalDate closingDate,
            ApplicationStatus status,
            Priority priority,
            String contactPerson,
            String contactEmail,
            LocalDate interviewDate,
            String notes) {

        this.id = id;
        this.companyName = companyName;
        this.positionTitle = positionTitle;
        this.category = category;
        this.applicationDate = applicationDate;
        this.closingDate = closingDate;
        this.status = status;
        if (status != null) {
            statusHistory.add(
                    new StatusHistory(
                            status,
                            LocalDateTime.now()
                    )
            );
        }
        this.priority = priority;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
        this.interviewDate = interviewDate;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public InternshipCategory getCategory() {
        return category;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public String getNotes() {
        return notes;
    }

    public List<StatusHistory> getStatusHistory() {
        return new ArrayList<>(statusHistory);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public void setCategory(InternshipCategory category) {
        this.category = category;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public void setStatus(ApplicationStatus status) {

        if (status != null && status != this.status) {

            this.status = status;

            statusHistory.add(
                    new StatusHistory(
                            status,
                            LocalDateTime.now()
                    )
            );
        }
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return companyName + " - " + positionTitle;
    }
}