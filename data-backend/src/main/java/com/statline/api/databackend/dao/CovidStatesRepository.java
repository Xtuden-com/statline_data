package com.statline.api.databackend.dao;

import com.statline.api.databackend.model.CovidStates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CovidStatesRepository extends CrudRepository<CovidStates,Integer> {
    @Query(value = "SELECT state,new_cases FROM states_table WHERE date=(SELECT MAX(date) FROM states_table)",nativeQuery = true)
    List<Object[]> getLatest();
}
