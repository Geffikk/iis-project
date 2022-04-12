package org.forum.service;

import org.forum.entities.Post;
import org.forum.entities.user.User;
import org.forum.entities.user.UserAdditionalInfo;
import org.forum.repository.UserAdditionalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAdditionalInfoServiceImpl implements UserAdditionalInfoService {

    @Autowired
    UserAdditionalInfoRepository userAdditionalInfoRepository;

    @Override
    public UserAdditionalInfo findOne(int id) {
        Optional<UserAdditionalInfo> optional = userAdditionalInfoRepository.findById(id);
        UserAdditionalInfo userAdditionalInfo = null;
        if(optional.isPresent()) {
            userAdditionalInfo = optional.get();
        }else {
            throw new RuntimeException("Info s id: " + id + " nebolo najdene!");
        }
        return userAdditionalInfo;
    }

    @Override
    public UserAdditionalInfo findByUser(User user) {
        return userAdditionalInfoRepository.findByUser(user);
    }

    @Override
    public void save(UserAdditionalInfo userAdditionalInfo) {
        userAdditionalInfoRepository.save(userAdditionalInfo);
    }
}
