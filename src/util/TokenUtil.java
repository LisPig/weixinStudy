package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import pojo.Token;

public class TokenUtil {
	/**
	 * 获取token
	 * @return
	 */
	public static Map<String, Object>getToken(){
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="select * from wx_token";
		String access_token="";
		Map<String, Object>map=new HashMap<String, Object>();
		Integer expires_in=0;
		try {
			//创建数据库链接
			con=DBUtility.getConnection();
			//创建处理器
			stmt=con.prepareStatement(sql);
			//查询token
			rs=stmt.executeQuery();
			if(rs.next()){
				access_token=rs.getString("access_token");
				expires_in=rs.getInt("expiress_in");
				map.put("access_token", access_token);
				map.put("expires_in", expires_in);
			}
		} catch (SQLException ex) {
			System.out.println("数据库操作异常："+ex.getMessage());
		}finally{
			DBUtility.closeConnection(con);
		}
		return map;
	}
	
	public static void saveToken(Token token){
		//存入数据库
		Connection conn=null;
		PreparedStatement pst=null;
		try {
			//创建数据库链接
			conn=DBUtility.getConnection();
			//创建预处理器
			pst=conn.prepareStatement("insert into wx_token(access_token,expires_in,createTime)values(?,?,?)");
			pst.setString(1, token.getAccessToken());
			pst.setInt(2, token.getExpiresIn());
			long now=new Date().getTime();
			pst.setTimestamp(3, new java.sql.Timestamp(now));
			pst.execute();
		} catch (SQLException ex) {
			System.out.println("数据库操作异常："+ex.getMessage());
		}finally{
			DBUtility.closeConnection(conn);
		}
	}

}
