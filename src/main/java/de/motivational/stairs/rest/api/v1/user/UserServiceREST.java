package de.motivational.stairs.rest.api.v1.user;

/**
 * Created by Florian on 11.07.2016.
 */

import com.fasterxml.jackson.annotation.JsonView;
import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.database.service.UserService;
import de.motivational.stairs.rest.dto.UserDto;
import de.motivational.stairs.rest.dto.View;
import de.motivational.stairs.socket.WebSocketHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Api(description = "Rest Service for user management.", produces = "application/json")
@RestController
@RequestMapping(value="/api/v1.0/user")
public class UserServiceREST {

    @Autowired
    UserService userService;

    @Autowired
    WebSocketHandler webSocketHandler;


    @ApiOperation(value = "Returns users details", notes = "Returns a complete list of all users.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of users details")}
    )
    @JsonView(View.class)
    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody UserDto[] getAll() {
        UserDto[] users = userService
                .findAll().stream()
                .map(UserDto::new)
                .toArray(UserDto[]::new);
        return users;
    }

    @ApiOperation(value = "Returns users details", notes = "Returns a complete list of all users who are active connected via Websocket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of users details")}
    )
    @JsonView(View.class)
    @RequestMapping(value="active/", method= RequestMethod.GET)
    @ResponseBody UserDto[] getAllActiveUsers() {
        UserDto[] users = this.webSocketHandler
                .getAllActiveUsers().stream()
                .map(usr -> new UserDto(userService.findOne(usr.getUserId()).get()))
                .toArray(UserDto[]::new);
        return users;
    }

    @ApiOperation(value = "Returns user details", notes = "Returns a user based on a cookie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of user detail"),
            @ApiResponse(code = 404, message = "User with given username does not exist")}
    )
    @RequestMapping(value="/me", method= RequestMethod.GET)
    @JsonView(View.Extended.class)
    @ResponseBody UserDto getOneByCookie(@CookieValue(value = "ms_user_c", defaultValue = "undefined") String cookie,
                                         @RequestParam(required = false, defaultValue = "undefined", name = "uid") String paramCookie) {
        String c = cookie.equals("undefined")?paramCookie:cookie;
        Optional<UserDto> userDto = userService.findOneByCookie(c).map(UserDto::new);
        return userDto.isPresent()?userDto.get():null;
    }

    @ApiOperation(value = "Creates a new user", notes = "Creates a new user by the given name and cookie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a new user"),
            @ApiResponse(code = 404, message = "Username already exists")}
    )
    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody UserDto create(@RequestBody UserDto userDto, HttpServletResponse response) {
        Optional<UserEntity> user = userService.create(userDto);
        if(user.isPresent()) {
            response.addCookie(new Cookie("ms_user_c", user.get().getCookie()));
            return new UserDto(user.get());
        }
        return null;
    }


    @ApiOperation(value = "Deletes a user", notes = "Deletes a user by the given user id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted user"),
            @ApiResponse(code = 404, message = "User with given user id does not exist")}
    )
    @RequestMapping(value="/{userId}", method= RequestMethod.DELETE)
    @ResponseBody void delete(@PathVariable int userId) {
        userService.delete(userId);
    }


    @CrossOrigin()
    @ApiOperation(value = "Set new cookie for user", notes = "Sets a new cookie for a user with the given cookie and user id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user cookie details"),
            @ApiResponse(code = 404, message = "User with given user id does not exist")}
    )
    @RequestMapping(value="/", method= RequestMethod.PUT)
    @ResponseBody boolean update(@RequestBody UserDto userDto) {
        boolean res = userService.setCookie(userDto);

        if(res) {
            webSocketHandler.updateUsers();
            webSocketHandler.notifyUsers(WebSocketHandler.EVENT.NEW_PLAYER_LIST, "");
        }

        return res;
    }
}