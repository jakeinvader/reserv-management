package com.api_demo.reserv_management.api.v1.local.utils;

public class UtilsService {

    public static boolean isErrorService (Object object) {
        String typeData = (object != null) ? object.getClass().getSimpleName() : "";
        if ( typeData.equals("ErrorService"))
            return true;
        return false;
    }
}
