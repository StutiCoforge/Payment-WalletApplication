package com.coforge.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.coforge.daos.BillPaymetDao;
import com.coforge.dtos.BillPaymentRequestDto;
import com.coforge.dtos.BillPaymentResponseDto;
import com.coforge.dtos.CustomerJWTTokenDto;
import com.coforge.dtos.ElectricityBillPaymentDto;
import com.coforge.dtos.GasCylinderBookingBillPaymentDto;
import com.coforge.dtos.MobileRechargeBillPaymentDto;
import com.coforge.entities.BillPayment;
import com.coforge.entities.BillType;
import com.coforge.entities.Customer;
import com.coforge.entities.Transaction;
import com.coforge.entities.Wallet;
import com.coforge.exception.InvalidBillPaymentDataException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BillPaymentService implements BillPaymentServiceInterface {
	@Autowired
	BillPaymetDao billPaymentDao;

	@Autowired
	WalletServiceImpl walletService;

	@Autowired
	CustomerService customerService;

	@Autowired
	TransactionService transactionService;
	
//	private long customerid = 88;

	@Override
	public List<BillPaymentResponseDto> getAllBillPayments() {
		return billPaymentDao.getAllBillPayments().stream().map((b)->createBillPaymentResponseDtoFromBillPayment(b)).collect(Collectors.toList());
	}

	@Override
	public BillPaymentResponseDto getBillPaymentByBillId(long billId) {
		BillPayment b = billPaymentDao.getBillPaymentByBillId(billId).orElseThrow();
		return createBillPaymentResponseDtoFromBillPayment(b);
	}

	@Override
	public BillPaymentResponseDto createBillPayment(BillPaymentRequestDto billPaymentRequestDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomerJWTTokenDto customerDto = (CustomerJWTTokenDto) auth.getPrincipal();
		Customer customer = customerService.getById(customerDto.getCustId());
		Wallet wallet = walletService.getWalletByCustomerId(customerDto.getCustId());
		
		BillPayment billPayment = new BillPayment();
		String description="Bill Payment "+billPaymentRequestDto.getBillType();
        Transaction trans= new Transaction("DEBIT",billPaymentRequestDto.getAmount(),customer,description);
        Transaction transaction = transactionService.addTransaction(trans);
		try {
			billPayment = convertBillPaymentRequestDtoToBillPayment(billPaymentRequestDto);
//			System.out.println(billPayment);
			walletService.debit(wallet.getWalletId(), BigDecimal.valueOf(billPaymentRequestDto.getAmount()));
			transaction.setTransactionStatus("SUCCESS");
            transactionService.updateTransaction(transaction);
            BillPayment b = billPaymentDao.saveBillPayment(billPayment);
            
			return createBillPaymentResponseDtoFromBillPayment(b);
		}
		catch(Exception e) {
			System.out.println(e);
			transaction.setTransactionStatus("FAILED");
            transactionService.updateTransaction(transaction);
			throw new InvalidBillPaymentDataException("Invalid bill data");
		}
	}
	
	public BillPayment convertBillPaymentRequestDtoToBillPayment(BillPaymentRequestDto billPaymentRequestDto) {
		ObjectMapper objectMapper = new ObjectMapper();
		BillPayment billPayment = new BillPayment();
		billPayment.setAmount(billPaymentRequestDto.getAmount());
		billPayment.setBillType(billPaymentRequestDto.getBillType());
//		Wallet wallet = walletService.getWalletById(billPaymentRequestDto.getWallet_id());
//		Wallet wallet = walletService.getWalletByCustomerId(customerid);
//		billPayment.setWallet(wallet);
		if(billPaymentRequestDto.getBillType().equals(BillType.MOBILE_RECHARGE)) {
			MobileRechargeBillPaymentDto mobileDto = objectMapper.convertValue(billPaymentRequestDto.getBillData(),MobileRechargeBillPaymentDto.class);
			System.out.println(mobileDto);
		}
		else if(billPaymentRequestDto.getBillType().equals(BillType.GAS_BOOKING)) {
			GasCylinderBookingBillPaymentDto gasBookingDto = objectMapper.convertValue(billPaymentRequestDto.getBillData(),GasCylinderBookingBillPaymentDto.class);
			System.out.println(gasBookingDto);
		}
		else if(billPaymentRequestDto.getBillType().equals(BillType.ELECTRICITY)) {
			ElectricityBillPaymentDto electicityBilDto = objectMapper.convertValue(billPaymentRequestDto.getBillData(),ElectricityBillPaymentDto.class);
			System.out.println(electicityBilDto);
		}
		billPayment.setBillData(billPaymentRequestDto.getBillData());
		return billPayment;
	}
	
	
	@Override
	public BillPaymentResponseDto updateBillPayment(BillPayment billPayment) {
		getBillPaymentByBillId(billPayment.getBillId());
		BillPayment b = billPaymentDao.saveBillPayment(billPayment);
		return createBillPaymentResponseDtoFromBillPayment(b);
	}

	@Override
	public void deleteBillPayment(long billId) {
		getBillPaymentByBillId(billId);
		billPaymentDao.deleteBillPayment(billId);
	}
	
	public BillPaymentResponseDto createBillPaymentResponseDtoFromBillPayment(BillPayment billPayment) {
		return new BillPaymentResponseDto(billPayment.getBillId(),billPayment.getPaymentDate(),billPayment.getAmount(),billPayment.getBillType(),billPayment.getBillData());
	}
}
