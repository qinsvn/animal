package com.label.common.model.custom.label;

/**
 * 联盟用户信息
 * @author jolley
 */
public class UserInfo {

	private String labelOpenId;
	
	private String nickname;
	
	private int sex;
	
	private String headImgUrl;
	
	private String userToken;
	
	private String email;
	
	private String birthday;
	
	private String phone;
	
	private String createTime;

	public String getCiticOpenId() {
		return labelOpenId;
	}

	public void setCiticOpenId(String labelOpenId) {
		this.labelOpenId = labelOpenId;
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

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
