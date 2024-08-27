package com.api_demo.reserv_management.api.v1.local.utils;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ResponseLocal {

    public boolean success;
    public Integer code;
    public Object data;
    public String message;
    public String description;
    private String action;

    public ResponseLocal(String action) {
        this.success = true;
        this.code = 200;
        this.action = action;
    }

    public HttpStatus validateService(
            Object data,
            String message,
            String classPath,
            String payload,
            HttpServletRequest req
    ){
        UtilsLocal.log("\n============== validateService ==============");
        if (UtilsService.isErrorService(data)){
            ErrorService errorService = (ErrorService) data;

            String messageB = errorService.getMessage();
            String descriptionB = errorService.getDescription();
            String classPathB = errorService.getClassPath();
            Integer codeB = errorService.getCode();
            this.setError(
                    codeB,
                    messageB,
                    descriptionB,
                    new ArrayList<ObjectError>(),
                    classPathB,
                    payload,
                    req
            );
            UtilsLocal.log("===> Error validateService: \n"); UtilsLocal.log(descriptionB);
            HttpStatus httpStatus = this.getHttpStatus(codeB);
            return httpStatus;
        }
        else {
            this.success = true;
            this.code = 200;
            this.message = message;
            this.data = data;
            this.setSuccess(
                    classPath,
                    payload,
                    req
            );
            return HttpStatus.OK;
        }
    }

    public void setSuccess(
        String classPath,
        String payload,
        HttpServletRequest req
    ) {
        Timestamp created = UtilsLocal.getTimestampDateTimeNow();
        String url = this.getUrl(req);
        String method = req.getMethod();

    }

    public void setError(
            Integer code,
            String message,
            String description,
            List<ObjectError> listErrors,
            String classPath,
            String payload,
            HttpServletRequest req
    ) {
        this.success = false;
        if (code == null) this.code = 500;
        else this.code = code;

        if (this.code == 200) this.success = true;

        if (listErrors.size() != 0) {
            StringBuilder messagesB = new StringBuilder();
            messagesB.append("Lista_errores:");
            listErrors.forEach(err -> messagesB.append( "- "+err.getDefaultMessage()));
            this.message = messagesB.toString();
        }
        else {
            this.message = message;
        }

        if (description == null || description.equals("")) description = this.message;
        this.description = description;

        Timestamp created = UtilsLocal.getTimestampDateTimeNow();
        String url = this.getUrl(req);
        String method = req.getMethod();
    }

    private String getUrl(HttpServletRequest req) {
        String queryString = (req.getQueryString() != null)? "!" + req.getQueryString() : "";
        return req.getRequestURL().toString() + queryString;
    }

    private HttpStatus getHttpStatus(Integer code) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (code) {
            case 400:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case 401:
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case 403:
                httpStatus = HttpStatus.FORBIDDEN;
                break;
            case 404:
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case 405:
                httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                break;
            case 406:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                break;
            case 408:
                httpStatus = HttpStatus.REQUEST_TIMEOUT;
                break;
            case 409:
                httpStatus = HttpStatus.CONFLICT;
                break;
            case 501:
                httpStatus = HttpStatus.NOT_IMPLEMENTED;
                break;
            case 502:
                httpStatus = HttpStatus.BAD_GATEWAY;
                break;
            case 503 :
                httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
                break;
            case 504:
                httpStatus = HttpStatus.GATEWAY_TIMEOUT;
                break;
        }
        return  httpStatus;
    }
}
