package com.ondodox.kosan.renter;

import com.ondodox.kosan.room.Room;
import com.ondodox.kosan.user.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
public class Renter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "renter_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    private int period;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "room_room_id")
    private Room room;

    private boolean status = true;
}
