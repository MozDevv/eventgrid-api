package app.moz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserServices {

    @Id
    @GeneratedValue(generator = "services_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "services_id_seq", initialValue = 1, sequenceName = "services_id_seq", allocationSize = 1)
    private  int serviceId;

    private  String serviceName;

    private int availableSlots;

    private int totalSlots;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

}
