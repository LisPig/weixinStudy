package com.ego.ext.weixin.common.msg.handle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ego4qin@163.com
 */
public class MsgHandleListenerManager {

    protected static final List<MsgHandleListener> listeners = new ArrayList(3);
    // protected static final Map<String, List<MsgHandleListener>> _listeners = new HashMap<String, List<MsgHandleListener>>();

    public static void registListener(MsgHandleListener listener) {
        listeners.add(listener);
    }

    public static List<MsgHandleListener> getRegistedListeners() {
        return listeners;
    }
}
