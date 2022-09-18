package com.ondodox.kosan.renter.dto;

import lombok.Data;

@Data
public class RenterData {
    int period;
    Long userId;
    Long roomId;
    String name;
}
