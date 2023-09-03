package org.example.Netty.Game.server.netcmd.shop;

import org.example.Netty.Game.common.proto.out.shop.ShopProto;
import org.example.Netty.Game.common.protocol.NetHandler;
import org.example.Netty.Game.server.GPlayer;

/**
 * ClassName: C2GSOpenShop
 * Description:
 * date: 2023/9/2 0002 上午 10:48
 *
 * @author 丁宇
 * @since JDK 8
 */
public class C2GSOpenShop extends NetHandler {
    @Override
    public void request() throws Exception{
        ShopProto.C2GSOpenShop data = ShopProto.C2GSOpenShop.parseFrom(packet.getBytes());
        System.out.println("---------------C2GSOpenShop--------------" + data.getShopid());

        GPlayer player = packet.getPlayer();
        ShopProto.GS2COpenShop.Builder builder = ShopProto.GS2COpenShop.newBuilder();

        ShopProto.Shop.Builder shop = ShopProto.Shop.newBuilder();
        shop.setShopid(data.getShopid());
        shop.setData("商城");

        builder.setShopdata(shop.build());
        player.send("GS2COpenShop", builder.build());
    }
}
