package de.motivational.stairs.rest.api.v1.user;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.database.service.UserService;
import de.motivational.stairs.rest.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Api(description = "Rest Service for user management.", produces = "application/json")
@RestController
@RequestMapping(value="/api/v1.0/user")
public class UserServiceREST {

    @Autowired
    UserService userService;


    @ApiOperation(value = "Returns users details", notes = "Returns a complete list of all users.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of users details")}
    )
    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody UserDto[] getAll() {
        UserDto[] users = userService
                .findAll().stream()
                .map(UserDto::new)
                .toArray(UserDto[]::new);
        return users;
    }


    @ApiOperation(value = "Returns user details", notes = "Returns a complete list of users details with a date of last modification.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of user detail"),
            @ApiResponse(code = 404, message = "User with given username does not exist")}
    )
    @RequestMapping(value="/{userId}", method= RequestMethod.GET)
    @ResponseBody UserDto getOneById(@PathVariable int userId) {
        Optional<UserDto> userDto = userService.findOne(userId).map(UserDto::new);
        return userDto.isPresent()?userDto.get():null;
    }


    @ApiOperation(value = "Creates a new user", notes = "Creates a new user by the given name and cookie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a new user"),
            @ApiResponse(code = 404, message = "Username already exists")}
    )
    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody boolean create(@RequestBody UserDto userDto) {
        return userService.create(userDto).isPresent();
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


    @ApiOperation(value = "Set new cookie for user", notes = "Sets a new cookie for a user with the given cookie and user id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user cookie details"),
            @ApiResponse(code = 404, message = "User with given user id does not exist")}
    )
    @RequestMapping(value="/", method= RequestMethod.PUT)
    @ResponseBody void update(@RequestBody UserDto userDto) {
        userService.setCookie(userDto);
    }
}