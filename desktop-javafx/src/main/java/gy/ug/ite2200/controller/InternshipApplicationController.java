package gy.ug.ite2200.controller;

import gy.ug.ite2200.model.ApplicationStatus;
import gy.ug.ite2200.model.InternshipApplication;
import gy.ug.ite2200.model.InternshipCategory;
import gy.ug.ite2200.repository.InMemoryInternshipApplicationRepository;
import gy.ug.ite2200.repository.InternshipApplicationRepository;
import gy.ug.ite2200.service.DeadlinePriorityStrategy;
import gy.ug.ite2200.service.InternshipApplicationService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class InternshipApplicationController {

    @FXML
    private TableView<InternshipApplication> applicationTable;

    @FXML
    private TableColumn<InternshipApplication, Number> idColumn;

    @FXML
    private TableColumn<InternshipApplication, String> companyColumn;

    @FXML
    private TableColumn<InternshipApplication, String> positionColumn;

    @FXML
    private TableColumn<InternshipApplication, String> statusColumn;

    @FXML
    private TableColumn<InternshipApplication, String> priorityColumn;

    @FXML
    private TextField companyField;

    @FXML
    private TextField positionField;

    @FXML
    private ComboBox<InternshipCategory> categoryComboBox;

    @FXML
    private DatePicker applicationDatePicker;

    @FXML
    private DatePicker closingDatePicker;

    @FXML
    private ComboBox<ApplicationStatus> statusComboBox;

    @FXML
    private TextField contactPersonField;

    @FXML
    private TextField contactEmailField;

    @FXML
    private DatePicker interviewDatePicker;

    @FXML
    private TextArea notesArea;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> filterStatusComboBox;

    @FXML
    private ComboBox<String> filterPriorityComboBox;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private ListView<String> statusHistoryListView;

    private InternshipApplicationService service;

    private final ObservableList<InternshipApplication> applications =
            FXCollections.observableArrayList();

    private FilteredList<InternshipApplication> filteredApplications;

    private SortedList<InternshipApplication> sortedApplications;

    private InternshipApplication selectedApplication;

    @FXML
    private Label totalApplicationsLabel;

    @FXML
    private Label appliedCountLabel;

    @FXML
    private Label interviewCountLabel;

    @FXML
    private Label offeredCountLabel;

    @FXML
    private Label highPriorityCountLabel;

    @FXML
    public void initialize() {

        InternshipApplicationRepository repository =
                new InMemoryInternshipApplicationRepository();

        service = new InternshipApplicationService(
                repository,
                new DeadlinePriorityStrategy()
        );

        categoryComboBox.setItems(
                FXCollections.observableArrayList(
                        InternshipCategory.values()
                )
        );

        statusComboBox.setItems(
                FXCollections.observableArrayList(
                        ApplicationStatus.values()
                )
        );

        filterStatusComboBox.setItems(
                FXCollections.observableArrayList(
                        "ALL",
                        "INTERESTED",
                        "APPLIED",
                        "SHORTLISTED",
                        "INTERVIEW",
                        "OFFERED",
                        "ACCEPTED",
                        "REJECTED",
                        "WITHDRAWN"
                )
        );

        filterStatusComboBox.setValue("ALL");

        filterPriorityComboBox.setItems(
                FXCollections.observableArrayList(
                        "ALL",
                        "LOW",
                        "MEDIUM",
                        "HIGH"
                )
        );

        filterPriorityComboBox.setValue("ALL");

        sortComboBox.setItems(
                FXCollections.observableArrayList(
                        "Company Name",
                        "Closing Date",
                        "Priority"
                )
        );

        sortComboBox.setValue("Company Name");

        applicationDatePicker.setValue(LocalDate.now());

        setupTable();

        setupFilteringAndSorting();

        refreshTable();

        applicationTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {

                    if (newSelection != null) {
                        loadApplicationIntoForm(newSelection);
                    }
                });
    }

    private void setupTable() {

        idColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getId()
                )
        );

        companyColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCompanyName()
                )
        );

        positionColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getPositionTitle()
                )
        );

        statusColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getStatus().toString()
                )
        );

        priorityColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getPriority().toString()
                )
        );
    }

    private void setupFilteringAndSorting() {

        filteredApplications =
                new FilteredList<>(applications, p -> true);

        sortedApplications =
                new SortedList<>(filteredApplications);

        applicationTable.setItems(sortedApplications);

        searchField.textProperty().addListener(
                (observable, oldValue, newValue) ->
                        applyFilters()
        );

        filterStatusComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        applyFilters()
        );

        filterPriorityComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        applyFilters()
        );

        sortComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        applySorting()
        );

        applySorting();
    }

    private void applyFilters() {

        filteredApplications.setPredicate(application -> {

            String searchText =
                    searchField.getText() == null
                            ? ""
                            : searchField.getText()
                            .toLowerCase()
                            .trim();

            boolean matchesSearch =
                    application.getCompanyName()
                            .toLowerCase()
                            .contains(searchText)
                            ||
                            application.getPositionTitle()
                                    .toLowerCase()
                                    .contains(searchText);

            String selectedStatus =
                    filterStatusComboBox.getValue();

            boolean matchesStatus =
                    selectedStatus == null
                            || selectedStatus.equals("ALL")
                            || application.getStatus()
                            .toString()
                            .equals(selectedStatus);

            String selectedPriority =
                    filterPriorityComboBox.getValue();

            boolean matchesPriority =
                    selectedPriority == null
                            || selectedPriority.equals("ALL")
                            || application.getPriority()
                            .toString()
                            .equals(selectedPriority);

            return matchesSearch
                    && matchesStatus
                    && matchesPriority;
        });
    }

    private void applySorting() {

        String selectedSort =
                sortComboBox.getValue();

        if (selectedSort == null) {
            return;
        }

        switch (selectedSort) {

            case "Closing Date":
                sortedApplications.setComparator(
                        (a, b) ->
                                a.getClosingDate()
                                        .compareTo(
                                                b.getClosingDate()
                                        )
                );
                break;

            case "Priority":
                sortedApplications.setComparator(
                        (a, b) ->
                                b.getPriority()
                                        .compareTo(
                                                a.getPriority()
                                        )
                );
                break;

            default:
                sortedApplications.setComparator(
                        (a, b) ->
                                a.getCompanyName()
                                        .compareToIgnoreCase(
                                                b.getCompanyName()
                                        )
                );
        }
    }

    private void loadApplicationIntoForm(
            InternshipApplication application) {

        selectedApplication = application;

        companyField.setText(
                application.getCompanyName()
        );

        positionField.setText(
                application.getPositionTitle()
        );

        categoryComboBox.setValue(
                application.getCategory()
        );

        applicationDatePicker.setValue(
                application.getApplicationDate()
        );

        closingDatePicker.setValue(
                application.getClosingDate()
        );

        statusComboBox.setValue(
                application.getStatus()
        );

        contactPersonField.setText(
                application.getContactPerson()
        );

        contactEmailField.setText(
                application.getContactEmail()
        );

        interviewDatePicker.setValue(
                application.getInterviewDate()
        );

        notesArea.setText(
                application.getNotes()
        );

        statusHistoryListView.getItems().clear();

        application.getStatusHistory().forEach(history ->
                statusHistoryListView.getItems().add(
                        history.toString()
                )
        );

        messageLabel.setText(
                "Application selected for editing."
        );
    }

    @FXML
    private void handleAddApplication() {

        try {

            InternshipApplication application =
                    new InternshipApplication(
                            0,
                            companyField.getText(),
                            positionField.getText(),
                            categoryComboBox.getValue(),
                            applicationDatePicker.getValue(),
                            closingDatePicker.getValue(),
                            statusComboBox.getValue(),
                            null,
                            contactPersonField.getText(),
                            contactEmailField.getText(),
                            interviewDatePicker.getValue(),
                            notesArea.getText()
                    );

            service.saveApplication(application);

            clearForm();

            statusHistoryListView.getItems().clear();

            refreshTable();

            messageLabel.setText(
                    "Internship application added successfully."
            );

        } catch (IllegalArgumentException e) {

            messageLabel.setText(
                    e.getMessage()
            );
        }
    }

    @FXML
    private void handleUpdateApplication() {

        if (selectedApplication == null) {

            messageLabel.setText(
                    "Please select an application to update."
            );

            return;
        }

        try {

            selectedApplication.setCompanyName(
                    companyField.getText()
            );

            selectedApplication.setPositionTitle(
                    positionField.getText()
            );

            selectedApplication.setCategory(
                    categoryComboBox.getValue()
            );

            selectedApplication.setApplicationDate(
                    applicationDatePicker.getValue()
            );

            selectedApplication.setClosingDate(
                    closingDatePicker.getValue()
            );

            selectedApplication.setStatus(
                    statusComboBox.getValue()
            );

            selectedApplication.setContactPerson(
                    contactPersonField.getText()
            );

            selectedApplication.setContactEmail(
                    contactEmailField.getText()
            );

            selectedApplication.setInterviewDate(
                    interviewDatePicker.getValue()
            );

            selectedApplication.setNotes(
                    notesArea.getText()
            );

            service.updateApplication(
                    selectedApplication
            );

            clearForm();

            refreshTable();

            messageLabel.setText(
                    "Application updated successfully."
            );

        } catch (IllegalArgumentException e) {

            messageLabel.setText(
                    e.getMessage()
            );
        }
    }

    @FXML
    private void handleDeleteApplication() {

        InternshipApplication selected =
                applicationTable.getSelectionModel().getSelectedItem();

        if (selected == null) {

            messageLabel.setText(
                    "Please select an application to delete."
            );

            return;
        }

        service.deleteApplication(
                selected.getId()
        );

        clearForm();

        refreshTable();

        messageLabel.setText(
                "Application deleted successfully."
        );
    }

    private void updateDashboard() {

        long total =
                applications.size();

        long applied =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.APPLIED)
                        .count();

        long interviews =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.INTERVIEW)
                        .count();

        long offers =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.OFFERED
                                        ||
                                        application.getStatus()
                                                == ApplicationStatus.ACCEPTED)
                        .count();

        long highPriority =
                applications.stream()
                        .filter(application ->
                                application.getPriority()
                                        == gy.ug.ite2200.model.Priority.HIGH)
                        .count();

        totalApplicationsLabel.setText(
                String.valueOf(total)
        );

        appliedCountLabel.setText(
                String.valueOf(applied)
        );

        interviewCountLabel.setText(
                String.valueOf(interviews)
        );

        offeredCountLabel.setText(
                String.valueOf(offers)
        );

        highPriorityCountLabel.setText(
                String.valueOf(highPriority)
        );
    }

    private void refreshTable() {

        applications.setAll(
                service.getAllApplications()
        );

        applyFilters();
        applySorting();
        updateDashboard();
    }

    private void clearForm() {

        companyField.clear();

        positionField.clear();

        categoryComboBox.setValue(null);

        applicationDatePicker.setValue(
                LocalDate.now()
        );

        closingDatePicker.setValue(null);

        statusComboBox.setValue(null);

        contactPersonField.clear();

        contactEmailField.clear();

        interviewDatePicker.setValue(null);

        notesArea.clear();

        selectedApplication = null;

        applicationTable
                .getSelectionModel()
                .clearSelection();
    }
}