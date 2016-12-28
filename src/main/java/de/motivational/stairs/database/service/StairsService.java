package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.StairsEntity;
import de.motivational.stairs.rest.dto.setup.StairsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class StairsService {
    @Autowired
    private StairsRepository stairsRepository;

    public Collection<StairsEntity> findAll() {
        return stairsRepository.findAll();
    }

    public Optional<StairsEntity> findOne(int stairsId) {
        return Optional.ofNullable(stairsRepository.findOne(stairsId));
    }

    public boolean create(StairsDto stairsDto) {
        return stairsRepository.save(helperUpdateCreateStairs(new StairsEntity(), stairsDto))!=null;
    }

    public boolean delete(int stairsId) {
        Optional<StairsEntity> stairsEntity = this.findOne(stairsId);
        if(stairsEntity.isPresent()){
            stairsRepository.delete(stairsEntity.get());
            return true;
        }
        return false;
    }

    public void update(StairsDto stairsDto) {
        Optional<StairsEntity> stairsEntity = this.findOne(stairsDto.getStairsId());
        if(stairsEntity.isPresent()){
            stairsRepository.save(helperUpdateCreateStairs(stairsEntity.get(),stairsDto));
        }
    }

    private StairsEntity helperUpdateCreateStairs(StairsEntity stairsEntity, StairsDto stairsDto){
        stairsEntity.setStairsName(stairsDto.getStairsName());
        stairsEntity.setStepsCount(stairsDto.getStepsCount());
        stairsEntity.setStepWidth(stairsDto.getStepWidth());
        stairsEntity.setStepDepth(stairsDto.getStepDepth());
        stairsEntity.setStepHeight(stairsDto.getStepHeight());
        //TODO @VIKTOR IS THIS OFFSET?
        if(stairsDto.getMargin()!=null) {
            stairsEntity.setMarginLeft(stairsDto.getMargin().getLeft());
            stairsEntity.setMarginRight(stairsDto.getMargin().getRight());
            stairsEntity.setMarginTop(stairsDto.getMargin().getTop());
            stairsEntity.setMarginBottom(stairsDto.getMargin().getBottom());
        }
        return  stairsEntity;
    }
}

