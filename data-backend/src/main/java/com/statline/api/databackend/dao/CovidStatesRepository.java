package com.statline.api.databackend.dao;

import com.statline.api.databackend.model.CovidStates;
import com.statline.api.databackend.model.CovidStatesId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface CovidStatesRepository extends CrudRepository<CovidStates,Integer> {
    @Query(value = "SELECT state,new_cases FROM states_table WHERE date=(SELECT MAX(date) FROM states_table)",nativeQuery = true)
    List<Object[]> getLatest();
    @Query(value = "SELECT SUM(new_cases) FROM states_table WHERE date=(SELECT MAX(date) FROM states_table)", nativeQuery = true)
    long totalNewCases();
    CovidStates findById(CovidStatesId id);
}