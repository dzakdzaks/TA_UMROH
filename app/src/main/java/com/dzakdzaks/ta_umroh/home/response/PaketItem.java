package com.dzakdzaks.ta_umroh.home.response;

import com.google.gson.annotations.SerializedName;

public class PaketItem{

	@SerializedName("jarak_to_madinah")
	private String jarakToMadinah;

	@SerializedName("nama")
	private String nama;

	@SerializedName("harga")
	private String harga;

	@SerializedName("transit")
	private String transit;

	@SerializedName("maskapai")
	private String maskapai;

	@SerializedName("id")
	private String id;

	@SerializedName("keberangkatan")
	private String keberangkatan;

	@SerializedName("durasi")
	private String durasi;

	@SerializedName("jarak_to_mekah")
	private String jarakToMekah;

	public void setJarakToMadinah(String jarakToMadinah){
		this.jarakToMadinah = jarakToMadinah;
	}

	public String getJarakToMadinah(){
		return jarakToMadinah;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setTransit(String transit){
		this.transit = transit;
	}

	public String getTransit(){
		return transit;
	}

	public void setMaskapai(String maskapai){
		this.maskapai = maskapai;
	}

	public String getMaskapai(){
		return maskapai;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setKeberangkatan(String keberangkatan){
		this.keberangkatan = keberangkatan;
	}

	public String getKeberangkatan(){
		return keberangkatan;
	}

	public void setDurasi(String durasi){
		this.durasi = durasi;
	}

	public String getDurasi(){
		return durasi;
	}

	public void setJarakToMekah(String jarakToMekah){
		this.jarakToMekah = jarakToMekah;
	}

	public String getJarakToMekah(){
		return jarakToMekah;
	}
}