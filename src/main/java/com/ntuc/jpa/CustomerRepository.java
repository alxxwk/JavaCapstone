package com.ntuc.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


// to enter table name and data type of primary key
public interface CustomerRepository extends JpaRepository<Customers, Integer>{
	
	@Query("SELECT c FROM Customers c WHERE c.custid = :custid")
	public Customers getCustByCustid(@Param("custid") Integer custid);
	
}
