package com.example.Registration.service;

import com.example.Registration.data.AccountRepository;
import com.example.Registration.models.Account;
import com.example.Registration.utils.Result;
import com.example.Registration.utils.ResultType;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Result<Account> addNewAccount(@NonNull Account user) {
        Result<Account> result = new Result<>();

        // 1. user can't have id, otherwise wrong endpoint
        if (user.getId() != 0) {
            result.type(ResultType.FAILED);
            result.add("id cannot be set");

        // 2. user must have unique email
        } else if (repository.findByEmail(user.getEmail()).isPresent()) {
            result.type(ResultType.FAILED);
            result.add("email already taken");

        // 3. no preferences will be mandatory
        } else {
            Account _registered = repository.save(user);
            result.type(ResultType.SUCCESS);
            result.payload(_registered);
        }

        return result;
    }

     public Optional<Account> getAccountDetails(long id) {
        return id > 0 ? repository.findById(id) : Optional.empty();
    }

    boolean confirmAccountExists(long id) {
        return id > 0 && repository.existsById(id);
    }

    public void deleteUserById() {
        // only returns success statement and user id (now-deleted)
        return;
    }

}
