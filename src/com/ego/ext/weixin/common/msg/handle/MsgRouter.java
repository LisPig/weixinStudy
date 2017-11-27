package com.ego.ext.weixin.common.msg.handle;

import com.ego.ext.weixin.common.msg.Msg;

/**
 * <pre>
 * 微信消息路由器，通过代码化的配置，把来自微信的消息交给handler处理
 *
 * 说明：
 * 1. 配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
 * 2. 默认情况下消息只会被处理一次，除非使用 {@link Rule#next()} 3.
 * 规则的结束必须用{@link Rule#end()}或者{@link Rule#next()}，否则不会生效
 *
 * 使用方法： MessageRouter router = new MessageRouter(); router .rule()
 * .msgType("MSG_TYPE").event("EVENT").eventKey("EVENT_KEY").content("CONTENT")
 * .interceptor(interceptor, ...).handler(handler, ...) .end() .rule() //
 * 另外一个匹配规则 .end() ;
 *
 * // 将WxXmlMessage交给消息路由器 router.route(message);
 *
 * </pre>
 *@deprecated 
 * @author qianjia
 *
 */
public interface MsgRouter<T extends Msg> {

    /**
     * 开始一个新的Route规则
     *
     * @return
     */
    public Rule rule();

    /**
     * 处理微信消息
     *
     * @param fromMsg
     * @return
     */
    public T route(final T fromMsg);
}
