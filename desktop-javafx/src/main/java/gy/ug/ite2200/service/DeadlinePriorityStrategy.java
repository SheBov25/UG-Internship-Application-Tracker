package gy.ug.ite2200.service;

import gy.ug.ite2200.model.ApplicationStatus;
import gy.ug.ite2200.model.InternshipApplication;
import gy.ug.ite2200.model.Priority;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DeadlinePriorityStrategy implements PriorityStrategy {

    @Override
    public Priority calculate(InternshipApplication application) {

        if (application.getStatus() == ApplicationStatus.ACCEPTED ||
                application.getStatus() == ApplicationStatus.REJECTED ||
                application.getStatus() == ApplicationStatus.WITHDRAWN) {

            return Priority.LOW;
        }

        LocalDate closingDate = application.getClosingDate();

        if (closingDate == null) {
            return Priority.LOW;
        }

        long daysRemaining =
                ChronoUnit.DAYS.between(LocalDate.now(), closingDate);

        if (daysRemaining <= 7) {
            return Priority.HIGH;
        } else if (daysRemaining <= 14) {
            return Priority.MEDIUM;
        } else {
            return Priority.LOW;
        }
    }
}