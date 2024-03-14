package com.toy.ecommerce.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePointForm {

    private String from;

    private String message;

    private int changePoint;
}
