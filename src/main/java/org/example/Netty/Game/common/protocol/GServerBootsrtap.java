package org.example.Netty.Game.common.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;

/**
 * ClassName: GServerBootsrtap
 * Description:
 * date: 2023/9/1 0001 下午 9:37
 *
 * @author 丁宇
 * @since JDK 8
 */
public class GServerBootsrtap {
    public GServerBootsrtap() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(new DefaultThreadFactory("bossGroup"));
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("workerGroup"));
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new ProtoDecoder());
                        pipeline.addLast(new DefaultEventLoopGroup(4), new ServerHandler());
                        pipeline.addLast("encoder", new ProtoEncoder());
                    }
                });

        try {
            serverBootstrap.bind(new InetSocketAddress("127.0.0.1", 36699)).sync();
        } catch (InterruptedException e) {
            System.err.println("bind err");
            try {
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
