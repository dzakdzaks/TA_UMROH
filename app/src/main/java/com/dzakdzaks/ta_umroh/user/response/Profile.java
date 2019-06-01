package com.dzakdzaks.ta_umroh.user.response;

import com.google.gson.annotations.SerializedName;

public class Profile{

	@SerializedName("goldar")
	private String goldar;

	@SerializedName("notelp")
	private String notelp;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("ttl")
	private String ttl;

	@SerializedName("email")
	private String email;

	@SerializedName("alamat")
	private String alamat;

	public void setGoldar(String goldar){
		this.goldar = goldar;
	}

	public String getGoldar(){
		return goldar;
	}

	public void setNotelp(String notelp){
		this.notelp = notelp;
	}

	public String getNotelp(){
		return notelp;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTtl(String ttl){
		this.ttl = ttl;
	}

	public String getTtl(){
		return ttl;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"Profile{" + 
			"goldar = '" + goldar + '\'' + 
			",notelp = '" + notelp + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",ttl = '" + ttl + '\'' + 
			",email = '" + email + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}