package com.sanmeditech.config;

/**
 * Created by lwq on 2019/12/18.
 */
public class BodyResult {

    public static final BodyResult SUCCESS = new BodyResult(0);

    private int code;
    private String message;
    private Object data;

    public BodyResult(int code){
        this(code,null,null);
    }

    public BodyResult(int code,String message){
        this(code,message,null);
    }

    public BodyResult(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BodyResult success(Object data){
        return new BodyResult(0,null,data);
    }

    public int getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }

    public Object getData(){
        return this.data;
    }

}
