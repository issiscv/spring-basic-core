package springbasic.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbasic.core.AppConfig;
import springbasic.core.member.MemberRepository;
import springbasic.core.member.MemberService;
import springbasic.core.member.MemberServiceImpl;
import springbasic.core.member.MemoryMemberRepository;
import springbasic.core.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingleTonTest {


    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemoryMemberRepository memberRepository = ac.getBean("memberRepository", MemoryMemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberRepository1).isSameAs(memberRepository2);

    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //내가 만든 클래스가 아니라 CGLIB라는 바이트코드 조작 라이브러리를 사용함
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = " + bean);
    }
}
