package com.revature.service;

import com.revature.model.Account;
import com.revature.repository.AccountDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTest {

    private AccountService accountService;

    @Mock
    private AccountDAO accountDAO;

    private Account account = new Account();

    @BeforeEach
    public void setup(){
        account.setAccountId(10000);
        account.setAccessLevel(1);
        account.setFirstName("John");
        account.setLastName("Smith");
        account.setUsername("jsmith1");
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountDAO);
        Mockito.when(accountDAO.getAccountById(10000)).thenReturn(account);
    }


    @Test
    public void getAccountTest(){
        assertEquals(10000, accountService.getAccountById(10000).getAccountId());
        assertEquals("jsmith1",accountService.getAccountById(10000).getUsername());
    }

}
