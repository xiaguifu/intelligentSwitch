package com.sanmeditech.socket.utils;

/**
 * Created by lwq on 2019/11/21.
 */
public class ByteUtils {

    public static final char[] chars_16 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static int indexOf(char c) {
        for (int i = 0; i < chars_16.length; i++) {
            if (chars_16[i] == c) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException("not in");
    }

    public static byte hexString2Byte(String str) {
        assert null != str;
        str = str.replaceAll("0x","").toUpperCase();
        assert str.length() == 2 && str.matches("([0-9]|[A-F]){2}");
        return (byte) ((0xFF & (indexOf(str.charAt(0)) << 4)) | (0xFF & indexOf(str.charAt(1))));
    }

    private static byte hexString2Byte(String str,int position){
        assert str.length() >= position + 2;
        return (byte) ((0xF0 & (indexOf(str.charAt(position)) << 4)) | (0x0F & indexOf(str.charAt(position+1))));
    }

    public static byte[] hexString2ByteArray(String str) {
        assert null != str ;
        str = str.replaceAll("0x","").replaceAll(" ","").toUpperCase();
        assert str.length() % 2 == 0 && str.matches("([0-9]|[A-Z]){2,}");
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i ++) {
            bytes[i] = hexString2Byte(str,i*2);
        }
        return bytes;
    }

    public static void appendTo(byte[] target, byte[] source, int offset) {
        assert offset + source.length <= target.length;
        System.arraycopy(source, 0, target, offset, source.length);
    }

    public static byte[] read(byte[] bytes, int start, int length) {
        assert start + length <= bytes.length;
        byte[] data = new byte[length];
        System.arraycopy(bytes, start, data, 0, length);
        return data;
    }

    public static byte[] sub(byte[] bytes, int start) {
        return sub(bytes, start, bytes.length - 1);
    }

    public static byte[] sub(byte[] bytes, int start, int end) {
        int length = end - start + 1;
        assert null != bytes && bytes.length >= end + 1 && length > 0;
        byte[] data = new byte[length];
        System.arraycopy(bytes, start, data, 0, length);
        return data;
    }

    public static String hexString(byte b) {
        return String.format("%02X", b);
    }

    public static String hexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(2*bytes.length);
        for (byte b : bytes) {
            sb.append(hexString(b));
        }
        return sb.toString();
    }
}
