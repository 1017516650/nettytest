package org.example.Netty.Game.server.netcmd.item;

import org.example.Netty.Game.common.proto.out.item.ItemProto;
import org.example.Netty.Game.common.protocol.NetHandler;
import org.example.Netty.Game.server.GPlayer;

/**
 * ClassName: C2GSDelItem
 * Description:
 * date: 2023/9/1 0001 下午 10:39
 *
 * @author 丁宇
 * @since JDK 8
 */
public class C2GSDelItem extends NetHandler {
    @Override
    public void request() throws Exception{
        ItemProto.C2GSDelItem data = ItemProto.C2GSDelItem.parseFrom(packet.getBytes());
        System.out.println("---------------C2GSDelItem--------------" + data.getItemsid());

        GPlayer player = packet.getPlayer();
        ItemProto.GS2CDelItem.Builder builder = ItemProto.GS2CDelItem.newBuilder();
        builder.setMsg("del success");
        player.send("GS2CDelItem", builder.build());
    }
}
