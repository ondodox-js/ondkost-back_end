package com.ondodox.kosan.room.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoomDTO {
    @NotNull(message = "roomNumber is required.") @Min(value = 1, message = "roomNumber must be more than 0.")
    private Long roomNumber;
}
