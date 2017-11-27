package pojo;
/**
 * 奖项对象
 * @author lsp11
 *
 */
public class PrizeItem {
	private static final long serialVersionUID=2017112701L;
	
	//奖品项
	private int id;
	//奖品项名称（特等，一等，二等，三等）
	private String name;
	//每轮抽奖个数
	private int cyclebidcount;
	//第几轮（摇一摇轮数）
	private int roundno;
	//状态（未开始：0，进行中：1，结束：2）
	private int status;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCyclebidcount() {
		return cyclebidcount;
	}
	public void setCyclebidcount(int cyclebidcount) {
		this.cyclebidcount = cyclebidcount;
	}
	public int getRoundno() {
		return roundno;
	}
	public void setRoundno(int roundno) {
		this.roundno = roundno;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
