package org.example.Netty.Game.common.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
        Packet packet = (Packet) msg;

        //客户端也要实现路由功能，这里接受到的数据暂时写死
        BindProto.GS2CBindPlayer data = BindProto.GS2CBindPlayer.parseFrom(packet.getBytes());
        System.out.println(data.getState());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        GClientBootstrap.getInstance().setChannel(ctx.channel());
    }
}
