package com.ondodox.kosan.renter.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class DescRenterData {
    boolean isLate;
    int manyMonth;
    LocalDateTime end;
}
