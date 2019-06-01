package com.dzakdzaks.ta_umroh.admin.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAllJamaah{

	@SerializedName("result")
	private int result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("alljamaah")
	private List<AlljamaahItem> alljamaah;

	public void setResult(int result){
		this.result = result;
	}

	public int getResult(){
		return result;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setAlljamaah(List<AlljamaahItem> alljamaah){
		this.alljamaah = alljamaah;
	}

	public List<AlljamaahItem> getAlljamaah(){
		return alljamaah;
	}
}