package com.coforge.services;

import java.util.List;

import com.coforge.dtos.BillPaymentRequestDto;
import com.coforge.dtos.BillPaymentResponseDto;
import com.coforge.entities.BillPayment;

public interface BillPaymentServiceInterface {
	public List<BillPaymentResponseDto> getAllBillPayments();
	public BillPaymentResponseDto getBillPaymentByBillId(long billId);
	public BillPaymentResponseDto createBillPayment(BillPaymentRequestDto billPaymentRequestDto);
	public BillPaymentResponseDto updateBillPayment(BillPayment billPayment);
	public void deleteBillPayment(long billId);
}
