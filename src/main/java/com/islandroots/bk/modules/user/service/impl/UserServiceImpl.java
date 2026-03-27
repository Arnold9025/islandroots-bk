package com.islandroots.bk.modules.user.service.impl;

import com.islandroots.bk.modules.user.entity.User;
import com.islandroots.bk.modules.user.repository.UserRepository;
import com.islandroots.bk.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User save(User entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
