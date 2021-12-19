package springbasic.core.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import springbasic.core.member.Grade;
import springbasic.core.member.Member;

import static org.assertj.core.api.Assertions.*;


class RateDiscountPolicyTest {

    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void vip_o() {

        //given
        Member memberA = new Member(1L, "memberA", Grade.VIP);

        //when
        int discount = discountPolicy.discount(memberA, 10000);

        //then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아니면 할인 적용 X")
    void vip_x() {

        //given
        Member memberA = new Member(1L, "memberBasic", Grade.BASIC);

        //when
        int discount = discountPolicy.discount(memberA, 10000);

        //then
        assertThat(discount).isEqualTo(0);
    }
}