package com.dzakdzaks.ta_umroh.user.response;

import com.google.gson.annotations.SerializedName;

public class ResponseProfile{

	@SerializedName("result")
	private String result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("profile")
	private Profile profile;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setProfile(Profile profile){
		this.profile = profile;
	}

	public Profile getProfile(){
		return profile;
	}

	@Override
 	public String toString(){
		return 
			"ResponseProfile{" + 
			"result = '" + result + '\'' + 
			",msg = '" + msg + '\'' + 
			",profile = '" + profile + '\'' + 
			"}";
		}
}