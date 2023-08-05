package kg.kuban.airport.controller.v1;

import messaging.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
//    private final MessageHelper messageHelper;
//
//    @Autowired
//    public TestController(MessageHelper messageHelper) {
//        this.messageHelper = messageHelper;
//    }
//
//    @GetMapping(produces = "application/json?utf-16")
//    public String getMessage() {
//        String result = this.messageHelper.getMessage("first.test");
//        return result;
//    }
}
