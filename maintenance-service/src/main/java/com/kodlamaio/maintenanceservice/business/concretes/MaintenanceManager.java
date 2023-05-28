package com.kodlamaio.maintenanceservice.business.concretes;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceIsCompletedEvent;
import com.kodlamaio.commonpackage.kafka.producer.KafkaProducer;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.maintenanceservice.business.abstracts.MaintenanceService;
import com.kodlamaio.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import com.kodlamaio.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import com.kodlamaio.maintenanceservice.business.dto.responses.CreateMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.responses.GetAllMaintenancesResponse;
import com.kodlamaio.maintenanceservice.business.dto.responses.GetMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.responses.UpdateMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.rules.MaintenanceBusinessRules;
import com.kodlamaio.maintenanceservice.entities.Maintenance;
import com.kodlamaio.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceManager implements MaintenanceService {
    private final MaintenanceRepository repository;
    private final ModelMapperService mapper;
    private final MaintenanceBusinessRules rules;
    private final KafkaProducer producer;

    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        var maintenances = repository.findAll();
        var response = maintenances
                .stream()
                .map(maintenance -> mapper.forResponse().map(maintenance, GetAllMaintenancesResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        rules.checkIfMaintenanceExists(id);
        var maintenance = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);

        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(UUID carId) {
        rules.checkIfCarIsNotUnderMaintenance(carId);

        Maintenance maintenance = repository.findByCarIdAndIsCompletedIsFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());

        //carService.changeState(carId, State.AVAILABLE);
        sendKafkaMaintenanceCompletedEvent(carId);

        repository.save(maintenance);

        var response = mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);
        return response;
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.checkIfCarIsUnderMaintenance(request.getCarId());
        rules.ensureCarIsAvailable(request.getCarId());

        var maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(UUID.randomUUID());
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);
        repository.save(maintenance);

        //carService.changeState(request.getCarId(), State.MAINTENANCE);
        sendKafkaMaintenanceCreatedEvent(request.getCarId());

        var response = mapper.forResponse().map(maintenance, CreateMaintenanceResponse.class);
        return response;
    }

    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExists(id);

        var maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(id);
        repository.save(maintenance);

        var response = mapper.forResponse().map(maintenance, UpdateMaintenanceResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfMaintenanceExists(id);
        makeCarAvailableIfIsCompletedFalse(id);
        repository.deleteById(id);
    }

    /*
        private void makeCarAvailableIfIsCompletedFalse(UUID id) {
            var maintenance = repository.findById(id).orElseThrow();
            UUID carId = maintenance.getCarId();
            if (repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
                sendKafkaMaintenanceDeletedEvent(carId);
            }

            private void makeCarAvailableIfIsCompletedFalse(int id) {
            int carId = repository.findById(id).get().getCar().getId();
            if (repository.existsByCarIdAndIsCompletedFalse(carId)) {
                carService.changeState(carId, State.AVAILABLE);
            }
             */
    private void makeCarAvailableIfIsCompletedFalse(UUID id) {
        UUID carId = repository.findById(id).get().getCarId();
        if (repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            sendKafkaMaintenanceDeletedEvent(carId);
        }

    }

    private void sendKafkaMaintenanceCreatedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceCreatedEvent(carId),
                "maintenance-created");
    }

    private void sendKafkaMaintenanceDeletedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceDeletedEvent(carId),
                "maintenance-deleted");
    }

    //maintenanceden sildiğimizde
    private void sendKafkaMaintenanceCompletedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceIsCompletedEvent(carId),
                "maintenance-completed");
    }
//maintenanceden döndürdüğümüzde
//sebebi log tutuyoruz

}
// todo: her şey yaptığın gibi