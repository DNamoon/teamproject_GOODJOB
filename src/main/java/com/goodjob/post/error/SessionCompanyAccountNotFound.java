package com.goodjob.post.error;

public class SessionCompanyAccountNotFound extends BusinessException {
    public SessionCompanyAccountNotFound() {
        super(PostErrorCode.SESSION_COMPANY_ACCOUNT_NOT_FOUND);
    }
}
