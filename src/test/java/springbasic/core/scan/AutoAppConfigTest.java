package springbasic.core.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbasic.core.AppConfig;
import springbasic.core.AutoAppConfig;
import springbasic.core.member.MemberRepository;
import springbasic.core.member.MemberService;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);


    }
}
