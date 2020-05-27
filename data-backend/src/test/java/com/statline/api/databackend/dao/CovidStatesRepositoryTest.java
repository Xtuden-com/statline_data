package com.statline.api.databackend.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

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
}