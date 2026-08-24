package gy.ug.ite2200.repository;
import gy.ug.ite2200.model.ServiceRequest;
import java.util.List;
import java.util.Optional;
public interface ServiceRequestRepository {
    List<ServiceRequest> findAll();
    Optional<ServiceRequest> findById(int id);
    ServiceRequest save(ServiceRequest request);
    boolean deleteById(int id);
}
