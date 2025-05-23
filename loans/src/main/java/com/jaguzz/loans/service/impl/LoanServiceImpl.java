package com.jaguzz.loans.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

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
    public void createLoan(LoansDto loansDto) {
        Loans loan = LoansMapper.mapToLoans(loansDto, new Loans());
        
        if(validateLoan(loansDto)){
            throw new LoanAlreadyExistException("Loan already registered with the given mobile number.");
        }
        
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loan.setLoanNumber(Long.toString(randomLoanNumber));
        loan.setCreatedAt(LocalDateTime.now());
        loan.setCreatedBy("Loans_MS");
        loansRepository.save(loan);
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        LoansDto loanDto = findLoanByMobileNumber(mobileNumber);
        return loanDto;
    }

    @Override
    public LoansDto updateLoan(LoansDto loansDto) {
        
        if(!validateLoan(loansDto)){
            throw new ResourceNotFoundException("Loan", "mobileNumber", loansDto.getMobileNumber());
        }

        Loans loan = LoansMapper.mapToLoans(loansDto, new Loans());
        loan.setUpdatedBy("Loans_MS");
        loan.setUpdatedAt(LocalDateTime.now());
        loansRepository.save(loan);

        return loansDto;
    }
    

    @Override
    public boolean deleteLoan(String mobileNumber) {
        LoansDto loansDto = findLoanByMobileNumber(mobileNumber);
        Loans loan = LoansMapper.mapToLoans(loansDto, new Loans());
        loansRepository.delete(loan);
        return !validateLoan(loansDto);
    }
    
    
        public boolean validateLoan(LoansDto loansDto){
            Optional<Loans> optionalLoan = loansRepository.findByMobileNumber(loansDto.getMobileNumber());
            return optionalLoan.isPresent();
        }

        public LoansDto findLoanByMobileNumber(String mobileNumber){
            Loans loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
            );
            return LoansMapper.mapToLoanDto(loan, new LoansDto());
        }
    
}
