package com.example.intermediate.discount;

import com.example.intermediate.Enum.GradeEnum;
import com.example.intermediate.domain.Member;
import org.springframework.stereotype.Component;


@Component
public class RateDiscountPolicy implements DiscountPolicy{
    //50프로 할인
    private int discountpersent = 50;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == GradeEnum.Vip){
            return price * discountpersent/100;
        }else {
            return 0;
        }

    }
}
