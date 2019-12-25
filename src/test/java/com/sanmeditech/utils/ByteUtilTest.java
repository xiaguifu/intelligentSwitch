package com.sanmeditech.utils;

import com.sanmeditech.socket.utils.ByteUtils;
import org.junit.Test;

/**
 * Created by lwq on 2019/12/11.
 */
public class ByteUtilTest {

    @Test
    public void hexToByteTest(){
        String data = "0x0B2F";
        byte[] bytes = ByteUtils.hexString2ByteArray(data);
        for(byte b : bytes) {
            System.out.println(b);
        }
    }

}
