package com.data.etl.repository.postgres;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Repository
public class PostgresMailRepository {

    private final static String MAILS_SQL = "/sql/selectMails.sql";
    private final static String MAILS_COUNT_SQL = "/sql/countMails.sql";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresMailRepository(@Qualifier("jdbcPostgres") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMailsCount() throws IOException {
        String mailsCountSql = IOUtils.resourceToString(MAILS_COUNT_SQL, StandardCharsets.UTF_8);
        return jdbcTemplate.queryForObject(mailsCountSql, Integer.class);
    }

    public List<Map<String, Object>> getMails(int offset, int limit) throws IOException {
        String mailsSql = IOUtils.resourceToString(MAILS_SQL, StandardCharsets.UTF_8);
        return jdbcTemplate.queryForList(mailsSql, limit, offset);
    }
}
