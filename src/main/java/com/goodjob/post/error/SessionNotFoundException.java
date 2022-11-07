package com.goodjob.post.error;

public class SessionNotFoundException extends BusinessException{
    public SessionNotFoundException() {
        super(PostErrorCode.SESSION_NOT_FOUND);
    }
}
