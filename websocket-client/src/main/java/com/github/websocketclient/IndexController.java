package com.github.websocketclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/websocket")
public class IndexController {

    @Autowired
    private ScoketClient webScoketClient;

    @GetMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(@RequestParam("message") String message) {
        webScoketClient.groupSending(message);
        return message;
    }

    @GetMapping("test")
    public String test() {
        return "ok";
    }
}