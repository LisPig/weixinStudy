package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.ext.weixin.WeiXinConfigHelper;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.context.WxConfigHolder;
import com.ego.ext.weixin.common.model.WxConfig;
import com.ego.ext.weixin.common.msg.handle.DefaultMsgHandler;
import com.ego.ext.weixin.common.msg.handle.MsgHandleListener;

import friends.util.log.Log;

import util.CheckUtil;

public class WeixinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Log log = new Log(getClass());
    private HashMap<String, WxConfig> cfgMap = new HashMap<String, WxConfig>();
    private HashMap<String, List<MsgHandleListener>> listenersMap = new HashMap<String, List<MsgHandleListener>>();
    private WxConfig currCfg = null;
    private List<MsgHandleListener> currListeners = null;

	/**
	 * 微信服务器验证
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String signature=req.getParameter("signature");
		String timestamp=req.getParameter("timestamp");
		String nonce=req.getParameter("nonce");
		String echostr=req.getParameter("echostr");
		
		PrintWriter out=resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	super.doPost(req, resp);
    }
}
