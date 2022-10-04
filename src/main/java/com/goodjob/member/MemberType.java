package com.goodjob.member;


public enum MemberType {
    USER("유저"),
    COMPANY("기업");

    private String identity;

    MemberType(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
