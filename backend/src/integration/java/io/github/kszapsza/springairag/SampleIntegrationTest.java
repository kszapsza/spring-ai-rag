package io.github.kszapsza.springairag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

class SampleIntegrationTest extends BaseIntegrationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testDatabaseConnection() {
        // when
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);

        // then
        assert result == 1;
    }
}
