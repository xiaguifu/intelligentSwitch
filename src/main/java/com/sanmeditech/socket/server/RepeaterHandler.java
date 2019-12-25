package com.sanmeditech.socket.server;

import com.sanmeditech.socket.utils.ByteUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.FilterEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by lwq on 2019/12/4.
 */
@Component
public class RepeaterHandler implements IoHandler {

    private Map<Long, IoSession> sessionMap = new Hashtable<Long, IoSession>();
    private Map<Long, Client> sessionClientMap = new Hashtable<>();

    public Map<Long, IoSession> getSessionMap() {
        return this.sessionMap;
    }

    public void put(IoSession session) {
        this.put(session, null);
    }

    public void put(IoSession session, Object data) {
        this.sessionMap.put(session.getId(), session);
        Client client = this.sessionClientMap.get(session.getId());
        if (null == client) {
            client = new Client(session.getId());
        } else {
//            client.setObject(data);//todo
        }
        this.sessionClientMap.put(session.getId(), client);
    }

    public void remove(IoSession session) {
        this.sessionMap.remove(session.getId());
        this.sessionClientMap.remove(session.getId());
    }

    public Collection<Client> getClientList() {
        return this.sessionClientMap.values();
    }

    public void send(Long sessionId,byte[] data){
        IoSession session = this.sessionMap.get(sessionId);
        if(session.isActive()){
            IoBuffer io = IoBuffer.wrap(data);
            System.out.println("send byte length"+io.remaining());
            session.write(io);
        }
    }

    public void sessionCreated(IoSession ioSession) throws Exception {
        System.out.println("session created");
        this.put(ioSession);
    }

    public void sessionOpened(IoSession ioSession) throws Exception {
        System.out.println("session opened");
        this.put(ioSession);
    }

    public void sessionClosed(IoSession ioSession) throws Exception {
        System.out.println("session closed");
        this.remove(ioSession);
    }

    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        System.out.println("session idle : " + idleStatus.toString());
    }

    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        System.out.println("exception caught : " + throwable.toString());
    }

    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        this.put(ioSession, o);//todo

        IoBuffer buffer = (IoBuffer) o;
        int length = buffer.remaining();
        byte[] data = new byte[length];
        buffer.get(data);

        System.out.println(ByteUtils.hexString(data));
        ioSession.write(IoBuffer.wrap(data));//todo
    }

    public void messageSent(IoSession ioSession, Object o) throws Exception {
        System.out.println("message sent");
    }

    public void inputClosed(IoSession ioSession) throws Exception {
        System.out.println("input closed");
        ioSession.closeNow();
    }

    public void event(IoSession ioSession, FilterEvent filterEvent) throws Exception {
        System.out.println("event : " + filterEvent.toString());
    }
}
