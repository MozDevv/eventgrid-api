package app.moz.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private long id;
    private String firstName;
    private String lastname;

    private String company;
    private String email;

    private String password;
    private String token;

}
