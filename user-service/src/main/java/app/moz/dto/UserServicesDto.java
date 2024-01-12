package app.moz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserServicesDto {

    private  int serviceId;

    private  String serviceName;

    private int availableSlots;

    private int totalSlots;

    private long userId;
}
