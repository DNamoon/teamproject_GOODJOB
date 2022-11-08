package com.goodjob.post.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

@ControllerAdvice("com.goodjob.post")
@Slf4j
public class PostExceptionHandler {
    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(PostErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
//    @ExceptionHandler(BindException.class)
//    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
//        log.error("handleBindException", e);
//        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        ModelAndView mv = new ModelAndView();
        final ErrorResponse response = ErrorResponse.of(PostErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        final ErrorResponse response = ErrorResponse.of(PostErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(PostErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
    }

    @ExceptionHandler(SessionNotFoundException.class)
    protected ModelAndView sessionNotFoundException(SessionNotFoundException e) {
        log.error("SessionNotFoundException", e);
        final ErrorResponse response = ErrorResponse.of(PostErrorCode.SESSION_NOT_FOUND);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login");
        mv.addObject("errorResponse",response);
        return mv;
    }
    @ExceptionHandler(SessionCompanyAccountNotFound.class)
    protected ModelAndView SessionCompanyAccountNotFound(SessionCompanyAccountNotFound e) {
        log.error("SessionCompanyAccountNotFound", e);
        final ErrorResponse response = ErrorResponse.of(PostErrorCode.SESSION_COMPANY_ACCOUNT_NOT_FOUND);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login");
        mv.addObject("errorResponse",response);
        return mv;
    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
//        log.error("handleEntityNotFoundException", e);
//        final ErrorResponse response = ErrorResponse.of(PostErrorCode.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
