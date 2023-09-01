package org.example.NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;

        while (true){
            int byteRead = 0;
            while (byteRead < messageLength){
                long l = socketChannel.read(byteBuffers);
                byteRead += 1;
                System.out.println();
                System.out.println(" byteRead = " + byteRead);
                Arrays.asList(byteBuffers).stream().map(buffer -> " position = "
                        + buffer.position() + ", limit = " + buffer.limit()).forEach(System.out::print);
            }

            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

            long byteWirte = 0;
            while (byteWirte < messageLength){
                long l = socketChannel.write(byteBuffers);
                byteWirte += 1;
            }

            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.clear();
            });

            System.out.println("byteRead = " + byteRead + ", byteWirte = " + byteWirte + ", messagelength = " + messageLength);
        }


    }
}
