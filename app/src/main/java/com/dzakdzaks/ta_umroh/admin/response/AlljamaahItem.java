package com.dzakdzaks.ta_umroh.admin.response;

import com.google.gson.annotations.SerializedName;

public class AlljamaahItem{

	@SerializedName("keperluan")
	private String keperluan;

	@SerializedName("nama_lengkap")
	private String namaLengkap;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("kewarganegaraan")
	private String kewarganegaraan;

	@SerializedName("id_jamaah")
	private String idJamaah;

	@SerializedName("tempat_lahir")
	private String tempatLahir;

	@SerializedName("pekerjaan")
	private String pekerjaan;

	@SerializedName("no_ktp")
	private String noKtp;

	@SerializedName("nama_ibu_kandung")
	private String namaIbuKandung;

	@SerializedName("no_telpon")
	private String noTelpon;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	@SerializedName("email")
	private String email;

	public void setKeperluan(String keperluan){
		this.keperluan = keperluan;
	}

	public String getKeperluan(){
		return keperluan;
	}

	public void setNamaLengkap(String namaLengkap){
		this.namaLengkap = namaLengkap;
	}

	public String getNamaLengkap(){
		return namaLengkap;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setKewarganegaraan(String kewarganegaraan){
		this.kewarganegaraan = kewarganegaraan;
	}

	public String getKewarganegaraan(){
		return kewarganegaraan;
	}

	public void setIdJamaah(String idJamaah){
		this.idJamaah = idJamaah;
	}

	public String getIdJamaah(){
		return idJamaah;
	}

	public void setTempatLahir(String tempatLahir){
		this.tempatLahir = tempatLahir;
	}

	public String getTempatLahir(){
		return tempatLahir;
	}

	public void setPekerjaan(String pekerjaan){
		this.pekerjaan = pekerjaan;
	}

	public String getPekerjaan(){
		return pekerjaan;
	}

	public void setNoKtp(String noKtp){
		this.noKtp = noKtp;
	}

	public String getNoKtp(){
		return noKtp;
	}

	public void setNamaIbuKandung(String namaIbuKandung){
		this.namaIbuKandung = namaIbuKandung;
	}

	public String getNamaIbuKandung(){
		return namaIbuKandung;
	}

	public void setNoTelpon(String noTelpon){
		this.noTelpon = noTelpon;
	}

	public String getNoTelpon(){
		return noTelpon;
	}

	public void setJenisKelamin(String jenisKelamin){
		this.jenisKelamin = jenisKelamin;
	}

	public String getJenisKelamin(){
		return jenisKelamin;
	}

	public void setTanggalLahir(String tanggalLahir){
		this.tanggalLahir = tanggalLahir;
	}

	public String getTanggalLahir(){
		return tanggalLahir;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}