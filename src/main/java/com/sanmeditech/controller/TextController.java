package com.sanmeditech.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sanmeditech.socket.kewo.Repeater;
import com.sanmeditech.socket.kewo.Request;
import com.sanmeditech.socket.kewo.Response;
import com.sanmeditech.socket.utils.ByteUtils;

/**
 * Created by lwq on 2019/11/25.
 */
@Controller
@RequestMapping("/text/")
public class TextController {

    @ResponseBody
    @RequestMapping("decodeRequest")
    public Object decodeRequest(@RequestBody JSONObject json) {
        Request request = new Request();
        byte[] bytes = ByteUtils.hexString2ByteArray(json.getString("bytes"));
        request.decode(bytes);
        return request;
    }

    @ResponseBody
    @RequestMapping("encodeRequest")
    public Object encodeRequest(@RequestBody JSONObject json) {
        byte[] lora = ByteUtils.hexString2ByteArray(json.getString("lora"));
        byte[] option = ByteUtils.hexString2ByteArray(json.getString("optionData"));
        byte[] cmdData = ByteUtils.hexString2ByteArray(json.getString("cmdData"));
        byte[] cnt = ByteUtils.hexString2ByteArray(json.getString("cnt"));
        byte[] bytes = Request.encode(lora, option, cmdData, cnt);
        return ByteUtils.hexString(bytes);
    }

    @ResponseBody
    @RequestMapping("decodeResponse")
    public Object decodeResponse(@RequestBody JSONObject json) {
        Response response = new Response();
        byte[] bytes = ByteUtils.hexString2ByteArray(json.getString("bytes"));
        response.decode(bytes);
        return response;
    }

    @ResponseBody
    @RequestMapping("encodeResponse")
    public Object encodeResponse(@RequestBody JSONObject json) {
        byte[] lora = ByteUtils.hexString2ByteArray(json.getString("lora"));
        byte[] option = ByteUtils.hexString2ByteArray(json.getString("optionData"));
        byte[] eventData = ByteUtils.hexString2ByteArray(json.getString("eventData"));
        byte[] cnt = ByteUtils.hexString2ByteArray(json.getString("cnt"));
        byte[] bytes = Response.encode(lora, option, eventData, cnt);
        return ByteUtils.hexString(bytes);
    }

}
