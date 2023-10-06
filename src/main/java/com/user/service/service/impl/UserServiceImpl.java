package com.user.service.service.impl;

import com.user.service.exception.ResourceNotFoundException;
import com.user.service.model.User;
import com.user.service.payload.UserDto;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto saveOneUser(UserDto userDto) {
        User users = mapToEntity(userDto);
        User savedUser = userRepository.save(users);
        log.info("User Saved {}", savedUser);
        return mapToDto(savedUser);
    }

    @Override
    public UserDto getOneUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found With I'D : " + id)
        );
        log.info("User details {}", user);
        return mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(Integer pageNumber, Integer pageSize) {

        // PageRequest pageRequest = PageRequest.ofSize(pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> all = userRepository.findAll(pageRequest);
        List<User> users = all.getContent();
        return users.stream().map(user -> mapToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with I'D : " + id)
        );
        userRepository.delete(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, long id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with I'D : " + id)
        );
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    User mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    UserDto mapToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
