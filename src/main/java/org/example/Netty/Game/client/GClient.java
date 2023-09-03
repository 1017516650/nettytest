package org.example.Netty.Game.client;

import com.google.protobuf.InvalidProtocolBufferException;
import org.example.Netty.Game.common.proto.out.bind.BindProto;
import org.example.Netty.Game.common.proto.out.item.ItemProto;
import org.example.Netty.Game.common.proto.out.shop.ShopProto;
import org.example.Netty.Game.common.protocol.GClientBootstrap;
import org.example.Netty.Game.common.protocol.Packet;

/**
 * ClassName: GClient
 * Description:
 * date: 2023/9/2 0002 下午 11:24
 *
 * @author 丁宇
 * @since JDK 8
 */
public class GClient {
    public static void main(String[] args) {
        bind();
        add();
        del();
        openShop();
    }

    public static void bind(){
        GClientBootstrap instance = GClientBootstrap.getInstance();
        BindProto.C2GSBindPlayer.Builder builder = BindProto.C2GSBindPlayer.newBuilder();
        builder.setAccount("222222");
        instance.send(1000001, builder.build());
    }

    public static void add(){
        GClientBootstrap instance = GClientBootstrap.getInstance();
        ItemProto.C2GSAddItem.Builder builder = ItemProto.C2GSAddItem.newBuilder();
        builder.setItemsid("100001");
        builder.setAmount(100);
        instance.send(1001001, builder.build());
    }

    public static void del(){
        GClientBootstrap instance = GClientBootstrap.getInstance();
        ItemProto.C2GSDelItem.Builder builder = ItemProto.C2GSDelItem.newBuilder();
        builder.setItemsid("100001");
        instance.send(1001002, builder.build());
    }

    public static void openShop(){
        GClientBootstrap instance = GClientBootstrap.getInstance();
        ShopProto.C2GSOpenShop.Builder builder = ShopProto.C2GSOpenShop.newBuilder();
        builder.setShopid(1001);
        instance.send(1002001, builder.build());
    }

    public static void Read(Packet packet){
        try {
            switch (packet.getCmd()){
                case 2000001:
                    BindProto.GS2CBindPlayer bind = BindProto.GS2CBindPlayer.parseFrom(packet.getBytes());
                    System.out.println("-------------GS2CBindPlayer-----------" + bind.getState());
                    break;
                case 2001001:
                    ItemProto.GS2CAddItem itemAdd = ItemProto.GS2CAddItem.parseFrom(packet.getBytes());
                    System.out.println("-----------GS2CAddItem-------------" + itemAdd.getMsg());
                    break;
                case 2001002:
                    ItemProto.GS2CDelItem itemDel = ItemProto.GS2CDelItem.parseFrom(packet.getBytes());
                    System.out.println("------------GS2CDelItem------------" + itemDel.getMsg());
                    break;
                case 2002001:
                    ShopProto.GS2COpenShop shop = ShopProto.GS2COpenShop.parseFrom(packet.getBytes());
                    System.out.println("----------------GS2COpenShop------------------" + shop.getShopdata());
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
