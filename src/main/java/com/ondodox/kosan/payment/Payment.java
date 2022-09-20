package com.ondodox.kosan.payment;

import com.ondodox.kosan.renter.Renter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    private Long total;

    private String description;

    @ManyToOne(targetEntity = Renter.class)
    @JoinColumn(name = "renter_id")
    private Renter renter;
}
