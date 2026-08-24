package gy.ug.ite2200.repository;

import gy.ug.ite2200.model.InternshipApplication;

import java.util.List;
import java.util.Optional;

public interface InternshipApplicationRepository {

    List<InternshipApplication> findAll();

    Optional<InternshipApplication> findById(int id);

    InternshipApplication save(InternshipApplication application);

    boolean deleteById(int id);
}