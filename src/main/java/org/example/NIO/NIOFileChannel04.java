package org.example.NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception{
        FileInputStream in = new FileInputStream(
                "src/main/java/org/example/NIO/file/Snipaste_2023-08-29_17-43-55.jpg");
        FileOutputStream out = new FileOutputStream("src/main/java/org/example/NIO/file/2.jpg");

        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();

        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
        in.close();
        out.close();
    }
}
