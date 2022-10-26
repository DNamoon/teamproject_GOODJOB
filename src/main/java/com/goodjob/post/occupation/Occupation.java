package com.goodjob.post.occupation;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "occId")
    private Long occId;
    @Column(name="occCode", unique = true)
    private String occCode;
    @Column(name= "occName", unique = true)
    private String occName;

    public Occupation(String s, String s2) {
        this.occCode = s;
        this.occName = s2;
    }
}
