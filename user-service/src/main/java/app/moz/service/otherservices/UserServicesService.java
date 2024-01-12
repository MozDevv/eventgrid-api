package app.moz.service.otherservices;

import app.moz.dto.UserServicesDto;
import app.moz.dto.UserServicesRequest;

import java.util.List;

public interface UserServicesService {

    List<UserServicesDto> findAllUserServices();

    List <UserServicesDto> findUsersServicesById(long id);



    UserServicesDto createUserService(UserServicesRequest userServicesRequest, long userId);

    void deleteUserService(int id);

}
