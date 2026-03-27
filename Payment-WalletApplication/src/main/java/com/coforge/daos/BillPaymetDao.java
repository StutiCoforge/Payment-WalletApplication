package com.coforge.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coforge.entities.BillPayment;
import com.coforge.repositories.BillPaymentRepository;

@Repository
public class BillPaymetDao implements BillPaymentDaoInterface {
	@Autowired
	BillPaymentRepository billPaymentRepository;

	@Override
	public List<BillPayment> getAllBillPayments() {
		return billPaymentRepository.findAll();
	}

	@Override
	public Optional<BillPayment> getBillPaymentByBillId(long billId) {
		return billPaymentRepository.findById(billId);
	}

	@Override
	public BillPayment saveBillPayment(BillPayment billPayment) {
		return billPaymentRepository.save(billPayment);
	}

	@Override
	public BillPayment updateBillPayment(BillPayment billPayment) {
		return billPaymentRepository.save(billPayment);
	}

	@Override
	public void deleteBillPayment(long billId) {
		billPaymentRepository.deleteById(billId);
	}

}
