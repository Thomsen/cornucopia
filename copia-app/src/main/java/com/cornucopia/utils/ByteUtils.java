package com.cornucopia.utils;

import java.nio.ByteBuffer;

public class ByteUtils {
    
    private ByteUtils() {}

    public static int toUnsignedByte(byte b) {
        return b & 0xFF;
    }
    
    // byte - 8bit, short - 16bit, int - 32bit , long - 64bit, float - 32bit, double - 64bit, char - 16bit unicode
    // byte [-128, 127], char [0, 65535]
    
    /**
     * byte array to short
     * @param b
     * @return
     */
    public static short byteArrayToShort(byte[] b) {
        if (b.length != 2) {
            throw new UnsupportedOperationException("byte length is not short.");
        }
        
        return (short) ((b[1] & 0xFF) | ((b[0] & 0xFF) << 8));
    }
    
    /**
     * short to byte array
     * @param a
     * @return
     */
    public static byte[] shortToByteArray(short a) {
        return new byte[] {
                (byte) ((a >> 8 ) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
    
    /**
     * byte array to int
     * @param b
     * @return
     */
    public static int byteArrayToInt(byte[] b) {
        if (b.length != 4) {
            throw new UnsupportedOperationException("byte length is not integer.");
        }
        return  b[3] & 0xFF |  
                (b[2] & 0xFF) << 8 |  
                (b[1] & 0xFF) << 16 |  
                (b[0] & 0xFF) << 24;  
    }

    /**
     * int to byte array
     * @param a
     * @return
     */
    public static byte[] intToByteArray(int a) {  
        return new byte[] {  
            (byte) ((a >> 24) & 0xFF),  
            (byte) ((a >> 16) & 0xFF),     
            (byte) ((a >> 8) & 0xFF),     
            (byte) (a & 0xFF)  
        };  
    }
    
    /**
     * byte array to long by big-endian
     * @param b
     * @return
     */
    public static long byteArrayToLongBig(byte[] b) {
        if (b.length != 8) {
            throw new UnsupportedOperationException("byte length is not long.");
        }
        // byte endian (big or little)
        // big-endian 
        // little-endian 
        long c = (long) b[7] & 0x000000FF;  
        c += (long) (b[6] & 0x000000FF) << 8;  
        c += (long) (b[5] & 0x000000FF) << 16;  
        c += (long) (b[4] & 0x000000FF) << 24;
        c += (long) (b[3] & 0x000000FF) << 32;
        c += (long) (b[2] & 0x000000FF) << 40;
        c += (long) (b[1] & 0x000000FF) << 48;
        c += (long) (b[0] & 0x000000FF) << 56;
        return c;
    }
    
    /**
     * long to byte array by big-endian
     * @param a
     * @return
     */
    public static byte[] longToByteArrayBig(long a) {
        return new byte[] {
                (byte) ((a >> 56) & 0xFF),  
                (byte) ((a >> 48) & 0xFF),  
                (byte) ((a >> 40) & 0xFF),  
                (byte) ((a >> 32) & 0xFF),  
                (byte) ((a >> 24) & 0xFF),  
                (byte) ((a >> 16) & 0xFF),     
                (byte) ((a >> 8) & 0xFF),     
                (byte) (a & 0xFF)  
            };
    }
    
    /**
     * byte array to long by little-endian
     * @param b
     * @return
     */
    public static long byteArrayToLongLittle(byte[] b) {
        if (b.length != 8) {
            throw new UnsupportedOperationException("byte length is not long.");
        }
        // byte endian (big or little)
        // big-endian 
        // little-endian 
        long c = (long) b[0] & 0x000000FF;  
        c += (long) (b[1] & 0x000000FF) << 8;  
        c += (long) (b[2] & 0x000000FF) << 16;  
        c += (long) (b[3] & 0x000000FF) << 24;
        c += (long) (b[4] & 0x000000FF) << 32;
        c += (long) (b[5] & 0x000000FF) << 40;
        c += (long) (b[6] & 0x000000FF) << 48;
        c += (long) (b[7] & 0x000000FF) << 56;
        return c;
    }
    
    /**
     * long to byte array by little-endian
     * @param a
     * @return
     */
    public static byte[] longToByteArrayLittle(long a) {
        return new byte[] {
                (byte) (a & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 32) & 0xFF),
                (byte) ((a >> 40) & 0xFF),
                (byte) ((a >> 48) & 0xFF),
                (byte) ((a >> 56) & 0xFF)
            };
    }
    
    /**
     * byte array to float
     * @param b
     * @return
     */
    public static float byteArrayToFloat(byte[] b) {
        if (b.length != 4) {
            throw new UnsupportedOperationException("byte length is not float.");
        }
        int c;
        c = b[0] & 0xFF;
        c |= (b[1] << 8) & 0xFFFF;
        c |= (b[2] << 16) & 0xFFFFFF;
        c |= (b[3] << 24);
        return Float.intBitsToFloat(c);
    }
    
    /**
     * float to byte array
     * @param a
     * @return
     */
    public static byte[] floatToByteArray(float a) {
        int c = Float.floatToIntBits(a);
        int i = 0;
        byte[] b = new byte[4];
        for (i=3; i>=0; i--) {
            b[i] = new Integer(c).byteValue();
            c = c >> 8;
        }
        return b;
    }
    
    /**
     * byte array to double
     * @param b
     * @return
     */
    public static double byteArrayToDouble(byte[] b) {
        if (b.length != 8) {
            throw new UnsupportedOperationException("byte length is not double.");
        }
        
        long c = byteArrayToLongBig(b);
        return Double.longBitsToDouble(c);
        
//        return ByteBuffer.wrap(b).getDouble();
    }
    
    /**
     * double to byte array
     * @param a
     * @return
     */
    public static byte[] doubleToByteArray(double a) {
        long c = Double.doubleToLongBits(a);
        byte[] b = new byte[8];
        for (int i=7; i>=0; i--) {
            b[i] = new Long(c).byteValue();
            c = c >> 8;
        }
//        b[0] = (byte) ((c >> 56) & 0xff);
//        b[1] = (byte) ((c >> 48) & 0xff);
//        b[2] = (byte) ((c >> 40) & 0xff);
//        b[3] = (byte) ((c >> 32) & 0xff);
//        b[4] = (byte) ((c >> 24) & 0xff);
//        b[5] = (byte) ((c >> 16) & 0xff);
//        b[6] = (byte) ((c >> 8) & 0xff);
//        b[7] = (byte) ((c >> 0) & 0xff);
        
//        ByteBuffer.wrap(b).putDouble(a);
        return b;
    }
    
    /**
     * byte array to char
     * @param b
     * @return
     */
    public static char byteArrayToChar(byte[] b) {
        return (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
    }
    
    /**
     * char to byte array
     * @param a
     * @return
     */
    public static byte[] charToByteArray(char a) {
        byte[] b = new byte[2];
        b[0] = (byte) ((a & 0xFF00) >> 8);
        b[1] = (byte) (a & 0xFF);
        return b;
    }
    
    /**
     * byte array to string
     * @param b
     * @return
     */
    public static String byteArrayToString(byte[] b) {
        String s = new String(b);
        return s;
    }
    
    /**
     * string to byte array
     * @param s
     * @return
     */
    public static byte[] stringToByteArray(String s) {
        byte[] b = null;
        if (null != s) {
            b = s.getBytes();
        }
        return b;
    }

}
