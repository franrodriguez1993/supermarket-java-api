package com.app.services.user;

import com.app.entities.User;
import com.app.services.base.BaseService;

public interface UserService extends BaseService<User, Long> {

  User register(User user) throws Exception;

}
