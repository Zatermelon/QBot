package com.sans.qbot.setu;

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

import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

@Service
public class SeTu {


    @GroupMessageHandler(regex = "^色图.*.*")
    public void test(Group group, Bot bot,String msgs){
        msgs = msgs.replace("色图 ","");msgs = msgs.replace("色图","");
        String[] strs = msgs.split(" ");
        JSONObject jsStr = null;
        if(strs.length == 1){
            if(strs[0].contains("18")){
                jsStr = getXpath("",strs[0]);
            }else{
                jsStr = getXpath(strs[0],"");
            }
        }else if(strs.length == 2 ){
            if(strs[1].contains("18")){
                jsStr = getXpath(strs[0],strs[1]);
            }else{
                jsStr = getXpath(strs[1],strs[0]);
            }
        }
        JSONArray data = (JSONArray) jsStr.get("data");
        if(data.size() == 0){
            MessageChain messageChain = new MessageChain();
            TextMessage textMessage = new TextMessage("^~^图片太色了,然后我就吃掉了!");
            messageChain.add(textMessage);
            bot.sendGroupMessage(group.getGroupId(),messageChain);
            return;
        }else{
            MessageChain messageChain = new MessageChain();
            TextMessage textMessage = new TextMessage("来了来了,等等再冲,加载一下!");
            messageChain.add(textMessage);
            bot.sendGroupMessage(group.getGroupId(),messageChain);
        }
        for (Object datum : data) {
            Map<String, Object> map = (Map<String, Object>) datum;
            Map<String, Object> url = (Map<String, Object>) map.get("urls");
            url.get("original");
            MessageChain messageChain = new MessageChain();
            ImageMessage imageMessage = new ImageMessage(url.get("original").toString());
            TextMessage textMessage = new TextMessage("pid:"+map.get("pid")+"\n"+"title:"+map.get("title")+"\n");
            messageChain.add(textMessage);
            messageChain.add(imageMessage);
            bot.sendGroupMessage(group.getGroupId(),messageChain);
        }
    }

    public static JSONObject getXpath(String keyword,String r18) {
        String baseUrl = "https://api.lolicon.app/setu/v2";
        String param = "";
        if (keyword != null && !keyword.equals("")) {
            try{
                param = "keyword=" + URLEncoder.encode(keyword, "UTF-8");
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
        if(r18.equals("r18") || r18.equals("R18")){
            if (r18.length() > 3 ){
                getXpath(r18.replace("r18",""),"r18");
            }
            if(param.equals("")) {
                param = "r18=1";
            }else{
                param = param + "&r18=1";
            }
        }
        HttpUtils httpUtils = new HttpUtils();
        String result = httpUtils.sendGet(baseUrl,param);
        return new JSONObject().parseObject(result);
    }
}
