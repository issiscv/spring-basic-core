package springbasic.core.beanfind;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbasic.core.AppConfig;
import springbasic.core.discount.DiscountPolicy;
import springbasic.core.discount.FixDiscountPolicy;
import springbasic.core.discount.RateDiscountPolicy;
import springbasic.core.member.MemberRepository;
import springbasic.core.member.MemoryMemberRepository;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면 중복 오류가 발생한다.")
    void findBeanByDuplicate() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
    void findBeanByName() {
        DiscountPolicy discountPolicy = ac.getBean("discountPolicy", DiscountPolicy.class);

        assertThat(discountPolicy).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);

        for (String s : beansOfType.keySet()) {
            System.out.println("key = " + s + " value: " + beansOfType.get(s));
        }

        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Configuration
    static class SameBeanConfig {

        @Bean
        public DiscountPolicy discountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy discountPolicy2() {
            return new FixDiscountPolicy();
        }
    }
}
