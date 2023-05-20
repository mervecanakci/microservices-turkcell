package com.kodlamaio.maintenanceservice.repository;

import com.kodlamaio.maintenanceservice.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaintenanceRepository extends JpaRepository<Maintenance, UUID> {
    boolean existsByCarIdAndIsCompletedIsFalse(UUID carId);
    //? false olarak aldık çünkü add kısmında araç bakımda mı buna bakması lazım öncelikle

    Maintenance findByCarIdAndIsCompletedIsFalse(UUID carId);
    // gidecek tablolara bakıcak; carId ve isCompleted false ise
    // onu getirecek - yoksa zaten sorun yok -


    // veri tabanı tablosundan bulup getiricek verileri o nedenle repository de yazdık

}
