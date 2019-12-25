package com.sanmeditech.controller;

import com.alibaba.fastjson.JSONObject;
import com.sanmeditech.config.BodyResult;
import com.sanmeditech.socket.server.RepeaterHandler;
import com.sanmeditech.socket.server.RepeaterServer;
import com.sanmeditech.socket.server.ServerConfig;
import com.sanmeditech.socket.utils.ByteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by lwq on 2019/12/18.
 */
@Controller
@RequestMapping("/server/")
public class ServerController {

    private static RepeaterServer repeaterServer;

    @Autowired
    private RepeaterHandler handler;

    @Autowired
    public void setRepeaterServer(RepeaterServer server) throws IOException {
        repeaterServer = server;
        repeaterServer.setHandler(this.handler);
    }

    @ResponseBody
    @RequestMapping("config")
    public Object config() {
        return new ServerConfig();
    }

    @ResponseBody
    @RequestMapping("restart")
    public Object restart(@RequestBody ServerConfig config) {
        try {
            repeaterServer.stop();
            repeaterServer.start(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BodyResult.SUCCESS;
    }

    @ResponseBody
    @RequestMapping("stop")
    public Object stop() {
        repeaterServer.stop();
        return BodyResult.SUCCESS;
    }

    @ResponseBody
    @RequestMapping("listClients")
    public Object listClients() {
        return this.handler.getClientList();
    }

    @ResponseBody
    @RequestMapping("send")
    public Object send(@RequestBody JSONObject json) {
        String data = json.getString("data");
        byte[] bytes = ByteUtils.hexString2ByteArray(data);
        this.handler.send(json.getLong("sessionId"), bytes);
        return BodyResult.SUCCESS;
    }

}
