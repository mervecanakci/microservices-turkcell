package com.kodlamaio.maintenanceservice.business.rules;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.maintenanceservice.api.clients.CarClient;
import com.kodlamaio.maintenanceservice.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceBusinessRules {
    private final MaintenanceRepository repository;
    @Qualifier("com.kodlamaio.maintenanceservice.api.clients.CarClient")
    private final CarClient carClient;

    public void ensureCarIsAvailable(UUID carId) {
        var response = carClient.checkIfCarAvailable(carId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
        //arabanın müsaitliği kontrol ettik/ emin olduk
    }

    public void checkIfMaintenanceExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("MAINTENANCE_NOT_EXISTS");
        }
        //bakımda araba var mı yok mu onu kontrol ediyor
    }

    public void checkIfCarIsNotUnderMaintenance(UUID carId) {
        if (!repository.existsByCarIdAndIsCompletedIsFalse(carId))
            throw new BusinessException("MAINTENANCE_CAR_NOT_EXISTS");
    }

    //bakımda mı onu kontrol ediyoruz
    //arabanın bakımdan gelebilmesi için arabanın bakımda olması lazım!!!!
    //Araba Bakımda Değilse Kontrol Edin

    public void checkIfCarIsUnderMaintenance(UUID carId) {
        if (repository.existsByCarIdAndIsCompletedIsFalse(carId))
            throw new BusinessException("MAINTENANCE_CAR_EXISTS");

        //Arabanın Bakımda Olduğunu Kontrol Edin
        //bakımda olan arabayı tekrar bakıjma gönderemezsin
    }
}



