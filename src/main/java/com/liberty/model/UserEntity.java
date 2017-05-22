package com.liberty.model;

import com.liberty.common.enumeration.UserAuthority;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 04.05.2017.
 */

@Entity(name = "user" )
@Table(name = "user", schema = "neurolib")
@Data
public class UserEntity implements Serializable{

    private static final long serialVersionUID = -3752974173565474629L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "createTime")
    private Long createTime;

    @Column(name = "authority")
    private UserAuthority authority;

    @Column(name = "login")
    private String login;

    @Column(name = "vkontakteId")
    private String vkontakteId;

    @Column(name = "facebookId")
    private String facebookId;

    @Column(name = "linkedinId")
    private String linkedinId;

    @Column(name = "twitterId")
    private String twitterId;

}