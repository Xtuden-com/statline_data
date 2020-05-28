package com.statline.api.databackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="covid_states")
public class CovidStates {
    @EmbeddedId
    CovidStatesId id;
    private Long new_cases;
}


