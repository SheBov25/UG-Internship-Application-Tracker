package gy.ug.ite2200.repository;

import gy.ug.ite2200.model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryServiceRequestRepository implements ServiceRequestRepository {
    private final Map<Integer, ServiceRequest> data = new LinkedHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(3);
    public InMemoryServiceRequestRepository() {
        save(new ServiceRequest(1, "Repair lab projector", "IT Support", "Projector has no image.", RequestStatus.OPEN, LocalDate.now()));
        save(new ServiceRequest(2, "Replace hallway light", "Facilities", "Light near Room 12 is faulty.", RequestStatus.IN_PROGRESS, LocalDate.now()));
    }
    public List<ServiceRequest> findAll() { return new ArrayList<>(data.values()); }
    public Optional<ServiceRequest> findById(int id) { return Optional.ofNullable(data.get(id)); }
    public ServiceRequest save(ServiceRequest r) {
        ServiceRequest value=r;
        if (r.getId() <= 0) value=new ServiceRequest(nextId.getAndIncrement(), r.getTitle(), r.getCategory(), r.getDescription(), r.getStatus(), r.getCreatedDate());
        data.put(value.getId(), value); return value;
    }
    public boolean deleteById(int id) { return data.remove(id) != null; }
}
