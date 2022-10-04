package com.goodjob.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Admin")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adId;

    @Column(length = 20, unique = true)
    private String adLoginId;

    @Column(length = 20, nullable = false)
    private String adPw;

    @Column(length = 20, nullable = false)
    private String adIdentifier;

}
