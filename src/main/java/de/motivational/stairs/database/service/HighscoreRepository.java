package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.HighscoreEntity;
import de.motivational.stairs.rest.dto.HighscoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Repository
interface HighscoreRepository extends JpaRepository<HighscoreEntity, Integer> {

    @Query("SELECT new de.motivational.stairs.rest.dto.HighscoreDto(SUM(h.highscore), h.gameByGameId.gameId,h.userByUserId.userId, h.userByUserId.name, h.created) FROM HighscoreEntity h WHERE h.userByUserId.userId = :userId AND h.gameByGameId.gameId = :gameId GROUP BY h.userByUserId.userId")
    Optional<HighscoreDto> findOneByUserIdAndGameId(@Param("userId") int userId, @Param("gameId") int gameId);

    @Query("SELECT new de.motivational.stairs.rest.dto.HighscoreDto(SUM(h.highscore), h.gameByGameId.gameId, u.userId, u.name, h.created) " +
            "FROM HighscoreEntity h, UserEntity u " +
            "WHERE u.userId = h.userByUserId.userId and h.gameByGameId.gameId = :gameId " +
            "GROUP BY h.userByUserId.userId, h.gameByGameId.gameId "+
            "ORDER BY h.highscore")
    Collection<HighscoreDto> findListByGameId(@Param("gameId") int gameId);
}
