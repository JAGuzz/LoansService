package com.jaguzz.loans.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaguzz.loans.constants.LoansConstants;
import com.jaguzz.loans.dto.LoansDto;
import com.jaguzz.loans.dto.ResponseDto;
import com.jaguzz.loans.service.ILoansService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(path = "/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class LoansController {

    private ILoansService loansService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@Valid @RequestBody LoansDto loansDto) {
        loansService.createLoan(loansDto);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam 
    @Pattern(regexp ="(^$|[0-9]{10})", message = "Mobile number should be 10 digits") String mobileNumber) {
        LoansDto loansDto = loansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }
    
    @PutMapping("/update")
    public ResponseEntity<LoansDto> updateLoan(@Valid @RequestBody LoansDto loansDto){
        LoansDto response = loansService.updateLoan(loansDto);
        return ResponseEntity.status(HttpStatus.OK)
        .body(response); 
    }

    // delete?mobileNumber=XXXXX
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam 
    @Pattern(regexp ="(^$|[0-9]{10})", message = "Mobile number should be 10 digits") String mobileNumber){
        loansService.deleteLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
    }

}
