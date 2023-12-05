package com.developerblog.ws.ui.controller;

import com.developerblog.ws.exception.UserServiceException;
import com.developerblog.ws.service.UserService;
import com.developerblog.ws.shared.UserMapper;
import com.developerblog.ws.shared.dto.UserDto;
import com.developerblog.ws.ui.model.request.UserDetialsRequestModel;
import com.developerblog.ws.ui.model.response.*;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "users") //http://localhost:8080/users
@Validated
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {

        UserDto userDto = userService.findUserById(id);
        UserRest returnValue = userMapper.userDtoToUserRest(userDto);

        return returnValue;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@Valid @RequestBody UserDetialsRequestModel userDetails) throws Exception {

        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = userMapper.userDetailsRequestModelToUserDto(userDetails);

        UserDto createdUser = userService.createUser(userDto);
        UserRest returnValue = userMapper.userDtoToUserRest(createdUser);

        return returnValue;
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@PathVariable String id,@Valid @RequestBody UserDetialsRequestModel userDetails) {

        UserDto userDto = userMapper.userDetailsRequestModelToUserDto(userDetails);
        UserDto updatedUser = userService.updateUser(id, userDto);

        UserRest returnValue = userMapper.userDtoToUserRest(updatedUser);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RequestOperationModel deleteUser(@PathVariable String id) {
        RequestOperationModel returnValue = new RequestOperationModel();

        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<UserDto> users = userService.getUsers(page, limit);

        List<UserRest> returnValue = users.stream().map(userMapper::userDtoToUserRest).collect(Collectors.toList());

        return returnValue;
    }
}
