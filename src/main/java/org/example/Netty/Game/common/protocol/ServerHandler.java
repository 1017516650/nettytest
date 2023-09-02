package org.example.Netty.Game.common.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.Netty.Game.server.GGame;

/**
 * ClassName: ServerHandler
 * Description:
 * date: 2023/9/1 0001 下午 9:47
 *
 * @author 丁宇
 * @since JDK 8
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet) msg;
        GGame.getInstance().AddPacket(packet);
    }
}
