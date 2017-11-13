package rav.model;

import org.springframework.data.repository.CrudRepository;
import rav.model.entety.User;

import javax.transaction.Transactional;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {
    User findByLogin(String email);

    User findById(long id);
}
