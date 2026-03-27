package com.coforge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MobileRechargeBillPaymentDto {
	private String mobileNumber;
	private MobileRechargeBillPaymentOperator operator;
}
