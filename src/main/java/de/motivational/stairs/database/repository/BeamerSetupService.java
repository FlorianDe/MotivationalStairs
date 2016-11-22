package de.motivational.stairs.database.repository;

import de.motivational.stairs.database.entity.BeamerEntity;
import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.database.entity.StairsEntity;
import de.motivational.stairs.rest.dto.setup.BeamerSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class BeamerSetupService {
    @Autowired
    private BeamerSetupRepository beamerSetupRepository;

    public Collection<BeamerSetupEntity> findAll() {
        return beamerSetupRepository.findAll();
    }

    public Optional<BeamerSetupEntity> findOne(int beamerSetupId) {
        return Optional.ofNullable(beamerSetupRepository.findOne(beamerSetupId));
    }

    public boolean delete(int beamerSetupId) {
        Optional<BeamerSetupEntity> beamerSetupEntity = this.findOne(beamerSetupId);
        if(beamerSetupEntity.isPresent()){
            beamerSetupRepository.delete(beamerSetupEntity.get());
            return true;
        }
        return false;
    }

    public void update(BeamerSetupDto beamerSetupDto) {
        Optional<BeamerSetupEntity> beamerSetupEntity = this.findOne(beamerSetupDto.getSetupId());
        if(beamerSetupEntity.isPresent()){
            beamerSetupEntity.get().setSetupName(beamerSetupDto.getSetupName());

            if(beamerSetupDto.getStairs()!=null) {
                StairsEntity stairs = beamerSetupEntity.get().getStairsByStairsId();

                stairs.setStairsName(beamerSetupDto.getStairs().getStairsName());
                stairs.setStepsCount(beamerSetupDto.getStairs().getStepsCount());
                stairs.setStepWidth(beamerSetupDto.getStairs().getStepWidth());
                stairs.setStepDepth(beamerSetupDto.getStairs().getStepDepth());
                stairs.setStepHeight(beamerSetupDto.getStairs().getStepHeight());
                //TODO @VIKTOR IS THIS OFFSET?
                if(beamerSetupDto.getStairs().getMargin()!=null) {
                    stairs.setMarginLeft(beamerSetupDto.getStairs().getMargin().getLeft());
                    stairs.setMarginRight(beamerSetupDto.getStairs().getMargin().getRight());
                    stairs.setMarginTop(beamerSetupDto.getStairs().getMargin().getTop());
                    stairs.setMarginBottom(beamerSetupDto.getStairs().getMargin().getBottom());
                }
            }
            if(beamerSetupDto.getBeamer()!=null) {
                BeamerEntity beamer = beamerSetupEntity.get().getBeamerByBeamerId();

                beamer.setBeamerName(beamerSetupDto.getBeamer().getBeamerName());
                beamer.setAngle(beamerSetupDto.getBeamer().getAngle());
                beamer.setWidth(beamerSetupDto.getBeamer().getWidth());
                beamer.setHeight(beamerSetupDto.getBeamer().getHeight());
            }

            beamerSetupRepository.save(beamerSetupEntity.get());
        }
    }
}

