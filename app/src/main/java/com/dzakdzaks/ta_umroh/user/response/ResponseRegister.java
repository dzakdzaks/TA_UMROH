package com.dzakdzaks.ta_umroh.user.response;

import com.google.gson.annotations.SerializedName;

public class ResponseRegister{

	@SerializedName("msg")
	private String msg;

	@SerializedName("error")
	private String error;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}
}