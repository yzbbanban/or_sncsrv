package com.pl.pro.sncsrv.config.server.heartbeat;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pl.pro.sncsrv.config.server.heartbeat.ProtocolUtils.OK;

public abstract class HeartbeatHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatHandler.class);

//    @Override
//    protected void channelRead0(ChannelHandlerContext context, ByteBuf byteBuf) throws Exception {
//
//    }

    protected abstract void handleData(ChannelHandlerContext channelHandlerContext, ByteBuf buf);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:// 要处理读 by Alex
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.error("---" + ctx.channel().remoteAddress() + " is active---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.error("---" + ctx.channel().remoteAddress() + " is inactive---");
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        LOGGER.error("---READER_IDLE---");
        //心跳重新恢复
        try {
            replayHeartChannel(ctx);
            LOGGER.info("心跳恢复");
        } catch (Exception e) {
            LOGGER.error("心跳异常：" + ExceptionUtils.getStackTrace(e));
            removeChannel(ctx);
            ctx.close();
        }
//		removeChannel(ctx);
//		ctx.close();
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }

    protected abstract void removeChannel(ChannelHandlerContext ctx);

    protected abstract void replayHeartChannel(ChannelHandlerContext ctx);
}