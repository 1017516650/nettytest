package org.example.Netty.Game.server.netcmd.item;

import org.example.Netty.Game.common.proto.out.item.ItemProto;
import org.example.Netty.Game.common.protocol.NetHandler;
import org.example.Netty.Game.server.GPlayer;

/**
 * ClassName: C2GSAddItem
 * Description:
 * date: 2023/9/1 0001 下午 10:39
 *
 * @author 丁宇
 * @since JDK 8
 */
public class C2GSAddItem extends NetHandler {
    @Override
    public void request() throws Exception{

        ItemProto.C2GSAddItem data = ItemProto.C2GSAddItem.parseFrom(packet.getBytes());
        System.out.println("---------------C2GSAddItem--------------" + data.getItemsid() + " " + data.getAmount());

        GPlayer player = packet.getPlayer();
        ItemProto.GS2CAddItem.Builder builder = ItemProto.GS2CAddItem.newBuilder();
        builder.setMsg("success");
        player.send("GS2CAddItem", builder.build());
    }
}
