package com.kodlamaio.commonpackage.events.maintenance;

import com.kodlamaio.commonpackage.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceIsCompletedEvent implements Event {
    private UUID carId;

}
