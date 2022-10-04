package com.goodjob.member;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Memberdiv {

    @Id
    @Enumerated(EnumType.STRING)
    private MemberType memdivCode;

}
