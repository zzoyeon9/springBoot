package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@Slf4j //Logger log = LoggerFactory.getLogger(getClass())이런 거를 안써도 되게 해주는 롬
public class HomeController {


    @RequestMapping("/")//URL
    public String home() {
        log.info("home controller");
        return "home";//home.html로 찾아가게됨
    }
}
