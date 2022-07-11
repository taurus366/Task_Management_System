package com.system.management.service.Impl;

import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.entity.AccountEntity;
import com.system.management.model.service.LoginUserServiceModel;
import com.system.management.model.service.RegisterNewUserServiceModel;
import com.system.management.model.user.CurrentUser;
import com.system.management.repository.AccountRepository;
import com.system.management.service.AccountService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CurrentUser currentUser;

    public AccountServiceImpl(AccountRepository accountRepository, CurrentUser currentUser) {
        this.accountRepository = accountRepository;
        this.currentUser = currentUser;
    }

    @Override
    public UserInformationDTO login(LoginUserServiceModel loginUserServiceModel) {

        AccountEntity account = accountRepository
                .getAccountEntityByEmail(loginUserServiceModel.getEmail()).orElseThrow(() -> new UsernameNotFoundException("There isn't any account with that email address"));

        this.login(account);

        return new UserInformationDTO()
                .setEmail(account.getEmail())
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName());
    }

    @Override
    public UserInformationDTO registerThenLogin(RegisterNewUserServiceModel registerNewUserServiceModel) {

        AccountEntity newAccount = new AccountEntity();
        newAccount
                .setFirstName(registerNewUserServiceModel.getFirstName())
                .setLastName(registerNewUserServiceModel.getLastName())
                .setEmail(registerNewUserServiceModel.getEmail())
                .setCreatedAt(Instant.now())
                .setUpdateAt(Instant.now());

        AccountEntity savedAccount = accountRepository
                .save(newAccount);

        this.login(savedAccount);

        return new UserInformationDTO()
                .setFirstName(savedAccount.getFirstName())
                .setLastName(savedAccount.getLastName())
                .setEmail(newAccount.getEmail());
    }

    @Override
    public boolean isEmailExists(String email) {

        return  accountRepository
                .existsAccountEntityByEmail(email);
    }

    @Override
    public void logout() {
        currentUser
                .clean();
    }

    @Override
    public UserInformationDTO checkUserLogged(String email) {
        AccountEntity account = this.accountRepository
                .getAccountEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

        return new UserInformationDTO()
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName())
                .setEmail(account.getEmail());
    }

    private void login(AccountEntity account) {

        currentUser
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName())
                .setEmail(account.getEmail())
                .setLoggedIn(true);

    }
}
