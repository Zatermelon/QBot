package com.sans.qbot.cos;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sans.qbot.utils.HttpUtils;
import com.zhuangxv.bot.annotation.GroupMessageHandler;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.Group;
import com.zhuangxv.bot.message.MessageChain;
import com.zhuangxv.bot.message.support.ImageMessage;
import com.zhuangxv.bot.message.support.TextMessage;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

@Service
public class Cos {


    @GroupMessageHandler(regex = "^(cos|COS|coser|括丝)$")
    public void test(Group group, Bot bot,String msgs) throws Exception {
        MessageChain messageChain = new MessageChain();
        String baseUrl = "https://api.iyk0.com/cos/";
        messageChain.add(new ImageMessage(getRedirectUrl(baseUrl)));
        bot.sendGroupMessage(group.getGroupId(),messageChain);
    }


    private static String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path)
                .openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        return conn.getHeaderField("Location");
    }
}
