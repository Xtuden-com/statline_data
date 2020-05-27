package com.statline.api.databackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="states_table")
public class CovidStates {
    @EmbeddedId
    CovidStatesId id;
    private Long new_cases;
}


