package org.example.NIO;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        File file = new File("src/main/java/org/example/NIO/file/file01.txt");
        FileInputStream in = new FileInputStream(file);
        FileChannel fileChannel = in.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        in.close();
    }
}
