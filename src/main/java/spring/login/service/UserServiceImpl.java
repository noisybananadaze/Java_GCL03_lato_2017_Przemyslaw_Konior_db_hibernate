package spring.login.service;

import org.springframework.transaction.annotation.Transactional;
import spring.login.model.User;
import spring.login.repository.RoleRepository;
import spring.login.repository.UserRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void activateUser(User user) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("UPDATE user SET enabled =: enabled where id =: userid");
        query.setParameter("enabled", 1);
        query.setParameter("userid", user.getId());
        query.executeUpdate();

    }
}
