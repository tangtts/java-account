package com.tsk.todo.exception;

import com.tsk.todo.resp.CommonResp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = ServiceException.class)
    public ResultResponse<Void> handleServiceException(ServiceException serviceException, HttpServletRequest request) {
        log.warn("request {} throw ServiceException \n", request, serviceException);
        return ResultResponse.error(serviceException.getStatus(), serviceException.getMessage());
    }


    /**
     * 其他异常拦截
     *
     * @param ex      异常
     * @param request 请求参数
     * @return 接口响应
     */
    @ExceptionHandler(Exception.class)
    public ResultResponse<Void> handleException(Exception ex, HttpServletRequest request) {
        log.error("request {} throw unExpectException \n", request, ex);
        return ResultResponse.error(StatusEnum.SERVICE_ERROR);
    }

    /**
     * 参数非法校验
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        try {
            List<ObjectError> errors = ex.getBindingResult().getAllErrors();
            String message = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            log.error("param illegal: {}", message);
            return ResultResponse.error(StatusEnum.PARAM_INVALID, message);
        } catch (Exception e) {
            return ResultResponse.error(StatusEnum.SERVICE_ERROR);
        }
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResultResponse<Void> handleUnexpectedTypeException(UnexpectedTypeException ex,
                                                              HttpServletRequest request) {
        log.error("catch UnexpectedTypeException, errorMessage: \n", ex);
        return ResultResponse.error(StatusEnum.PARAM_INVALID, ex.getMessage());
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResultResponse<Void> handleUnexpectedTypeException(SQLSyntaxErrorException ex,
                                                              HttpServletRequest request) {
        log.error("catch SQLSyntaxErrorException, errorMessage: \n", ex);
        return ResultResponse.error(StatusEnum.SQL_ERROR, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultResponse<Void> handleUnexpectedTypeException(HttpRequestMethodNotSupportedException ex,
                                                              HttpServletRequest request) {
        log.error("catch SQLSyntaxErrorException, errorMessage: \n", ex);
        return ResultResponse.error(StatusEnum.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public  ResultResponse<String> accessDeniedException(RuntimeException e) {
        log.error("运行时异常:{}", e.getMessage());
        throw e;
    }



}
