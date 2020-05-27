package com.statline.api.databackend.dao;

import com.statline.api.databackend.model.CovidStatesId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;

@SpringBootTest
@ActiveProfiles("test")
class CovidStatesRepositoryTest {
    @Autowired
    CovidStatesRepository covidStatesRepository;
    @Test
    @Sql({"/schema.sql", "/data.sql"})
    void testTotalNewCases(){
        assert(20==covidStatesRepository.totalNewCases());
    }
    @Test
    @Sql({"/schema.sql", "/data.sql"})
    void testLatest(){
        assert (covidStatesRepository.getLatest().size()==2);
    }
    @Test
    @Sql({"/schema.sql", "/data.sql"})
    void testGetById(){
        System.out.println(covidStatesRepository.findById(new CovidStatesId(Date.valueOf("2020-05-26"),
                "New York")).toString());
    }
}