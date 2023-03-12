package com.liteon.renewable_energy.repository;

import com.liteon.renewable_energy.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventRepo extends CrudRepository<Event, String> {

    @Query(value = "select * from \"event\" where tagname = ?1", nativeQuery = true)
    Event findByTagname(String eventTagname);
}
