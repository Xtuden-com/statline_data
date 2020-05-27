package com.statline.api.databackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CovidStatesId implements Serializable {
    private Date date;
    private String state;
}
