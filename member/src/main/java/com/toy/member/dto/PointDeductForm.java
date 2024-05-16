package com.toy.member.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDeductForm {

    @Min(0)
    private int totalAmount;

    private String message;
}
