package app.moz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private  int clientId;

    private String clientName;

    private String phoneNumber;

    private String email;

    private  long userId;

    private LocalDateTime createdAt;
}
