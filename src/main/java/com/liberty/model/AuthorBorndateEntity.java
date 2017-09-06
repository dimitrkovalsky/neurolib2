package com.liberty.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by user on 06.06.2017.
 */
@Entity
@Table(name = "author_born_date", schema = "flibusta")
@Data
public class AuthorBorndateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ll_author_id")
    private Long livelibAuthorId;
    @Column(name = "nl_author_id")
    private Long neurolibAuthorId;
    @Column(name = "born_year")
    private Integer bornYear;
    @Column(name = "born_month")
    private Integer bornMonth;
    @Column(name = "born_day")
    private Integer bornDay;
    @Column(name = "author_name")
    private String authorName;


}