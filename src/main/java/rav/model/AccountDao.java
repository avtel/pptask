package rav.model;

import org.springframework.data.repository.CrudRepository;
import rav.model.entety.Account;

import javax.transaction.Transactional;

@Transactional
public interface AccountDao extends CrudRepository<Account, Long> {
    Account findByUsername(String email);

    Account findById(long id);
}
