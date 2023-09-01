package org.example.NIO.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;

    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        try {
            while (true){
                int count = selector.select();
                if (count > 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        System.out.println("--------------key--------------------" + key.isAcceptable() + key.isReadable());
                        if (key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + " 上线");
                        }

                        if (key.isReadable()){
                            readData(key);
                        }

                        iterator.remove();
                    }
                }else {
                    System.out.println("等待...");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    public void readData(SelectionKey key){
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count > 0){
                String msg = new String(buffer.array()).trim();
                System.out.println("form客户端: " + msg);
                sendInfoToOtherClients(msg, channel);
            }
        }catch (IOException e){
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了...");
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException{
        System.out.println("服务器转发消息中...");
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self){
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
