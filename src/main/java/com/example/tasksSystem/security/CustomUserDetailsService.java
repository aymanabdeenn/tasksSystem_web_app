package com.example.tasksSystem.security;


import com.example.tasksSystem.Repositories.AccountRepository;
import com.example.tasksSystem.elements.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findFirstByUsername(username);
        if(account == null) throw new UsernameNotFoundException("Username not found");
        return new CustomUserDetails(account);
    }
}
