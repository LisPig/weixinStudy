package test;

import java.sql.SQLException;
import org.junit.Test;

import util.DBUtility;
/**
 * 测试数据库连接
 * @author lsp11
 *
 */
public class TestDBUtil {

	@Test
	public void testgetConnection()throws SQLException{
		DBUtility db=new DBUtility();
		System.out.println(db.getConnection());
	}
}
