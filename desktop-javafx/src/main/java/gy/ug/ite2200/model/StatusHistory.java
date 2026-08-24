package gy.ug.ite2200.model;

import java.time.LocalDateTime;

public class StatusHistory {

    private final ApplicationStatus status;
    private final LocalDateTime changedAt;

    public StatusHistory(
            ApplicationStatus status,
            LocalDateTime changedAt) {

        this.status = status;
        this.changedAt = changedAt;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    @Override
    public String toString() {
        return changedAt + " - " + status;
    }
}