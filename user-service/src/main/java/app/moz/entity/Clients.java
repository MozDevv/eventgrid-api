package app.moz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Clients {

    @Id
    @GeneratedValue(generator = "client_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "client_id_seq", initialValue = 1, sequenceName = "client_id_seq", allocationSize = 1)
    private  int clientId;

    private String clientName;

    private String phoneNumber;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;


}
