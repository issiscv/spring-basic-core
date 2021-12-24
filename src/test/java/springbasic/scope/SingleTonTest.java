package springbasic.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingleTonTest {

    @Test
    void singleTonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingleToneBean.class);

        //얘는 생성할 때 init
        SingleToneBean singleToneBean1 = ac.getBean(SingleToneBean.class);
        SingleToneBean singleToneBean2 = ac.getBean(SingleToneBean.class);

        System.out.println("singleToneBean1 = " + singleToneBean1);
        System.out.println("singleToneBean2 = " + singleToneBean2);

        Assertions.assertThat(singleToneBean1).isSameAs(singleToneBean2);

        ac.close();
    }

    @Scope("singleton")
    static class SingleToneBean {

        @PostConstruct
        public void init() {
            System.out.println("SingleToneBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingleToneBean.destroy");
        }
    }
}
