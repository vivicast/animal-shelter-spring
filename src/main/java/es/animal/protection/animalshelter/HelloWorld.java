package es.animal.protection.animalshelter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloWorld {

    @RequestMapping("/")
    String home(){
        return "Hello World";
    }

}
