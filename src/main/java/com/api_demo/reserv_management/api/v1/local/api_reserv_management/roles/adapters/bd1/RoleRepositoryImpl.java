package com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.bd1;

import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class RoleRepositoryImpl {

    @PersistenceContext(unitName = "Bd1PostgresConfig")
    private EntityManager em;

    public List<?> findDataBySql(String sql)
    {
        try {
            List<?> result = em.createNamedQuery(sql).getResultList();
            return result;
        }
        catch (Exception e) {
            UtilsLocal.log("Roles: Error en findDataBySql:\n" + e.getMessage());
            return null;
        }
    }
}
