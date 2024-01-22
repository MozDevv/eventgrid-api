package app.moz.service;

import app.moz.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto findById(long id);

    UserDto updateUser(long userId, UserDto userDto );

}
