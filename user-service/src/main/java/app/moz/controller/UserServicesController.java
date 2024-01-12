package app.moz.controller;

import app.moz.dto.UserServicesDto;
import app.moz.dto.UserServicesRequest;
import app.moz.service.otherservices.UserServicesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserServicesController {

    private final UserServicesService userServicesService;


    @GetMapping("/services")
    @ResponseStatus(HttpStatus.OK)
    public List<UserServicesDto> findAllServices () {
        return userServicesService.findAllUserServices();
    }
    @PostMapping("/services/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserServicesDto createUserService(@RequestBody UserServicesRequest userServicesRequest,
                                             @PathVariable long userId
                                             ) {
        return userServicesService.createUserService(userServicesRequest, userId);
    }
    @GetMapping("/services/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserServicesDto> findUsersServices (@PathVariable long userId) {
        return userServicesService.findUsersServicesById(userId);
    }

    @DeleteMapping("/services/{serviceId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByUserId(@PathVariable int serviceId) {
        userServicesService.deleteUserService(serviceId);
    }

}
