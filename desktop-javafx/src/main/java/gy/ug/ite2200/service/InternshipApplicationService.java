package gy.ug.ite2200.service;

import gy.ug.ite2200.model.ApplicationStatus;
import gy.ug.ite2200.model.InternshipApplication;
import gy.ug.ite2200.model.Priority;
import gy.ug.ite2200.repository.InternshipApplicationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class InternshipApplicationService {

    private final InternshipApplicationRepository repository;
    private final PriorityStrategy priorityStrategy;

    public InternshipApplicationService(
            InternshipApplicationRepository repository,
            PriorityStrategy priorityStrategy) {

        this.repository = repository;
        this.priorityStrategy = priorityStrategy;
    }

    public List<InternshipApplication> getAllApplications() {
        return repository.findAll();
    }

    public Optional<InternshipApplication> getApplicationById(int id) {
        return repository.findById(id);
    }

    public InternshipApplication saveApplication(
            InternshipApplication application) {

        validateApplication(application);

        Priority calculatedPriority =
                priorityStrategy.calculate(application);

        application.setPriority(calculatedPriority);

        return repository.save(application);
    }

    public InternshipApplication updateStatus(
            int id,
            ApplicationStatus newStatus) {

        InternshipApplication application =
                repository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Application not found."
                                ));

        application.setStatus(newStatus);

        Priority calculatedPriority =
                priorityStrategy.calculate(application);

        application.setPriority(calculatedPriority);

        return repository.save(application);
    }
    public InternshipApplication updateApplication(
            InternshipApplication application) {

        if (application.getId() <= 0) {
            throw new IllegalArgumentException(
                    "A valid application must be selected for update."
            );
        }

        if (repository.findById(application.getId()).isEmpty()) {
            throw new IllegalArgumentException(
                    "Application not found."
            );
        }

        validateApplication(application);

        Priority calculatedPriority =
                priorityStrategy.calculate(application);

        application.setPriority(calculatedPriority);

        return repository.save(application);
    }

    public boolean deleteApplication(int id) {
        return repository.deleteById(id);
    }

    private void validateApplication(
            InternshipApplication application) {

        if (application.getCompanyName() == null ||
                application.getCompanyName().isBlank()) {

            throw new IllegalArgumentException(
                    "Company name is required."
            );
        }

        if (application.getPositionTitle() == null ||
                application.getPositionTitle().isBlank()) {

            throw new IllegalArgumentException(
                    "Position title is required."
            );
        }

        if (application.getCategory() == null) {
            throw new IllegalArgumentException(
                    "Internship category is required."
            );
        }

        if (application.getApplicationDate() == null) {
            throw new IllegalArgumentException(
                    "Application date is required."
            );
        }

        if (application.getClosingDate() == null) {
            throw new IllegalArgumentException(
                    "Closing date is required."
            );
        }

        if (application.getClosingDate()
                .isBefore(application.getApplicationDate())) {

            throw new IllegalArgumentException(
                    "Closing date cannot be before application date."
            );
        }

        if (application.getStatus() == null) {
            throw new IllegalArgumentException(
                    "Application status is required."
            );
        }

        validateEmail(application.getContactEmail());

        validateInterviewDate(
                application.getApplicationDate(),
                application.getInterviewDate()
        );

        validateNotes(application.getNotes());
    }

    private void validateEmail(String email) {

        if (email == null || email.isBlank()) {
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException(
                    "Please enter a valid contact email."
            );
        }
    }

    private void validateInterviewDate(
            LocalDate applicationDate,
            LocalDate interviewDate) {

        if (interviewDate == null) {
            return;
        }

        if (interviewDate.isBefore(applicationDate)) {
            throw new IllegalArgumentException(
                    "Interview date cannot be before application date."
            );
        }
    }

    private void validateNotes(String notes) {

        if (notes != null && notes.length() > 500) {
            throw new IllegalArgumentException(
                    "Notes cannot exceed 500 characters."
            );
        }
    }
}