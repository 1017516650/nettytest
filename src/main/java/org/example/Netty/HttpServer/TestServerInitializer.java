package org.example.Netty.HttpServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
        System.out.println("ok~~~~~");
    }
}
