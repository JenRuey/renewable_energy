package com.liteon.renewable_energy.service;

import com.liteon.renewable_energy.DTO.StationDataResponse;
import com.liteon.renewable_energy.model.Event;
import com.liteon.renewable_energy.model.History;
import com.liteon.renewable_energy.model.Location;
import com.liteon.renewable_energy.repository.EventRepo;
import com.liteon.renewable_energy.repository.HistoryRepo;
import com.liteon.renewable_energy.repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class StationServices {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private HistoryRepo historyRepo;
    @Autowired
    private LocationRepo locationRepo;

    public StationDataResponse getAllStationData() {
        try {
            Iterable<Event> events = eventRepo.findAll();
            Iterable<History> historyRecords = historyRepo.findAll();
            Iterable<Location> locations = locationRepo.findAll();

            return new StationDataResponse(events, historyRecords, locations);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
