package org.example.Netty.Game.server.netcmd.bind;

import org.example.Netty.Game.common.proto.out.bind.BindProto;
import org.example.Netty.Game.server.GPlayer;
import org.example.Netty.Game.common.protocol.NetHandler;

/**
 * ClassName: C2GSBindPlayer
 * Description:
 * date: 2023/9/2 0002 下午 8:10
 *
 * @author 丁宇
 * @since JDK 8
 */
public class C2GSBindPlayer extends NetHandler {
    @Override
    public void request() throws Exception{

        BindProto.C2GSBindPlayer data = BindProto.C2GSBindPlayer.parseFrom(packet.getBytes());
        System.out.println("-------------C2GSBindPlayer-----------------" + data.getAccount());

        GPlayer player = new GPlayer();
        player.setSid(data.getAccount());
        player.bind(packet.getChannel());
        BindProto.GS2CBindPlayer.Builder builder = BindProto.GS2CBindPlayer.newBuilder();
        builder.setState(200);
        player.send("GS2CBindPlayer", builder.build());
    }
}
