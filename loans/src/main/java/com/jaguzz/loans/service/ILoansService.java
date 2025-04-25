package com.jaguzz.loans.service;

import com.jaguzz.loans.dto.LoansDto;

public interface ILoansService {

    void createLoan(LoansDto loansDto);

    LoansDto fetchLoan(String mobileNumber);

    // boolean updateLoan(LoanDto loansDto);

    // boolean deleteLoan(LoanDto loansDto);

}
