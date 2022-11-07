package com.goodjob.post.error;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, PostErrorCode.ENTITY_NOT_FOUND);
    }
}
