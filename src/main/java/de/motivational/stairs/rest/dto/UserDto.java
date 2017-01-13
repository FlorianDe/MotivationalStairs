package de.motivational.stairs.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.motivational.stairs.database.entity.OffsetEntity;
import de.motivational.stairs.database.entity.StairsEntity;
import de.motivational.stairs.database.entity.UserEntity;

/**
 * Created by fdecker on 28.12.16.
 */
public class UserDto {
    @JsonProperty("id")
    @JsonView(View.class)
    private int userId;

    @JsonView(View.Extended.class)
    @JsonProperty("cookie")
    private String cookie;

    @JsonView(View.class)
    @JsonProperty("name")
    private String name;

    public UserDto(){}

    public UserDto(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.cookie = userEntity.getCookie();
        this.name = userEntity.getName();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
