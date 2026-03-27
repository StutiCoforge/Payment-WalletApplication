package com.coforge.daos;

import java.util.List;
import java.util.Optional;

import com.coforge.entities.BillPayment;

public interface BillPaymentDaoInterface {
	public List<BillPayment> getAllBillPayments();
	public Optional<BillPayment> getBillPaymentByBillId(long billId);
	public BillPayment saveBillPayment(BillPayment billPayment);
	public BillPayment updateBillPayment(BillPayment billPayment);
	public void deleteBillPayment(long billId);
}
