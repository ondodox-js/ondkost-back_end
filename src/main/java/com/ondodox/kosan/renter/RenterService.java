package com.ondodox.kosan.renter;

import com.ondodox.kosan.renter.dto.DescRenterData;
import com.ondodox.kosan.renter.dto.RenterData;
import com.ondodox.kosan.room.Room;
import com.ondodox.kosan.room.RoomService;
import com.ondodox.kosan.user.User;
import com.ondodox.kosan.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RenterService {
    private final RenterRepository renterRepository;
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public RenterService(RenterRepository renterRepository, UserService userService, RoomService roomService) {
        this.renterRepository = renterRepository;
        this.userService = userService;
        this.roomService = roomService;
    }

    public Renter save(RenterData body) {
        Room room = roomService.findOne(body.getRoomId());
        User user = new User();

        if (null == body.getUserId()){
            user.setName(body.getName());
            userService.save(user);
        }else {
            user.setId(body.getUserId());
        }

        Renter newRenter = new Renter();
        newRenter.setUser(user);
        newRenter.setRoom(room);
        newRenter.setPeriod(body.getPeriod());
        newRenter.setCreatedAt(LocalDateTime.now());
        newRenter = renterRepository.save(newRenter);

        if (newRenter.getId() != null){
            room.setStatus(true);
            roomService.save(room);
        }

        return  newRenter;
    }

    public List<Renter> findAll() {
        return renterRepository.findAll();
    }

    public Renter findOne(Long renterId) {
        Optional<Renter> renter = renterRepository.findById(renterId);
        return renter.orElseThrow();
    }

    public List<Renter> findAllByStatus(boolean status) {
        return renterRepository.findAllByStatus(status);
    }

    public DescRenterData descRenter(Renter renter){
        DescRenterData renterData = new DescRenterData();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime longLease = renter.getCreatedAt().plusMonths(renter.getPeriod());
        renterData.setEnd(longLease);

        renterData.setLate(now.isAfter(longLease));
        renterData.setManyMonth((int) ChronoUnit.MONTHS.between(now, longLease));

        return renterData;
    }

    public Renter save(Renter renter) {
        return renterRepository.save(renter);
    }

    public List<Renter> findAllByTypeRoomIdAndStatus(Long typeId, boolean status) {
        return renterRepository.findAllByRoom_TypeRoomIdAndStatus(typeId, status);
    }
}
