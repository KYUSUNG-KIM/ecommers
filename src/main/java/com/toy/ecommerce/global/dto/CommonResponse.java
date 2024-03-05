package com.toy.ecommerce.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
	
	private String result = "SUCCESS";
    private int status = HttpStatus.OK.value();
    private String errorCode;
    private String message;
    private Object paging;
    private Object data;

    public CommonResponse(Object data) {
    	setData(data);
    }
	
	public void setError(HttpStatus httpStatus, String message) {
		setResult("ERROR");
		setStatus(httpStatus.value());
		setMessage(message);
	}
	
	public void setError(HttpStatus httpStatus, String errorCode, String message) {
		setResult("ERROR");
		setStatus(httpStatus.value());
		setMessage(message);
		setErrorCode(errorCode);
	}
}
