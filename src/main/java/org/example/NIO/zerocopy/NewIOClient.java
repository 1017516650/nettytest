package org.example.NIO.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));

        String filename = "src/main/java/org/example/NIO/file/aida64business625.zip";
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总的字节数 = " + transferCount + " 耗时: " + (System.currentTimeMillis() - startTime));

        fileChannel.close();
    }
}
