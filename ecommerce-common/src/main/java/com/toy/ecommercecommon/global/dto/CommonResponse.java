package com.toy.ecommercecommon.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toy.ecommercecommon.global.constants.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

	private ResponseResult result = ResponseResult.SUCCESS;
    private int status = HttpStatus.OK.value();
    private String errorCode;
    private String message;
    private T data;

    public CommonResponse(T data) {
    	setData(data);
    }
	
	public void setError(HttpStatus httpStatus, String message) {
		setResult(ResponseResult.ERROR);
		setStatus(httpStatus.value());
		setMessage(message);
	}
	
	public void setError(HttpStatus httpStatus, String errorCode, String message) {
		setResult(ResponseResult.ERROR);
		setStatus(httpStatus.value());
		setErrorCode(errorCode);
		setMessage(message);
	}
}
