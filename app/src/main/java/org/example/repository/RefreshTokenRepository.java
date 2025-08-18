package org.example.repository;

import org.example.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer>
{
    //SELECT *from tokens where token = token1 THIS IS THE SQL QUERY
    //when we are using JPA in springboot then we can name the method instead of a whole sql qwery

    Optional<RefreshToken> findByToken(String token);


}
