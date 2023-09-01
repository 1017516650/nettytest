package org.example.NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{
        FileInputStream in = new FileInputStream("src/main/java/org/example/NIO/file/1.txt");
        FileChannel inChannel = in.getChannel();
        FileOutputStream out = new FileOutputStream("src/main/java/org/example/NIO/file/2.txt");
        FileChannel outChannel = out.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true){
            byteBuffer.clear();
            int read = inChannel.read(byteBuffer);
            System.out.println("read = " + read);
            if (read == -1){
                break;
            }

            byteBuffer.flip();
            outChannel.write(byteBuffer);
        }

        in.close();
        out.close();
    }
}
