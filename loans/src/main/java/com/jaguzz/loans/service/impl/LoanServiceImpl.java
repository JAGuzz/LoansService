package com.jaguzz.loans.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.jaguzz.loans.constants.LoansConstants;
import com.jaguzz.loans.dto.LoansDto;
import com.jaguzz.loans.entity.Loans;
import com.jaguzz.loans.exception.LoanAlreadyExistException;
import com.jaguzz.loans.exception.ResourceNotFoundException;
import com.jaguzz.loans.mapper.LoansMapper;
import com.jaguzz.loans.repository.LoansRepository;
import com.jaguzz.loans.service.ILoansService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LoanServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        
        if(validateLoan(mobileNumber)){
            throw new LoanAlreadyExistException("Loan already registered with the given mobile number.");
        }
        
        loansRepository.save(createNewLoan(mobileNumber));
    }

     private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        LoansDto loanDto = findLoanByMobileNumber(mobileNumber);
        return loanDto;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));

        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);

        return true;
    }
    

    @Override
    public boolean deleteLoan(String mobileNumber) {
        LoansDto loansDto = findLoanByMobileNumber(mobileNumber);
        Loans loan = LoansMapper.mapToLoans(loansDto, new Loans());
        loansRepository.delete(loan);
        return !validateLoan(loansDto.getMobileNumber());
    }
    
    
        public boolean validateLoan(String mobileNumber){
            Optional<Loans> optionalLoan = loansRepository.findByMobileNumber(mobileNumber);
            return optionalLoan.isPresent();
        }

        public LoansDto findLoanByMobileNumber(String mobileNumber){
            Loans loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
            );
            return LoansMapper.mapToLoanDto(loan, new LoansDto());
        }
    
}
