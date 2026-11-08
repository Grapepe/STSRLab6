package org.example.stsr_6.Service;

import org.example.stsr_6.Entities.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUser();
}