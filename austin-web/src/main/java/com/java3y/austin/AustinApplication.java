package com.java3y.austin;

import com.java3y.austin.handler.domain.sms.SmsParam;
import com.java3y.austin.handler.script.impl.TestSendSms;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashSet;


/**
 * @author 3y
 */
@SpringBootApplication
public class AustinApplication {
    public static void main(String[] args) {

        /**
         * 如果你需要启动Apollo动态配置
         * 1、启动apollo
         * 2、将application.properties配置文件的 austin.apollo.enabled 改为true
         * 3、下方的property替换真实的ip和port
         */
       // System.setProperty("apollo.config-service", "http://austin-apollo-config:8080");
        SpringApplication.run(AustinApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello");
        TestSendSms testSendSms=new TestSendSms();
        SmsParam smsParam = SmsParam.builder()
                .phones(new HashSet<>(Arrays.asList("18142349313")))
                .phones(new HashSet<>(Arrays.asList("//")))
                .content("23333")
                .build();

        return testSendSms.send(smsParam);


    }
}
