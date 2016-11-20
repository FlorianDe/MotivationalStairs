package de.motivational.stairs.database.repository;

import de.motivational.stairs.database.entity.BeamerSetupEntity;
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

    public Optional<BeamerSetupEntity> findOne(int beamerId) {
        return Optional.ofNullable(beamerSetupRepository.findOne(beamerId));
    }
}

