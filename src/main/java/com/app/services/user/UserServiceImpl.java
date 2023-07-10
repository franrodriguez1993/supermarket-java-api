package com.app.services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.User;
import com.app.repositories.BaseRepository;
import com.app.repositories.UserRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

  @Autowired
  UserRepository userRepository;

  public UserServiceImpl(BaseRepository<User, Long> baseRepository) {
    super(baseRepository);
  }

  @Override
  public User register(User user) throws Exception {

    try {
      // check mail:
      Optional<User> checkMail = userRepository.findByEmail(user.getEmail());

      if (checkMail.isPresent()) {
        throw new Exception("EMAIL_IN_USE");
      }
      Optional<User> checkDNI = userRepository.findByDni(user.getDni());
      if (checkDNI.isPresent()) {
        throw new Exception("DNI_IN_USE");
      }

      return userRepository.save(user);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }
}
