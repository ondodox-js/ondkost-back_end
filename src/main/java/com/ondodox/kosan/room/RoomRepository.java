package com.ondodox.kosan.room;

import com.ondodox.kosan.type_room.TypeRoom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    @Query(value = "SELECT t.* FROM rooms r JOIN type_room t ON t.type_id = r.type_room_id", nativeQuery = true)
    List<Object> joinTypeRoom();

    Long countByTypeRoom_IdAndStatusFalse(Long typeRoom_id);
    Long countByTypeRoom_Id(Long typeRoom_id);

    @Query(value = "SELECT room_id FROM rooms", nativeQuery = true)
    List<Long> findAllId();

    List<Room> findByTypeRoomIdAndStatus(Long typeRoom, boolean status);

    List<Room> findAllByTypeRoomId(Long typeRoom_id);

    Integer countByTypeRoomId(Long typeId);

    Integer countByTypeRoomIdAndStatus(Long typeId,Boolean status);

    Optional<List<Room>> findByTypeRoomId(Long typeId);
}
