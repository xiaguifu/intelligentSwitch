package com.sanmeditech.socket.server;

/**
 * Created by lwq on 2019/12/18.
 */
public class Client {

    private Long sessionId;
    private byte[] repeaterId;
    private byte[] loraId;
    private byte[] extend;
    private byte[] emitter;
    private byte[] number;
    private byte[] crc;

    public Client(Long sessionId){
        setSessionId(sessionId);
    }

    public void setSessionId(Long sessionId){
        this.sessionId = sessionId;
    }

    public Long getSessionId(){
        return this.sessionId;
    }

    public byte[] getRepeaterId() {
        return repeaterId;
    }

    public void setRepeaterId(byte[] repeaterId) {
        this.repeaterId = repeaterId;
    }

    public byte[] getLoraId() {
        return loraId;
    }

    public void setLoraId(byte[] loraId) {
        this.loraId = loraId;
    }

    public byte[] getExtend() {
        return extend;
    }

    public void setExtend(byte[] extend) {
        this.extend = extend;
    }

    public byte[] getEmitter() {
        return emitter;
    }

    public void setEmitter(byte[] emitter) {
        this.emitter = emitter;
    }

    public byte[] getNumber() {
        return number;
    }

    public void setNumber(byte[] number) {
        this.number = number;
    }

    public byte[] getCrc() {
        return crc;
    }

    public void setCrc(byte[] crc) {
        this.crc = crc;
    }
}
