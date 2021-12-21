package springbasic.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingleTonService {

    private final static SingleTonService SINGLE_TON_SERVICE = new SingleTonService();

    public static SingleTonService getInstance() {
        return SINGLE_TON_SERVICE;
    }

    private SingleTonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }


}
