package com.sanmeditech.controller;

import com.alibaba.fastjson.JSONObject;
import com.sanmeditech.config.BodyResult;
import com.sanmeditech.socket.client.ProtocolHandler;
import com.sanmeditech.socket.client.SocketClient;
import com.sanmeditech.socket.client.ClientConfig;
import com.sanmeditech.socket.kewo.CRCChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sanmeditech.socket.kewo.Repeater;
import com.sanmeditech.socket.utils.ByteUtils;

/**
 * Created by lwq on 2019/11/25.
 */
@Controller
@RequestMapping("/client/")
public class ClientController {

    private static SocketClient socketClient;

    @Autowired
    private ProtocolHandler handler;

    @Autowired
    public void setClient(SocketClient client) {
        socketClient = client;
        socketClient.setHandler(this.handler);
    }

    @ResponseBody
    @RequestMapping("config")
    public Object config() {
        return new ClientConfig();
    }

    @ResponseBody
    @RequestMapping("reconnect")
    public Object reconnect(@RequestBody ClientConfig config) {
        socketClient.stop();
        socketClient.start(config);
        return BodyResult.SUCCESS;
    }

    @ResponseBody
    @RequestMapping("stop")
    public Object stop() {
        socketClient.stop();
        return BodyResult.SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value = "send")
    public Object send(@RequestBody JSONObject json) {
        try {
            String data = json.getString("data");
            byte[] bytes = ByteUtils.hexString2ByteArray(data);
            handler.send(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return new BodyResult(1, e.getMessage());
        }
        return BodyResult.SUCCESS;
    }

    @ResponseBody
    @RequestMapping("receive")
    public Object receive() {
        byte[] bytes = this.handler.getReceive();
        if (null != bytes && bytes.length > 0) {
            return ByteUtils.hexString(bytes);
        }
        return BodyResult.SUCCESS;
    }

}
