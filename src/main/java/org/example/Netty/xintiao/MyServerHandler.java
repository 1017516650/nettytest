package org.example.Netty.xintiao;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        try {
            if (evt instanceof IdleStateHandler){
                IdleStateEvent event = (IdleStateEvent) evt;
                String eventType = null;
                switch (event.state()){
                    case READER_IDLE:
                        eventType = "读空闲";
                        break;
                    case WRITER_IDLE:
                        eventType = "写空闲";
                        break;
                    case ALL_IDLE:
                        eventType = "读写空闲";
                        break;
                }

                System.out.println(ctx.channel().remoteAddress() + "--------------超时时间--------------" + eventType);
                System.out.println("服务器做相应处理...");

                ctx.channel().close();
            }
        }catch (Exception e){
            System.out.println("11111111111111111111");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        System.out.println("----------------exceptionCaught--------------------");
        ctx.close();
        throw new RuntimeException(cause);
    }
}
