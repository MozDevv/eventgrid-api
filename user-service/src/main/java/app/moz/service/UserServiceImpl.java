package app.moz.service;

import app.moz.dto.UserDto;
import app.moz.entity.User;
import app.moz.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDto createUser(UserDto userDto) {

        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setCompany(userDto.getCompany());


        try {
            User user1 = userRepository.save(user);
            return modelMapper.map(user1, UserDto.class);

        } catch (Exception exception) {
            throw new IllegalArgumentException(exception);
        }

    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto findById(long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User Not Found");
        }

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(long userId, UserDto userDto) {

        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User was not Found");
        }

        User user = existingUser.get();

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getCompany() != null) {
            user.setCompany(userDto.getCompany());
        }



        User user1 = userRepository.save(user);

        return modelMapper.map(user1, UserDto.class);
    }


}
