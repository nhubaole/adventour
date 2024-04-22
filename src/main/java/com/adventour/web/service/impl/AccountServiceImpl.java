package com.adventour.web.service.impl;

import com.adventour.web.dto.AccountDto;
import com.adventour.web.models.Account;
import com.adventour.web.repository.AccountRepository;
import com.adventour.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public  AccountServiceImpl (AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    private AccountDto mapToAccountDto (Account account){
        return AccountDto.builder()
                .nameAccount(account.getNameAccount())
                .password(account.getPassword())
                .build();
    }

    @Override
    public List<AccountDto> findAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account)-> mapToAccountDto(account)).collect(Collectors.toList());
    }
}
