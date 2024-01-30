package app.moz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@Table(name = "event")
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(generator = "event_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "event_id_seq", initialValue = 1, sequenceName = "event_id_seq", allocationSize = 1)
    private int id;

    private String title;

    private String description;

    @Column(name = "event_start")
    private LocalDateTime start;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "event_end")
    private LocalDateTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

}
