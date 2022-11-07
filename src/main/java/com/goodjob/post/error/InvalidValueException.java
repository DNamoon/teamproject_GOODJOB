package com.goodjob.post.error;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String value) {
        super(value, PostErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(String value, PostErrorCode errorCode) {
        super(value, errorCode);
    }
}
