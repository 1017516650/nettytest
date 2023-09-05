package org.example.Netty.Game.common.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.Netty.Game.server.GGame;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//        System.out.println("------------------channelRead 1------------" + formatter.format(new Date(System.currentTimeMillis())));
//        TimeUnit.SECONDS.sleep(1);
//        System.out.println("------------------channelRead 2------------" + formatter.format(new Date(System.currentTimeMillis())));
        Packet packet = (Packet) msg;
        GGame.getInstance().AddPacket(packet);
    }
}
