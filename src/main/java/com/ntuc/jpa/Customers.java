package com.ntuc.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Customers {

	@Id
	@Column(name = "cust_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer custid;
	@Size(min = 3, max = 50)
	private String custname;
	
	@Size(min = 3, max = 25)
	private String location;

	@Size(min = 0)
	private Double balance;

	public Customers() {
		super();
	}

	public Customers(Integer custid, @Size(min = 3, max = 50) String custname, @Size(min = 3, max = 25) String location,
			Double balance) {
		super();
		this.custid = custid;
		this.custname = custname;
		this.location = location;
		this.balance = balance;
	}

	

	public Customers(@Size(min = 3, max = 50) String custname, @Size(min = 3, max = 25) String location,
			@Size(min = 0) Double balance) {
		super();
		this.custname = custname;
		this.location = location;
		this.balance = balance;
	}

	public Integer getCustid() {
		return custid;
	}

	public void setCustid(Integer custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Customers [custid=" + custid + ", custname=" + custname + ", location=" + location + ", balance="
				+ balance + "]";
	}
	
}
