package com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.bd1;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.responses.ResponseUsersList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByEmail(String email);

    @Query(value = "select u.id, u.name, u.rol from users u order by u.name desc;", nativeQuery = true )
    public List<ResponseUsersList> getUsersList();

    @Query(value = "select u.secret_key from users u where u.email = :email;", nativeQuery = true )
    public String getSecretKeyByEmail(@Param("email") String email);

}
