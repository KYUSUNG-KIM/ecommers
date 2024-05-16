package com.toy.order.client;

import jakarta.validation.constraints.Min;

public record PointDeductRequest(@Min(0) Integer totalAmount,
                                 String message) {
}
