package com.breakfast.client.util;

import android.nfc.Tag;
import android.nfc.tech.MifareClassic;

import com.breakfast.library.util.StringUtils;

import java.io.IOException;

/**
 * Created by Steven on 2017/7/27.
 */

public final class NfcUtils {


    private static final short ByteCountPerBlock = 16;
    private static final short BlockCountPerSector = 4;
    private static final short ByteCountPerCluster = ByteCountPerBlock
            * BlockCountPerSector;
    private static final byte EmptyByte = -1;

    private static final byte[] KEY_A =
            {(byte)0x4c,(byte)0x83,(byte)0x6c,(byte)0x01,(byte)0x54,(byte)0x4d};
    private static final byte[] KEY_B =
            {(byte)0x6c,(byte)0xa3,(byte)0x4c,(byte)0x21,(byte)0x74,(byte)0x6d};


    // 读取方法
    public static byte[] readMifareTag(Tag tag) throws Exception {
        //tag 就是在上一篇中onNewIntent中获取的tag
        MifareClassic mc = MifareClassic.get(tag);
        short startAddress = 4;
        short endAddress = 4;

        byte[] data = new byte[(endAddress - startAddress + 1 ) * ByteCountPerBlock];
        try {
            mc.connect();
            int time = 0;
            for (short i = startAddress; i <= endAddress; i++ ,time++) {
                short sectorAddress = getSectorAddress(i);
                if (mc.authenticateSectorWithKeyA(sectorAddress, KEY_A)){

                    //the last block of the sector is used for KeyA and KeyB cannot be overwritted
                    short readAddress = 4;//(short)(sectorAddress == 0 ? i : i + sectorAddress);
                    byte[] response = mc.readBlock(readAddress);
                    CombineByteArray(data, response, time * ByteCountPerBlock);
                }
                else{
                    throw new Exception(
                            "Authorization Error.");
                }
            }

        }
        catch (IOException e) {
            throw new Exception(
                    "Get response, what it is not successfully.");
        }
        catch (Exception ne) {
            throw ne;
        }

        finally
        {
            try {
                mc.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return data;
    }



    /**
     * Copy the data from @param data2 to @param data1.
     *
     * @param data1
     *            target data
     * @param data2
     *            source data
     * @param startIndex
     *            from which index to copy
     */
    private static void CombineByteArray(byte[] data1, byte[] data2, int startIndex) {
        for (int i = 0; i < data2.length; i++) {
            data1[startIndex + i] = data2[i];
        }
    }

    private static short getSectorAddress(short blockAddress) {
        return (short) (blockAddress / BlockCountPerSector);
    }

}
