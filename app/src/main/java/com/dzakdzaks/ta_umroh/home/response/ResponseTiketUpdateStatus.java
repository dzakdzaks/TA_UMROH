package com.dzakdzaks.ta_umroh.home.response;

import com.google.gson.annotations.SerializedName;

public class ResponseTiketUpdateStatus{

	@SerializedName("result")
	private String result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("tiket")
	private Tiket tiket;

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

	public void setTiket(Tiket tiket){
		this.tiket = tiket;
	}

	public Tiket getTiket(){
		return tiket;
	}
}