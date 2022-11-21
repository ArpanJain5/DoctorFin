package model;

public class DoctorModel {
	private int id,contact;
	private String image,name,address,speciality,experince,email,password;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContact() {
		return contact;
	}

	public void setContact(int contact) {
		this.contact = contact;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getExperince() {
		return experince;
	}

	public void setExperince(String experince) {
		this.experince = experince;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "DoctorModel [id=" + id + ", contact=" + contact + ", image=" + image + ", name=" + name + ", address="
				+ address + ", speciality=" + speciality + ", experince=" + experince + ", email=" + email
				+ ", password=" + password + "]";
	}
}
