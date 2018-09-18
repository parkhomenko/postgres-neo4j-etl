package com.data.etl.repository.neo4j;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class Neo4jMailRepository {

    private final static String INSERT_MAILS_CQL = "/cypher/insertMails.cql";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Neo4jMailRepository(@Qualifier("jdbcNeo4j") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveMails(List<Map<String, Object>> records) throws IOException {
        String insertMailsCql = IOUtils.resourceToString(INSERT_MAILS_CQL, StandardCharsets.UTF_8);
        records.forEach(record -> jdbcTemplate.update(insertMailsCql, preparedStatement -> {
            preparedStatement.setLong(1, (Long) record.get("sender_id"));
            preparedStatement.setString(2, record.get("sender_name").toString());
            preparedStatement.setString(3, record.get("sender_age").toString());
            preparedStatement.setString(4, record.get("sender_address").toString());
            preparedStatement.setLong(5, (Long) record.get("recipient_id"));
            preparedStatement.setString(6, record.get("recipient_name").toString());
            preparedStatement.setString(7, record.get("recipient_age").toString());
            preparedStatement.setString(8, record.get("recipient_address").toString());
            preparedStatement.setLong(9, (Long) record.get("mail_id"));
            preparedStatement.setString(10, record.get("mail_topic").toString());
            preparedStatement.setString(11, record.get("mail_body").toString());
            preparedStatement.setTimestamp(12, (Timestamp) record.get("sent_date"));
            preparedStatement.setTimestamp(13, (Timestamp) record.get("delivery_date"));
            preparedStatement.setString(14, record.get("status").toString());
        }));
    }
}
