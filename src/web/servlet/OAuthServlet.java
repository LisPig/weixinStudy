package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pojo.SNSUserInfo;
import pojo.WeixinOauth2Token;
import util.AdvancedUtil;

public class OAuthServlet extends HttpServlet {

	private static final long serialVersionUID=-1847238807216447030L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		//用户同意授权后，获取到code
		String code=request.getParameter("code");
		String state=request.getParameter("state");
		//用户同意授权
		if(!"authdeny".equals(code)){
			//获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token=AdvancedUtil.getOauth2AccessToken("wx53777eaf2019be5f", "07b03dfb59ad277b85b305f8dba2aaaf", code);
			//网页授权接口访问凭证
			String accessToken=weixinOauth2Token.getAccessToken();
			//用户标识
			String openId=weixinOauth2Token.getOpenId();
			//获取用户信息
			SNSUserInfo snsUserInfo=AdvancedUtil.getSNSUserInfo(accessToken, openId);
			// 设置要传递的参数
			request.setAttribute("snsUserInfo", snsUserInfo);
			request.setAttribute("state", state);
		}
		//跳转到index.jsp
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
