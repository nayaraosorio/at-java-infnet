package com.bezkoder.spring.datajpa.model;

import jakarta.persistence.*;


@Entity
@Table(name = "funkosPop")
public class FunkoPop {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name="price")
	private Double price;

	@Column(name = "available")
	private boolean available;

	public FunkoPop() {

	}

	public FunkoPop(String title, String description, Double price, boolean available) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.available = available;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice(){
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean isAvailable) {
		this.available = isAvailable;
	}

	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", price=" + price + ", available=" + available + "]";
	}

}



// package com.bezkoder.spring.datajpa.model;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "tutorials")
// public class Tutorial {

// 	@Id
// 	@GeneratedValue(strategy = GenerationType.AUTO)
// 	private long id;

// 	@Column(name = "title")
// 	private String title;

// 	@Column(name = "description")
// 	private String description;

// 	@Column(name = "published")
// 	private boolean published;

// 	public Tutorial() {

// 	}

// 	public Tutorial(String title, String description, boolean published) {
// 		this.title = title;
// 		this.description = description;
// 		this.published = published;
// 	}

// 	public long getId() {
// 		return id;
// 	}

// 	public String getTitle() {
// 		return title;
// 	}

// 	public void setTitle(String title) {
// 		this.title = title;
// 	}

// 	public String getDescription() {
// 		return description;
// 	}

// 	public void setDescription(String description) {
// 		this.description = description;
// 	}

// 	public boolean isPublished() {
// 		return published;
// 	}

// 	public void setPublished(boolean isPublished) {
// 		this.published = isPublished;
// 	}

// 	@Override
// 	public String toString() {
// 		return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
// 	}

// }
