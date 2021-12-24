package springbasic.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient  {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호츌, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }
    
    //서비스 종료시 호출
    public void disConnect() {
        System.out.println("close " + url);
    }

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.destroy");
        disConnect();
    }
}
