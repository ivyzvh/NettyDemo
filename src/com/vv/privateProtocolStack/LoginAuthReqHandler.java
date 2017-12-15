package com.vv.privateProtocolStack;
/**
 * @author Lilinfeng
 * @version 1.0
 * @date 2014��3��15��
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(LoginAuthReqHandler.class);

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward to the
     * next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p/>
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to
     * the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p/>
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;

        // ���������Ӧ����Ϣ����Ҫ�ж��Ƿ���֤�ɹ�
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.LOGIN_RESP
                .value()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0) {
                // ����ʧ�ܣ��ر�����
                ctx.close();
            } else {
                LOG.info("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else
            //������һ��channel��..
            ctx.fireChannelRead(msg);
    }

    /**
     * ������¼����
     */
    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}