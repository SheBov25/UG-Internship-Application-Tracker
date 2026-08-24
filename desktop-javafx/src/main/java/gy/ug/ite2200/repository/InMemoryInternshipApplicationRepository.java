package gy.ug.ite2200.repository;

import gy.ug.ite2200.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryInternshipApplicationRepository
        implements InternshipApplicationRepository {

    private final Map<Integer, InternshipApplication> data =
            new LinkedHashMap<>();

    private final AtomicInteger nextId =
            new AtomicInteger(3);

    public InMemoryInternshipApplicationRepository() {

        save(new InternshipApplication(
                1,
                "GTT",
                "Software Development Intern",
                InternshipCategory.INFORMATION_TECHNOLOGY,
                LocalDate.now().minusDays(7),
                LocalDate.now().plusDays(14),
                ApplicationStatus.APPLIED,
                Priority.MEDIUM,
                "HR Department",
                "careers@gtt.co.gy",
                null,
                "Application submitted online."
        ));

        save(new InternshipApplication(
                2,
                "Demerara Bank",
                "Information Systems Intern",
                InternshipCategory.INFORMATION_TECHNOLOGY,
                LocalDate.now().minusDays(3),
                LocalDate.now().plusDays(7),
                ApplicationStatus.INTERESTED,
                Priority.HIGH,
                "Human Resources",
                "hr@example.com",
                null,
                "Prepare CV and application letter."
        ));
    }

    @Override
    public List<InternshipApplication> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<InternshipApplication> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public InternshipApplication save(
            InternshipApplication application) {

        InternshipApplication value = application;

        if (application.getId() <= 0) {

            value = new InternshipApplication(
                    nextId.getAndIncrement(),
                    application.getCompanyName(),
                    application.getPositionTitle(),
                    application.getCategory(),
                    application.getApplicationDate(),
                    application.getClosingDate(),
                    application.getStatus(),
                    application.getPriority(),
                    application.getContactPerson(),
                    application.getContactEmail(),
                    application.getInterviewDate(),
                    application.getNotes()
            );
        }

        data.put(value.getId(), value);

        return value;
    }

    @Override
    public boolean deleteById(int id) {
        return data.remove(id) != null;
    }
}