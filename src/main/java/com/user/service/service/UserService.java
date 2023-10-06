package com.user.service.service;

import com.user.service.payload.UserDto;

import java.util.List;

public interface UserService {

    UserDto saveOneUser(UserDto userDto);

    UserDto getOneUserById(long id);

    List<UserDto> getAllUsers(Integer pageNumber,Integer pageSize);

    void deleteUserById(long id);

    UserDto updateUser(UserDto userDto,long id);


}
