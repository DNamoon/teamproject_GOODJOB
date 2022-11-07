package com.goodjob.post.error;

public class BusinessException extends RuntimeException {

    private PostErrorCode errorCode;

    public BusinessException(String message, PostErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public PostErrorCode getErrorCode() {
        return errorCode;
    }

}
