package app.moz.dto;

import app.moz.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientBookedEvent {

    private String toEmail;

    private String link;

    private int clientId;

    private String name;


    private String userFirstName;

    private  String userEmail;

}
