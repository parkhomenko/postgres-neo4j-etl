package com.data.etl.service;

import com.data.etl.repository.neo4j.Neo4jMailRepository;
import com.data.etl.repository.postgres.PostgresMailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    private final PostgresMailRepository postgresMailRepository;
    private final Neo4jMailRepository neo4jMailRepository;

    @Value("${com.data.etl.batchSize}")
    private int batchSize;

    @Autowired
    public MailService(PostgresMailRepository postgresMailRepository, Neo4jMailRepository neo4jMailRepository) {
        this.postgresMailRepository = postgresMailRepository;
        this.neo4jMailRepository = neo4jMailRepository;
    }

    public void extractAndUploadMails() throws IOException {
        LOGGER.info("Started at {}", LocalTime.now());

        int totalAmountOfRows = postgresMailRepository.getMailsCount();
        for (int offset = 0; offset < totalAmountOfRows; offset += batchSize) {
            if (offset % 100_000 == 0) {
                LOGGER.info("{} mails has been uploaded...\n", offset);
            }
            List<Map<String, Object>> mails = postgresMailRepository.getMails(offset, batchSize);
            neo4jMailRepository.saveMails(mails);
        }

        LOGGER.info("Finished at {}", LocalTime.now());
    }
}
