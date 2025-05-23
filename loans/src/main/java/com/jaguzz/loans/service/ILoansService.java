package com.jaguzz.loans.service;

import com.jaguzz.loans.dto.LoansDto;

public interface ILoansService {

    void createLoan(LoansDto loansDto);

    LoansDto fetchLoan(String mobileNumber);

    LoansDto updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);

}
