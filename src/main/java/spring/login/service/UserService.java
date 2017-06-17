package spring.login.service;

import spring.login.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
