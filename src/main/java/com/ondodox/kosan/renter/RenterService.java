package com.ondodox.kosan.renter;

import com.ondodox.kosan.payment.Payment;
import com.ondodox.kosan.payment.PaymentService;
import com.ondodox.kosan.renter.dto.DescRenterData;
import com.ondodox.kosan.renter.dto.RenterData;
import com.ondodox.kosan.room.Room;
import com.ondodox.kosan.room.RoomService;
import com.ondodox.kosan.type_room.TypeRoom;
import com.ondodox.kosan.user.User;
import com.ondodox.kosan.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RenterService {
    private final RenterRepository renterRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final PaymentService paymentService;

    @Autowired
    public RenterService(RenterRepository renterRepository, UserService userService, RoomService roomService, PaymentService paymentService) {
        this.renterRepository = renterRepository;
        this.userService = userService;
        this.roomService = roomService;
        this.paymentService = paymentService;
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
        return renter.orElseThrow(() -> new RuntimeException("Renter not found!"));
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

    public Payment extension(Long renterId, Integer period) {
        Optional<Renter> renterOptional = renterRepository.findById(renterId);
        if (renterOptional.isPresent()){
            Renter renter = renterOptional.get();
            Room room = renter.getRoom();

            Long price = room.getTypeRoom().getPrice();

            Payment payment = new Payment();
            payment.setCreatedAt(LocalDateTime.now());
            payment.setDescription(this.descriptionPayment(renter, period));
            payment.setTotal(price * period);

            renter.setPeriod(renter.getPeriod() + period);
            this.save(renter);
            payment.setRenter(renter);

            return paymentService.save(payment);
        }

        return null;
    }

    public String descriptionPayment(Renter renter, Integer period){
        LocalDateTime startDate = this.descRenter(renter).getEnd();
        if (period > 1){
            LocalDateTime endDate = startDate.plusMonths(period - 1);

            String startMonth = startDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String lastMonth = endDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            return period + " month payment ( " + startMonth + " - " + lastMonth + " )";
        }else{
            String month = startDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            return period + " month payment ( " + month + " )";
        }
    }

    public Renter finished(Long renterId) {
        Renter renter = this.findOne(renterId);
        renter.getRoom().setStatus(false);
        renter.setStatus(false);
        return this.save(renter);
    }
}
