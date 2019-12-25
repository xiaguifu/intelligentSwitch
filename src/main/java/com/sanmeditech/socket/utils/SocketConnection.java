package com.sanmeditech.socket.utils;

import com.sanmeditech.socket.handler.ConsoleHandler;
import com.sanmeditech.socket.handler.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lwq on 2019/11/21.
 */
public class SocketConnection {

    public static final String default_remote = "127.0.0.1";
    public static final int default_port = 30007;
    public static final int default_read_timeout = 1000 * 45;
    public static final int default_connect_timeout = 1000 * 45;
    public static final boolean default_keep_alive = false;

    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private boolean connectionStatus = false;
    private Set<Handler> handlers;

    public static SocketConnection open() throws IOException {
        return new SocketConnection(default_remote, default_port, default_connect_timeout, default_read_timeout, default_keep_alive, new ConsoleHandler());
    }

    public static SocketConnection open(String remote, int port) throws IOException {
        return new SocketConnection(remote, port, default_connect_timeout, default_read_timeout, default_keep_alive, new ConsoleHandler());
    }

    public static SocketConnection open(String remote, int port, int connectTimeout, int readTimeout, boolean keepAlive) throws IOException {
        return new SocketConnection(remote, port, connectTimeout, readTimeout, keepAlive, new ConsoleHandler());
    }

    public static SocketConnection open(Handler handler) throws IOException {
        return new SocketConnection(default_remote, default_port, default_connect_timeout, default_read_timeout, default_keep_alive, handler);
    }

    public static SocketConnection open(String remote, int port, Handler handler) throws IOException {
        return new SocketConnection(remote, port, default_connect_timeout, default_read_timeout, default_keep_alive, handler);
    }

    public static SocketConnection open(String remote, int port, int connectTimeout, int readTimeout, boolean keepAlive, Handler handler) throws IOException {
        return new SocketConnection(remote, port, connectTimeout, readTimeout, keepAlive, handler);
    }

    private SocketConnection(String remote, int port, int connectTimeout, int readTimeout, boolean keepAlive, Handler handler) throws IOException {
        this.handlers = new HashSet<Handler>();
        this.socket = new Socket();
        this.socket.setSoTimeout(readTimeout); //设置读取超时时间
        this.socket.setKeepAlive(keepAlive);
        this.socket.connect(new InetSocketAddress(remote, port), connectTimeout); //设置连接超时
        this.input = this.socket.getInputStream();
        this.output = this.socket.getOutputStream();
        this.addHandler(handler);
    }

    public void addHandler(Handler handler){
        this.handlers.add(handler);
    }

    public byte[] request(byte[] data){
        try {
            this.output.write(data);
            this.output.flush();
            byte[] buff = new byte[1024];
            byte[] bytes = new byte[0];
            boolean hasRemaining = true;
            while (hasRemaining) {
                int size = this.input.read(buff);
                int offset = bytes.length;
                bytes = new byte[offset+size];
                System.arraycopy(buff, 0, bytes, offset, size);
                for (Handler handler : this.handlers) {
                    if(handler.isOver(bytes)){
                        hasRemaining = false;
                    }
                }
            }
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                this.output.close();
                this.input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void close() {
        if (null != this.input) {
            try {
                this.input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != this.output) {
            try {
                this.output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != this.socket) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String data) throws IOException {
        if (null != data) {
            data = data.replaceAll("0x", "").replaceAll(" ", "");
            data = data.toUpperCase();
            if (data.length() > 0 && data.matches("([0-9]|[A-F])+")) {
                byte[] bytes = ByteUtils.hexString2ByteArray(data);
                this.request(bytes);
            }
        }
    }

    public boolean isConnected() {
        return null != this.socket && this.socket.isConnected();
    }
}
