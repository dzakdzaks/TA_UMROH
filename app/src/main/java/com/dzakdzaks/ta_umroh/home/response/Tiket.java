package com.dzakdzaks.ta_umroh.home.response;

import com.google.gson.annotations.SerializedName;

public class Tiket{

	@SerializedName("id")
	private String id;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("id_paket")
	private String idPaket;

	@SerializedName("status")
	private String status;

	@SerializedName("bukti")
	private String bukti;

	@SerializedName("keterangan_bukti")
	private String keteranganBukti;



	public String getBukti() {
		return bukti;
	}

	public void setBukti(String bukti) {
		this.bukti = bukti;
	}

	public String getKeteranganBukti() {
		return keteranganBukti;
	}

	public void setKeteranganBukti(String keteranganBukti) {
		this.keteranganBukti = keteranganBukti;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setIdPaket(String idPaket){
		this.idPaket = idPaket;
	}

	public String getIdPaket(){
		return idPaket;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}