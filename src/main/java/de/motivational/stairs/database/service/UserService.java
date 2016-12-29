package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.rest.dto.UserDto;
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

    public  Optional<UserEntity> create(UserDto userDto){
        Optional<UserEntity> resUser = Optional.empty();
        if(userDto.getName() != null && !userDto.getName().isEmpty() && userDto.getCookie() != null && !userDto.getCookie().isEmpty()) {
            UserEntity user = new UserEntity();
            user.setName(userDto.getName());
            user.setCookie(userDto.getCookie());
            resUser = Optional.ofNullable(userRepository.save(user));
        }
        return resUser;
    }

    public boolean setCookie(UserDto userDto){
        UserEntity user = userRepository.findOne(userDto.getUserId());
        if(user != null) {
            user.setCookie(userDto.getCookie());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void delete(int userId){
        userRepository.delete(userId);
    }
}
