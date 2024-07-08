package com.tsk.todo.exception;

import lombok.Data;

import java.io.Serial;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Data
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3303518302920463234L;

    private final StatusEnum status;

    public ServiceException(StatusEnum status, String message) {
        super(message);
        this.status = status;
    }

    public ServiceException(StatusEnum status) {
        this(status, status.message);
    }
}
