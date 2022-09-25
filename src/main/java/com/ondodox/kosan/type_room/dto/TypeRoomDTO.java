package com.ondodox.kosan.type_room.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TypeRoomDTO {
    @NotEmpty(message = "typename is required.")
    private String typeName;
    @NotNull(message = "price is required.")
    private Long price;
    @NotEmpty(message = "description is required.")
    private String description;
}
