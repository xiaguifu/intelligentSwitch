package com.sanmeditech.controller;

import com.alibaba.fastjson.JSONObject;
import com.sanmeditech.socket.kewo.CRCChecker;
import com.sanmeditech.socket.kewo.Repeater;
import com.sanmeditech.socket.utils.ByteUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lwq on 2019/12/19.
 */
@Controller
@RequestMapping("/kewo/")
public class KewoController {

    @ResponseBody
    @RequestMapping("check")
    public Object check(@RequestBody JSONObject json) {
        String data = json.getString("data");
        byte[] bytes = ByteUtils.hexString2ByteArray(data);
        byte[] crc = CRCChecker.checkCRC16(bytes, bytes.length);
        return ByteUtils.hexString(crc);
    }

    @ResponseBody
    @RequestMapping("decode")
    public Object decode(@RequestBody JSONObject json) {
        Repeater repeater = new Repeater();
        byte[] bytes = ByteUtils.hexString2ByteArray(json.getString("bytes"));
        repeater.decode(bytes);
        return repeater;
    }

    @ResponseBody
    @RequestMapping("encode")
    public Object encode(@RequestBody JSONObject json) {
        byte[] address = ByteUtils.hexString2ByteArray(json.getString("address"));
        boolean isRequest = json.getBoolean("isRequest");
        byte[] body = ByteUtils.hexString2ByteArray(json.getString("body"));
        byte[] bytes = Repeater.encode(address, isRequest, body);
        return ByteUtils.hexString(bytes);
    }

}
