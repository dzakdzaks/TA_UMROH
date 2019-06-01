package com.dzakdzaks.ta_umroh.home.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePaket{

	@SerializedName("result")
	private int result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("paket")
	private List<PaketItem> paket;

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

	public void setPaket(List<PaketItem> paket){
		this.paket = paket;
	}

	public List<PaketItem> getPaket(){
		return paket;
	}
}