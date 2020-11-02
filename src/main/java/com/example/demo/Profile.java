package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

@Entity // This tells Hibernate to make a table out of this class
public class Profile  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
    private String password;
    
    @Column
    private String bio;

    @Column 
    private String imgurl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return username;
	}

	public void setName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
    }
    
    public String getBio() {
		return username;
	}

	public void setBio(String bio) {
		this.bio = bio;
    }
    
    public String getImageURL() {
		return imgurl;
	}

	public void setImageURL(String imgurl) {
		this.imgurl = imgurl;
	}

}
