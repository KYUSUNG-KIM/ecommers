package com.toy.ecommerce.global.handler;

import com.toy.ecommerce.global.dto.CommonResponse;
import com.toy.ecommerce.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler {


    /*
     * 파라미터 바인딩할 때 오류
     * 기본적으로는 스프링에서 400 등의 에러를 준다.
     */
    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class
    })
    public CommonResponse handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String message = ex.getLocalizedMessage();

        if (StringUtils.isEmpty(message)) {
            message = "Bad request";
        }

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setError(HttpStatus.BAD_REQUEST, message);

        return commonResponse;
    }


    @ExceptionHandler(value = {Exception.class})
    protected CommonResponse handleException(RuntimeException ex, WebRequest request) {
        String message = ex.getLocalizedMessage();

        if (StringUtils.isEmpty(message)) {
            message = "Server occurred error";
        }

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR, message);
        log.error(message);
        ex.printStackTrace();

        return commonResponse;
    }


    @ExceptionHandler({
            CustomException.class
    })
    public CommonResponse customerRequestException(final CustomException c) {

        log.warn("API Exception Error Code : {}", c.getErrorCode());
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setError(HttpStatus.BAD_REQUEST, c.getMessage(), c.getErrorCode().getCode());
        return commonResponse;
    }

}
