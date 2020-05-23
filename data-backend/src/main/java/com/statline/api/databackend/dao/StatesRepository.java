package com.statline.api.databackend.dao;

import com.statline.api.databackend.model.States;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatesRepository extends CrudRepository<States,Integer> {
    @Query(value = "SELECT state,new_cases FROM states_table WHERE date=(SELECT MAX(date) FROM states_table)",nativeQuery = true)
    List<Object[]> getLatest();
}
