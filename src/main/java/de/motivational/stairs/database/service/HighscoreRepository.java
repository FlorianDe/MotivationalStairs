package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.HighscoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Florian on 29.08.2016.
 */
@Repository
interface HighscoreRepository extends JpaRepository<HighscoreEntity, Integer> {

    @Query("SELECT h FROM HighscoreEntity h WHERE h.userByUserId.userId = :userId AND h.gameByGameId.gameId = :gameId")
    HighscoreEntity findOneByUserIdAndGameId(@Param("userId") int userId,@Param("gameId") int gameId);
}
