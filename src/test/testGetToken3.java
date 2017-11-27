package test;

import java.util.Map;

import org.junit.Test;

import pojo.Token;

import util.CommonUtil;
import util.TokenUtil;

public class testGetToken3 {
	@Test
	public void testGetToken(){
	Map<String, Object> token=TokenUtil.getToken();
	System.out.println(token.get("access_token"));
	System.out.println(token.get("expires_in"));
	}
	
	@Test
	public void testSaveToken() {
		Token token=CommonUtil.getToken("appID", "appsecret");
		TokenUtil.saveToken(token);

	}
}

