package com.jaguzz.loans.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jaguzz.loans.dto.LoansDto;
import com.jaguzz.loans.entity.Loans;
import com.jaguzz.loans.exception.LoanAlreadyExistException;
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
        Optional<Loans> optionalLoan = loansRepository.finByMobileNumber(loansDto.getMobileNumber());
        if(optionalLoan.isPresent()){
            throw new LoanAlreadyExistException("Loan already registered with the given mobile number.");
        }

        loan.setCreatedAt(LocalDateTime.now());
        loan.setCreatedBy("Loans_MS");
        loansRepository.save(loan);
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchLoan'");
    }

}
