package app.moz.auth;

import app.moz.config.JwtService;
import app.moz.entity.Role;
import app.moz.entity.User;
import app.moz.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .company(request.getCompany())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var user1 = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .company(user1.getCompany())
                .firstName(user1.getFirstName())
                .lastname(user1.getLastName())
                .company(user1.getCompany())
                .email(user1.getEmail())
                .password(user1.getPassword())
                .id(user1.getId())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse changePassword(ChangePasswordRequest request, long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("Password is Incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        User updatedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(updatedUser);

        return AuthenticationResponse.builder()
                .firstName(updatedUser.getCompany())
                .lastname(updatedUser.getLastName())
                .email(updatedUser.getEmail())
                .password(updatedUser.getPassword())
                .company(updatedUser.getCompany())
                .id(updatedUser.getId())
                .token(jwtToken)
                .build();


    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )

        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .token(jwtToken)


                .build();
    }


}



