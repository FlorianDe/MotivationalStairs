package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.BeamerEntity;
import de.motivational.stairs.rest.dto.setup.BeamerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class BeamerService {
    @Autowired
    private BeamerRepository beamerRepository;

    public Collection<BeamerEntity> findAll() {
        return beamerRepository.findAll();
    }

    public Optional<BeamerEntity> findOne(int beamerId) {
        return Optional.ofNullable(beamerRepository.findOne(beamerId));
    }

    public boolean create(BeamerDto beamerDto) {
        return beamerRepository.save(beamerUpdateCreateHelper(new BeamerEntity(),beamerDto))!=null;
    }

    public boolean delete(int beamerId) {
        Optional<BeamerEntity>  beamer = this.findOne(beamerId);
        if(beamer.isPresent()){
            beamerRepository.delete(beamer.get());
            return true;
        }
        return false;
    }

    public void update(BeamerDto beamerDto) {
        Optional<BeamerEntity>  beamer = this.findOne(beamerDto.getBeamerId());
        if(beamer.isPresent()){
            beamerRepository.save(beamerUpdateCreateHelper(beamer.get(), beamerDto));
        }
    }

    private BeamerEntity beamerUpdateCreateHelper(BeamerEntity beamerEntity, BeamerDto beamerDto){
        beamerEntity.setBeamerName(beamerDto.getBeamerName());
        beamerEntity.setAngle(beamerDto.getAngle());
        beamerEntity.setWidth(beamerDto.getWidth());
        beamerEntity.setHeight(beamerDto.getHeight());
        //TODO @VIKTOR J4I: OFFSET NOT CREATED/UPDATED
        return beamerEntity;
    }
}

