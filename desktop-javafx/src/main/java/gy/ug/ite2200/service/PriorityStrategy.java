package gy.ug.ite2200.service;

import gy.ug.ite2200.model.InternshipApplication;
import gy.ug.ite2200.model.Priority;

@FunctionalInterface
public interface PriorityStrategy {

    Priority calculate(InternshipApplication application);
}