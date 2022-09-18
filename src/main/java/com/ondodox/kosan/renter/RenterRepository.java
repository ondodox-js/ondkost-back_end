package com.ondodox.kosan.renter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RenterRepository extends JpaRepository<Renter, Long> {
    List<Renter> findAllByStatus(boolean status);

    List<Renter> findAllByRoom_TypeRoomIdAndStatus(Long typeId, Boolean status);
}
