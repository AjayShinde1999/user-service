package com.user.service.controller;

import com.user.service.payload.UserDto;
import com.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("Request from postman {}", userDto);
        return userService.saveOneUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public UserDto getOneUser(@RequestParam(value = "id") long id) {
        log.info("Request from postman with I'D {}", id);
        return userService.getOneUserById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/all")
    public List<UserDto> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        return userService.getAllUsers(pageNumber,pageSize);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUserById(id);
        return "User deleted successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public UserDto updateUserById(@RequestBody UserDto userDto, @RequestParam("id") long id) {
        return userService.updateUser(userDto, id);
    }

}
