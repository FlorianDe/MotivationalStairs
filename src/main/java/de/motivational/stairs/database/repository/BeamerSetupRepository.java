package de.motivational.stairs.database.repository;

import de.motivational.stairs.database.entity.BeamerSetupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Repository
public interface BeamerSetupRepository extends JpaRepository<BeamerSetupEntity, Integer> {
    //Optional<BeamerSetupEntity> findOneByConnectionId(long groupId);

    //@Query("SELECT c FROM ConnectionEntity c WHERE c.toUserId = :userId OR c.fromUserId = :userId")
    //Collection<BeamerSetupEntity> findCollectionByUserId(@Param("userId") int userId);
}
