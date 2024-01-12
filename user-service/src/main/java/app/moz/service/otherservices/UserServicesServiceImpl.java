package app.moz.service.otherservices;

import app.moz.dto.UserServicesDto;
import app.moz.dto.UserServicesRequest;
import app.moz.entity.User;
import app.moz.entity.UserServices;
import app.moz.repository.UserRepository;
import app.moz.repository.UsersServicesRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServicesServiceImpl implements UserServicesService {

    private final ModelMapper modelMapper;

    private final UsersServicesRepository userServicesRepository;

    private final UserRepository userRepository;


    @Override
    public List<UserServicesDto> findAllUserServices() {

        List<UserServices> userServices = userServicesRepository.findAll();


        return userServices.stream().map(
                userServices1 -> modelMapper.map(userServices1, UserServicesDto.class)

        ).toList();

    }

    @Override
    public List<UserServicesDto> findUsersServicesById(long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }

        User user1 = user.get();

        List<UserServices> userServices = userServicesRepository.findByUser(user1);

        return userServices.stream().map(
                userServices1 -> modelMapper.map(userServices1, UserServicesDto.class)
        ).toList();


    }

    @Override
    public UserServicesDto createUserService(UserServicesRequest userServicesRequest, long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty() ) {
            throw new IllegalArgumentException("Not found");
        }

        User user1 = user.get();

        UserServices userServices = new UserServices();

        userServices.setServiceName(userServicesRequest.getServiceName());
        userServices.setTotalSlots(userServicesRequest.getTotalSlots());
        userServices.setAvailableSlots(userServicesRequest.getTotalSlots());
        userServices.setUser(user1);

        UserServices userServices1 = userServicesRepository.save(userServices);

        return modelMapper.map(userServices1, UserServicesDto.class);
    }

    @Override
    public void deleteUserService(int id) {
        Optional<UserServices> userServices = userServicesRepository.findById(id);

        if (userServices.isEmpty()){
            throw new IllegalArgumentException();
        }

        userServicesRepository.delete(userServices.get());

    }
}
