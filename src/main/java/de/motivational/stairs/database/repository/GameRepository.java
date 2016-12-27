package de.motivational.stairs.database.repository;

import de.motivational.stairs.database.entity.BeamerEntity;
import de.motivational.stairs.database.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Florian on 29.08.2016.
 */
@Repository
interface GameRepository extends JpaRepository<GameEntity, Integer> {

}
