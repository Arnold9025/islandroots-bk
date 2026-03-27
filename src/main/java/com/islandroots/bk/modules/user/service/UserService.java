package com.islandroots.bk.modules.user.service;

import com.islandroots.bk.modules.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(User entity);
    Optional<User> findById(UUID id);
    List<User> findAll();
    void deleteById(UUID id);
}
