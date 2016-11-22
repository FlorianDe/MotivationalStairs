package de.motivational.stairs.database.repository;

import de.motivational.stairs.database.entity.OffsetEntity;
import de.motivational.stairs.rest.dto.setup.OffsetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class OffsetService {
    @Autowired
    private OffsetRepository offsetRepository;

    public Collection<OffsetEntity> findAll() {
        return offsetRepository.findAll();
    }

    public Optional<OffsetEntity> findOne(int offsetId) {
        return Optional.ofNullable(offsetRepository.findOne(offsetId));
    }

    public boolean create(OffsetDto offsetDto) {
        return offsetRepository.save(helperUpdateCreateOffset(new OffsetEntity(),offsetDto))!=null;
    }

    public boolean delete(int offsetId) {
        Optional<OffsetEntity> offsetEntity = this.findOne(offsetId);
        if(offsetEntity.isPresent()){
            offsetRepository.delete(offsetEntity.get());
            return true;
        }
        return false;
    }

    public void update(OffsetDto offsetDto) {
        Optional<OffsetEntity> offsetEntity = this.findOne(offsetDto.getOffsetId());
        if(offsetEntity.isPresent()){
            offsetRepository.save(helperUpdateCreateOffset(offsetEntity.get(),offsetDto));
        }
    }

    private OffsetEntity helperUpdateCreateOffset(OffsetEntity offsetEntity, OffsetDto offsetDto){
        offsetEntity.setX(offsetDto.getX());
        offsetEntity.setY(offsetDto.getY());
        offsetEntity.setZ(offsetDto.getZ());
        offsetEntity.setYaw(offsetDto.getYaw());
        offsetEntity.setPitch(offsetDto.getPitch());
        offsetEntity.setRoll(offsetDto.getRoll());
        return  offsetEntity;
    }
}

