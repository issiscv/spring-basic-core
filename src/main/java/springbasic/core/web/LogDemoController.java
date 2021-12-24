package springbasic.core.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springbasic.core.common.MyLogger;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final MyLogger myLogger;
    private final LogDemoService logDemoService;

    @GetMapping("/log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {

        System.out.println("myLogger : " + myLogger.getClass());

        myLogger.setRequestURL(request.getRequestURI());

        myLogger.log("controller test");
        logDemoService.logic("testid");

        return "ok";
    }
}
