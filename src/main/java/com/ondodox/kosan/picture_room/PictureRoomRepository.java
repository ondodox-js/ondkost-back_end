package com.ondodox.kosan.picture_room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRoomRepository extends JpaRepository<PictureRoom, Long> {
}
