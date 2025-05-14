package com.academia.sistemaMatricula.service.impl;

import com.academia.sistemaMatricula.model.Role;
import com.academia.sistemaMatricula.model.User;
import com.academia.sistemaMatricula.repository.IGenericRepo;
import com.academia.sistemaMatricula.repository.IRoleRepo;
import com.academia.sistemaMatricula.repository.IUserRepo;
import com.academia.sistemaMatricula.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, String> implements IUserService {

    private final IUserRepo userRepo;
    private final IRoleRepo roleRepo;
    private final BCryptPasswordEncoder bcrypt;
    @Override
    protected IGenericRepo<User, String> getRepo() {
        return userRepo;
    }

    @Override
    public Mono<User> saveHash(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Mono<com.academia.sistemaMatricula.security.User> searchByUser(String username) {
        return userRepo.findOneByUsername(username)
                .flatMap(user -> Flux.fromIterable(user.getRoles())
                        .flatMap(userRole -> roleRepo.findById(userRole.getId())
                                .map(Role::getName))
                        .collectList()
                        .map(roles -> new com.academia.sistemaMatricula.security.User(user.getUsername(), user.getPassword(), user.isStatus(), roles))
                );
    }



    @Override
    protected void applyUpdates(User existing, User updated) {

    }
}
