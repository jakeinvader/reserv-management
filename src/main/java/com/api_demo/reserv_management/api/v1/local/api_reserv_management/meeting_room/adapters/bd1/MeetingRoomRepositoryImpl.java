package com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.bd1;

import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class MeetingRoomRepositoryImpl {

    @PersistenceContext(unitName = "Bd1PostgresConfig")
    private EntityManager em;

    public List<?> findDataBySql(String sql)
    {
        try {
            List<?> result = em.createNamedQuery(sql).getResultList();
            return result;
        }
        catch (Exception e) {
            UtilsLocal.log("Sala de reuniones: Error en findDataBySql:\n" + e.getMessage());
            return null;
        }
    }
}
