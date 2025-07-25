package com.example.tasksSystem.Services;

import com.example.tasksSystem.Repositories.AccountRepository;
import com.example.tasksSystem.elements.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getAccountByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public void addNewAuth(Account account){
        accountRepository.save(account);
    }

}
