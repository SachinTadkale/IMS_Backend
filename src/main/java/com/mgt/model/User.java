package com.mgt.model;

import jakarta.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "name")
	private String full_name;

	@Column(name = "store_type")
	private String store_type;

	@Column(name = "email" )
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "Role")
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "Status")
	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDING;

	@Column(name = "isPaid")
	private boolean paymentStatus;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Product> products;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Customer> customers;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Seller> sellers;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Review> reviews;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Transaction transactions;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private UserProfile userProfile;

	public User() {
	}

	
	

	public User(String full_name, String email, String password, Role role, Status status) {
		this.full_name = full_name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.status = status;
	}




	public User(Long id, String full_name, String store_type, String email, String password, Role role, Status status,
			List<Product> products, List<Customer> customers, List<Seller> sellers, List<Review> reviews,
			Transaction transactions) {
		this.id = id;
		this.full_name = full_name;
		this.store_type = store_type;
		this.email = email;
		this.password = password;
		this.role = role;
		this.status = status;
		this.products = products;
		this.customers = customers;
		this.sellers = sellers;
		this.reviews = reviews;
		this.transactions = transactions;
	}

	




	public User(Long id, String full_name, String store_type, String email, String password, Role role, Status status,
			List<Product> products, List<Customer> customers, List<Seller> sellers, List<Review> reviews,
			Transaction transactions, UserProfile userProfile) {
		this.id = id;
		this.full_name = full_name;
		this.store_type = store_type;
		this.email = email;
		this.password = password;
		this.role = role;
		this.status = status;
		this.products = products;
		this.customers = customers;
		this.sellers = sellers;
		this.reviews = reviews;
		this.transactions = transactions;
		this.userProfile = userProfile;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getFull_name() {
		return full_name;
	}




	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}




	public String getStore_type() {
		return store_type;
	}




	public void setStore_type(String store_type) {
		this.store_type = store_type;
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




	public Role getRole() {
		return role;
	}




	public void setRole(Role role) {
		this.role = role;
	}




	public Status getStatus() {
		return status;
	}




	public void setStatus(Status status) {
		this.status = status;
	}




	public List<Product> getProducts() {
		return products;
	}




	public void setProducts(List<Product> products) {
		this.products = products;
	}




	public List<Customer> getCustomers() {
		return customers;
	}




	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}




	public List<Seller> getSellers() {
		return sellers;
	}




	public void setSellers(List<Seller> sellers) {
		this.sellers = sellers;
	}




	public List<Review> getReviews() {
		return reviews;
	}




	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}




	public Transaction getTransactions() {
		return transactions;
	}




	public void setTransactions(Transaction transactions) {
		this.transactions = transactions;
	}




	public UserProfile getUserProfile() {
		return userProfile;
	}




	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}




	public boolean isPaymentStatus() {
		return paymentStatus;
	}




	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	

	
	
}
