package com.liteon.renewable_energy.repository;

import com.liteon.renewable_energy.model.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepo extends CrudRepository<Notification, Integer> {

    @Query(value = "select * from \"notification\" where user_id = ?1 or user_id is null order by datetime desc", nativeQuery = true)
    Iterable<Notification> findAllByUserID(Integer id);
}
