package com.cornucopia;

import com.cornucopia.utils.ByteUtils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ByteTest {

    @Test
    public void testByteToShort() {
        byte[] bt = new byte[]{-128, 127};
        short ibt = ByteUtils.byteArrayToShort(bt);
        //  ->
//        assertEquals(10, ibt);

        byte[] bt2 = new byte[]{0, 1};
        short ibt2 = ByteUtils.byteArrayToShort(bt2);
        assertEquals(1, ibt2);

        byte[] bt3 = new byte[]{1, 0};
        short ibt3 = ByteUtils.byteArrayToShort(bt3);
        // 00000001 00000000
        int exp3 = (int) Math.pow(2, 8);
        assertEquals(exp3, ibt3);
    }


    @Test
    public void testByteToIntMax() {
        // 11111111 11111111 11111111 11111111
        byte[] btMax = new byte[]{127, 127, 127, 127};  // 2139062143
        int ibtMax = ByteUtils.byteArrayToInt(btMax);

        // (Math.pow(2, 32) - 1)
        // 01111111 11111111 11111111 11111111
        int max = Integer.MAX_VALUE;  // 2147483647
        assertThat("integer max value is not: [127, 127, 127, 127]", max, is(ibtMax));
    }

    @Test
    public void testByteToIntMin() {
        byte[] btMin = new byte[]{-128, 0, 0, 0};  // -2147483648
        int ibtMin = ByteUtils.byteArrayToInt(btMin);

        int min = Integer.MIN_VALUE;
        assertThat("integer min value: [-128, 0, 0, 0]", min, is(ibtMin));
    }

}
