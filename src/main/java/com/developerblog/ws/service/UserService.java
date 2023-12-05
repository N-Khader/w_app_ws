package com.developerblog.ws.service;

import com.developerblog.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
    UserDto findUserById(String userId);
    UserDto updateUser(String userId , UserDto userDto);
    void deleteUser(String userId);
    List<UserDto> getUsers(int page, int limit);
}