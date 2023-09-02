package org.example.Netty.Game.common.protocol;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * ClassName: GClientBootstrap
 * Description:
 * date: 2023/9/2 0002 下午 11:25
 *
 * @author 丁宇
 * @since JDK 8
 */
public class GClientBootstrap {

    private Channel channel;

    private static GClientBootstrap instance;

    public static GClientBootstrap getInstance(){
        if (instance == null){
            instance = new GClientBootstrap();
        }
        return instance;
    }

    public GClientBootstrap() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder", new ProtoDecoder());
                        pipeline.addLast("clienthandler", new ClientHandler());
                        pipeline.addLast("encoder", new ProtoEncoder());
                    }
                });

        bootstrap.connect(new InetSocketAddress("127.0.0.1", 36699));

    }

    public void send(int cmd, GeneratedMessage msg) {
        if (channel == null || msg == null || !channel.isWritable()) {
            return;
        }
        ByteBuf byteBuf = ProtoManager.wrapBuffer(cmd, msg);
        channel.writeAndFlush(byteBuf);
    }

    public void setChannel(Channel ch){
        channel = ch;
    }

    public Channel getChannel(){
        return channel;
    }
}
