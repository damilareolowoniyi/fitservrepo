package com.fitserv.user.profilemenu;

//Trainers Details class 
public class TrainersDetailsPojo {

	private String rating;
	private String location;
	private String details;
	private String reviews;
	private String qualification;
	private String fullname;
	private String email;
	private String username;

	public TrainersDetailsPojo(String rating, String location, String details,
			String reviews, String qualifcation, String fullname, String email,
			String username) {
		this.rating = rating;
		this.location = location;
		this.details = details;
		this.reviews = reviews;
		this.qualification = qualifcation;
		this.fullname = fullname;
		this.email = email;
		this.username = username;

	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getReviews() {
		return reviews;
	}

	public void setReviews(String reviews) {
		this.reviews = reviews;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
