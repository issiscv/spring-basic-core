package lifecycle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    
    @Test
    @DisplayName("빈 생명 주기")
    void lifeCycle() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycle.class);
        NetworkClient client = ac.getBean(NetworkClient.class);

        ac.close();
    }

    @Configuration
    static class LifeCycle {

        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://localhost:5000");
            return networkClient;
        }
    }
}
