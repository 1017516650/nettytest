package org.example.NIO;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "hello";
        FileOutputStream out = new FileOutputStream("D:\\project3\\nettytest\\src\\main\\java\\org\\example\\NIO\\file\\file01.txt");
        FileChannel fileChannel = out.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        out.close();
    }
}
