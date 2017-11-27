package pojo;
/**
 * 抽奖对象
 * @author lsp11
 *
 */
public class Member {
	private static final long serialVersionUID=-1529113041346298430L;
	//会员
	private int mid;
	//openId
	private String openId;
	//用户昵称
	private String nickname;
	//用户性别
	private int sex;
	//用户所在城市
	private String city;
	//用户所在国家
	private String country;
	//用户所在省份
	private String province;
	//用户头像
	private String headimgurl;
	//用户关注时间
	private String subscribeTime;
	//真实姓名
	private String realname;
	//手机号
	private String phone;
	//是否中奖
	private int isbid;
	private int willwin;
	
	
	
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getIsbid() {
		return isbid;
	}
	public void setIsbid(int isbid) {
		this.isbid = isbid;
	}
	public int getWillwin() {
		return willwin;
	}
	public void setWillwin(int willwin) {
		this.willwin = willwin;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
