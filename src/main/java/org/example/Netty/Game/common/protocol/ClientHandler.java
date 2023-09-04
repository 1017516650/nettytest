package org.example.Netty.Game.common.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.Netty.Game.client.GClient;
import org.example.Netty.Game.common.proto.out.bind.BindProto;

/**
 * ClassName: ClientHandler
 * Description:
 * date: 2023/9/2 0002 下午 11:31
 *
 * @author 丁宇
 * @since JDK 8
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof  Packet){
            Packet packet = (Packet) msg;
            GClient.Read(packet);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        GClientBootstrap.getInstance().setChannel(ctx.channel());
    }
}
