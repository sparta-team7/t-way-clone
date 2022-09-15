package com.example.intermediate.discount;

import com.example.intermediate.domain.Member;

public interface DiscountPolicy {

    int discount(Member member, int amount);
}
