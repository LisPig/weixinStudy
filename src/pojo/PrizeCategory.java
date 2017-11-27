package pojo;
/**
 * 奖项物品种类
 * @author lsp11
 *
 */
public class PrizeCategory {
	private static final long seriaVersionUID=2017222701L;
	
	//奖项物品种类id
	private int id;
	//奖项物品种类名称
	private String name;
	//关联奖项Id
	private int prizeItemId;
	//关联奖项名称
	private String prizeItemName;
	//物品总数量
	private int totalNum;
	//可用物品数量
	private int effecNum;
	
	
	
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
	public int getPrizeItemId() {
		return prizeItemId;
	}
	public void setPrizeItemId(int prizeItemId) {
		this.prizeItemId = prizeItemId;
	}
	public String getPrizeItemName() {
		return prizeItemName;
	}
	public void setPrizeItemName(String prizeItemName) {
		this.prizeItemName = prizeItemName;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getEffecNum() {
		return effecNum;
	}
	public void setEffecNum(int effecNum) {
		this.effecNum = effecNum;
	}
	public static long getSeriaversionuid() {
		return seriaVersionUID;
	}
	
	

}
