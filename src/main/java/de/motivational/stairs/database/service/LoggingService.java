package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.LoggingEntity;
import de.motivational.stairs.database.entity.OffsetEntity;
import de.motivational.stairs.rest.dto.LoggingDto;
import de.motivational.stairs.rest.dto.setup.OffsetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class LoggingService {
    @Autowired
    private LoggingRepository loggingRepository;

    public Collection<LoggingEntity> findAll() {
        return loggingRepository.findAll();
    }

    public Optional<LoggingEntity> findOne(int offsetId) {
        return Optional.ofNullable(loggingRepository.findOne(offsetId));
    }

    public boolean create(LoggingDto loggingDto) {
        LoggingEntity loggingEntity = new LoggingEntity();
        loggingEntity.setSession(loggingDto.getSession());
        loggingEntity.setAction(loggingDto.getAction());
        loggingEntity.setComment(loggingDto.getComment());
        loggingEntity.setTimestamp(loggingDto.getTimestamp());

        return loggingRepository.save(loggingEntity)!=null;
    }
}

