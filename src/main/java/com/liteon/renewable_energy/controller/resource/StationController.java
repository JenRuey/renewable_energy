package com.liteon.renewable_energy.controller.resource;

import com.liteon.renewable_energy.DTO.StationDataResponse;
import com.liteon.renewable_energy.model.Notification;
import com.liteon.renewable_energy.service.StationServices;
import com.liteon.renewable_energy.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth-station")
public class StationController {

    @Autowired
    private StationServices stationServices;

    @GetMapping("/data")
    public ResponseEntity<StationDataResponse> getAllStationData() {
        StationDataResponse stationData = stationServices.getAllStationData();
        return new ResponseEntity<>(stationData, HttpStatus.OK);
    }

}
