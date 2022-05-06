package com.sans.qbot;

import com.zhuangxv.bot.EnableBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBot
@ComponentScan(basePackages = {"com.sans.qbot.*"})
public class QBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QBotApplication.class, args);
    }

}
