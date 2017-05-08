package com.liberty.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 04.05.2017.
 */

@Entity(name = "userLibertyProfile" )
@Table(name = "userLibertyProfile", schema = "neurolib")
@Data
public class UserLibertyProfileEntity  implements Serializable{
    private static final long serialVersionUID = -2434950456931330218L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "changeTime")
    private Long changeTime;

}