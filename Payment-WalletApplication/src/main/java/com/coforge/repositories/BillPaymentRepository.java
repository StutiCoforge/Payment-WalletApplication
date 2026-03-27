package com.coforge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coforge.entities.BillPayment;
import com.coforge.entities.BillType;

public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {
	public List<BillPayment> findByBillType(BillType billtype);
}
