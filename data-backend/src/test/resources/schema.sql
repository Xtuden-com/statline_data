DROP TABLE IF EXISTS states_table;

CREATE TABLE states_table (
    date DATE,
    state VARCHAR(255),
    new_cases BIGINT(20)
);