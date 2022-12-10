package com.ajou.noop.domain;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;

    @Column
    private String email;

    @Column(name="user_name")
    private String name;

    @Column
    private String desc;

    @Column
    private UserStatus status;

}
