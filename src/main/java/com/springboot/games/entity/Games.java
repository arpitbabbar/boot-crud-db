package com.springboot.games.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // entity
@Table //to create table in DB
public class Games {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name = "Name")
	private String name;
	@Column(name = "Platform")
	private String platform;
	@Column(name = "Rating")
	private int rating;
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Games() {
		super();
	}
	public Games(int id,String name, String platform, int rating) {
		super();
		this.id = id;
		this.name = name;
		this.platform = platform;
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Games [id=" + id + ", name=" + name + ", platform=" + platform + ", rating=" + rating + "]";
	}
	
	
	
	
	
}
