package com.dzakdzaks.ta_umroh.home.response;

import com.google.gson.annotations.SerializedName;

public class TiketItem{
	@SerializedName("jarak_to_madinah")
	private String jarakToMadinah;

	@SerializedName("role")
	private String role;

	@SerializedName("keperluan")
	private String keperluan;

	@SerializedName("nama_lengkap")
	private String namaLengkap;

	@SerializedName("keberangkatan")
	private String keberangkatan;

	@SerializedName("jarak_to_mekah")
	private String jarakToMekah;

	@SerializedName("tempat_lahir")
	private String tempatLahir;

	@SerializedName("harga")
	private String harga;

	@SerializedName("no_ktp")
	private String noKtp;

	@SerializedName("id_tiket")
	private String idTiket;

	@SerializedName("id_paket")
	private String idPaket;

	@SerializedName("durasi")
	private String durasi;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	@SerializedName("email")
	private String email;

	@SerializedName("maskapai")
	private String maskapai;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("id_jamaah")
	private String idJamaah;

	@SerializedName("pekerjaan")
	private String pekerjaan;

	@SerializedName("nama")
	private String nama;

	@SerializedName("transit")
	private String transit;

	@SerializedName("nama_ibu_kandung")
	private String namaIbuKandung;

	@SerializedName("no_telpon")
	private String noTelpon;

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


	public String getKewarganegaraan() {
		return kewarganegaraan;
	}

	public void setKewarganegaraan(String kewarganegaraan) {
		this.kewarganegaraan = kewarganegaraan;
	}

	@SerializedName("kewarganegaraan")
	private String kewarganegaraan;

	public void setJarakToMadinah(String jarakToMadinah){
		this.jarakToMadinah = jarakToMadinah;
	}

	public String getJarakToMadinah(){
		return jarakToMadinah;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

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

	public void setKeberangkatan(String keberangkatan){
		this.keberangkatan = keberangkatan;
	}

	public String getKeberangkatan(){
		return keberangkatan;
	}

	public void setJarakToMekah(String jarakToMekah){
		this.jarakToMekah = jarakToMekah;
	}

	public String getJarakToMekah(){
		return jarakToMekah;
	}

	public void setTempatLahir(String tempatLahir){
		this.tempatLahir = tempatLahir;
	}

	public String getTempatLahir(){
		return tempatLahir;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setNoKtp(String noKtp){
		this.noKtp = noKtp;
	}

	public String getNoKtp(){
		return noKtp;
	}

	public void setIdTiket(String idTiket){
		this.idTiket = idTiket;
	}

	public String getIdTiket(){
		return idTiket;
	}

	public void setIdPaket(String idPaket){
		this.idPaket = idPaket;
	}

	public String getIdPaket(){
		return idPaket;
	}

	public void setDurasi(String durasi){
		this.durasi = durasi;
	}

	public String getDurasi(){
		return durasi;
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

	public void setMaskapai(String maskapai){
		this.maskapai = maskapai;
	}

	public String getMaskapai(){
		return maskapai;
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

	public void setIdJamaah(String idJamaah){
		this.idJamaah = idJamaah;
	}

	public String getIdJamaah(){
		return idJamaah;
	}

	public void setPekerjaan(String pekerjaan){
		this.pekerjaan = pekerjaan;
	}

	public String getPekerjaan(){
		return pekerjaan;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setTransit(String transit){
		this.transit = transit;
	}

	public String getTransit(){
		return transit;
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

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}