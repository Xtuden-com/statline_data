package com.statline.api.databackend.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import lombok.Data;

@Data
@Entity
@Table(name="states_table")
@IdClass(CovidStates.IdClass.class)
public class CovidStates {
    @Id
    private Date date;
    @Id
    private String state;
    private Long new_cases;

    @Data
    static class IdClass implements Serializable{
        private Data date;
        private String state;
    }
}


