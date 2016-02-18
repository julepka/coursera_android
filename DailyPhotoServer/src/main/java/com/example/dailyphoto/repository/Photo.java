package com.example.dailyphoto.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;

@Entity
public class Photo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String title;
	private String url;
	private String owner;
	
	public Photo() {
	}

	public Photo(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(title, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Photo) {
			Photo other = (Photo) obj;
			return Objects.equal(title, other.title)
					&& Objects.equal(url, other.url);
		} else {
			return false;
		}
	}

}
