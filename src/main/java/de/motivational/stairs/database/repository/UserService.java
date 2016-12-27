package de.motivational.stairs.database.repository;

import de.motivational.stairs.database.entity.HighscoreEntity;
import de.motivational.stairs.database.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Collection<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findOne(int userId) {
        return Optional.ofNullable(userRepository.findOne(userId));
    }
}

