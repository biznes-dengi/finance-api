package com.finance.app.account.dao;

import com.finance.app.account.domain.Account;
import com.finance.app.account.exception.ParentException;

import java.util.List;

public interface AccountDao {

    /**
     * That method was created only for scheduledCheckGoalsIfOverdue. It's not pageable.
     * It should be refactored later.
     *
     * @return list of ids of all users
     */
    List<Integer> fetchListIdsOfAllUsers();

    /**
     * Fetch Account record by id
     *
     * @param id unique identifier of Account entity
     * @return found Account record
     */
    Account fetchAccountById(int id) throws ParentException;

    /**
     * Fetch Account record by email
     *
     * @param email unique identifier of Account entity
     * @return found Account record
     */
    Account fetchAccountByEmail(String email) throws ParentException;

    /**
     * Fetch Account record by username
     *
     * @param username unique identifier of Account entity
     * @return found Account record
     */
    Account fetchAccountByUsername(String username) throws ParentException;

    /**
     * Create new Account record from given source
     *
     * @param source new Account for save
     * @return new created record
     */
    Account createAccount(Account source);

    /**
     * Checks If an Account record exists for the given accountId
     *
     * @param accountId unique identifier of Account entity
     * @return true if exists, false otherwise
     */
    boolean existsAccount(int accountId);
}
