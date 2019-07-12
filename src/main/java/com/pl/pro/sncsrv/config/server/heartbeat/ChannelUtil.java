package com.pl.pro.sncsrv.config.server.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class ChannelUtil {

	public static byte[] writeMsgSync(byte[] msg, Channel channel, int timeout) throws Exception {
		if (channel.isActive()) {
			synchronized (channel) {
				Attribute<byte[]> attr = channel.attr(AttributeKey.valueOf(channel.id().asLongText()));
				synchronized (attr) {
					attr.set(msg);
					System.err.println("发消息:" + msg);
					ByteBuf buf = channel.alloc().buffer();
					buf.writeBytes(msg);
					channel.writeAndFlush(buf);
					attr.wait(timeout * 1000);
					byte[] ret = attr.get();
					System.err.println("收消息:" + ret + ret.getClass());
					attr.set(null);
					if (ret != msg) {
						return ret;
					} else {
						return null;
					}
				}
			}
		}
		return null;
	}
}