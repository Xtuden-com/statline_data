DROP TABLE IF EXISTS covid_states;

CREATE TABLE covid_states (
    date DATE,
    state VARCHAR(255),
    new_cases BIGINT(20)
);