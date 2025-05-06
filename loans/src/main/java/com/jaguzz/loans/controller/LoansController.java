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

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class LoansController {

    private ILoansService loansService;

    @PostMapping("path")
    public ResponseEntity<ResponseDto> createLoan(@RequestBody LoansDto loansDto) {
        loansService.createLoan(loansDto);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }
    

}
