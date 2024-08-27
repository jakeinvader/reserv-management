package com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.bd1;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RolEntity, Integer> {

    @Transactional
    public List<?>findDataBySql(String sql);

    @Query(value = "select r.role from rols r where r.id = :id;", nativeQuery = true )
    public String getRoleById(@Param("id") Integer id);

}
