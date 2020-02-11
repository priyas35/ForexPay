package com.squad.forexpay.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.ErrorDto;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("date", LocalDateTime.now());
		body.put("status", status.value());

		// Get all errors for field valid
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());

		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, HttpStatus.OK);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDto> userNotFoundException(UserNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(Constant.USER_NOT_FOUND);
		errorDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}

	@ExceptionHandler(CurrencyNotFoundException.class)
	public ResponseEntity<ErrorDto> currencyNotFoundException(CurrencyNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(Constant.CURRENCY_NOT_FOUND);
		errorDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
	
	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<ErrorDto> transactionNotFoundException(TransactionNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(Constant.TRANSACTION_NOT_FOUND);
		errorDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
	

	@ExceptionHandler(MinimumBalanceException.class)
	public ResponseEntity<ErrorDto> minimumBalanceException(MinimumBalanceException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(Constant.CURRENCY_NOT_FOUND);
		errorDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
	
	@ExceptionHandler(AccountnotFoundException.class)
	public ResponseEntity<ErrorDto> accountnotFoundException(AccountnotFoundException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(Constant.CURRENCY_NOT_FOUND);
		errorDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
}
