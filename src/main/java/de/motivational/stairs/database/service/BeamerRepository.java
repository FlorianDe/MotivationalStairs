package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.BeamerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Florian on 29.08.2016.
 */
@Repository
interface BeamerRepository extends JpaRepository<BeamerEntity, Integer> {

}
