package com.liteon.renewable_energy.DTO;

import com.liteon.renewable_energy.model.Event;
import com.liteon.renewable_energy.model.History;
import com.liteon.renewable_energy.model.Location;

public class StationDataResponse {
    private Iterable<Event> events;
    private Iterable<History> historyRecords;
    private Iterable<Location> locations;

    public StationDataResponse(Iterable<Event> events, Iterable<History> historyRecords, Iterable<Location> locations) {
        this.events = events;
        this.historyRecords = historyRecords;
        this.locations = locations;
    }

    public Iterable<Event> getEvents() {
        return events;
    }

    public void setEvents(Iterable<Event> events) {
        this.events = events;
    }

    public Iterable<History> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(Iterable<History> historyRecords) {
        this.historyRecords = historyRecords;
    }

    public Iterable<Location> getLocations() {
        return locations;
    }

    public void setLocations(Iterable<Location> locations) {
        this.locations = locations;
    }
}
