package com.ego.ext.weixin.common.msg.handle;

import com.ego.ext.weixin.common.msg.Msg;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class DefaultMsgRouter<F extends Msg, T extends Msg, R extends Rule> implements MsgRouter<T> {

    private static final int DEFAULT_THREAD_POOL_SIZE = 20;
    private final List<Rule> rules = new ArrayList();
    private final ExecutorService executorService;
    private final Callback callback;

    public DefaultMsgRouter(Callback callback) {
        this.callback = callback;
        this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    }

    public DefaultMsgRouter(Callback callback, int threadPoolSize) {
        this.callback = callback;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * 开始一个新的Route规则
     *
     * @return
     */
    @Override
    public Rule rule() {
        return new Rule();
    }

    /**
     * 处理微信消息
     *
     * @param fromMsg
     */
    public T route(final T fromMsg) {
        final List<Rule> matchRules = new ArrayList();
        // 收集匹配的规则
        for (final Rule rule : rules) {
            if (rule.test(fromMsg)) {
                matchRules.add(rule);
                //if (!rule.reEnter) {
                // break;
                // }
            }
        }

        if (matchRules.isEmpty()) {
            return null;
        }

        T res = null;
        for (final Rule rule : matchRules) {
            // 返回最后一个非异步的rule的执行结果
            // if (rule.async) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    //  rule.service(fromMsg);
                }
            });
            //  } else {
            // res = rule.service(fromMsg);
            // }
        }
        return res;
    }
}
