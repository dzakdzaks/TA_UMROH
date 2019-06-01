package com.dzakdzaks.ta_umroh.home.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTiket{

	@SerializedName("result")
	private int result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("tiket")
	private List<TiketItem> jadwal;

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

	public void setTiket(List<TiketItem> jadwal){
		this.jadwal = jadwal;
	}

	public List<TiketItem> getTiket(){
		return jadwal;
	}
}