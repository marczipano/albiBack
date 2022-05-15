package hu.albi.back.service;

import hu.albi.back.model.Sublet;
import hu.albi.back.model.User;
import hu.albi.back.repo.SubletRepository;
import hu.albi.back.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUserById(Long id){
        return userRepository.findUserById(id);
    }
}
