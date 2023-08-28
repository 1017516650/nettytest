package org.example.NIO;

import java.nio.IntBuffer;

/**
 * ClassName: BasicBuffer
 * Description:
 * date: 2023/8/28 0028 下午 9:46
 *
 * @author 丁宇
 * @since JDK 8
 */
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
