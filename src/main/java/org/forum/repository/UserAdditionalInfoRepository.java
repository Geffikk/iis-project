package org.forum.repository;

import org.forum.entities.Post;
import org.forum.entities.user.User;
import org.forum.entities.user.UserAdditionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAdditionalInfoRepository extends JpaRepository<UserAdditionalInfo, Integer> {

    UserAdditionalInfo findByUser(User user);
}
