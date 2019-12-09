package cn.my.system;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
自定义资源不存在异常类，及返回404页面
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoFoundException extends RuntimeException {
    public NoFoundException() {
    }

    public NoFoundException(String message) {
        super(message);
    }

    public NoFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
