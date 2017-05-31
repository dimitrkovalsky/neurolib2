package com.liberty.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "rate_view" )
@Table(name = "rate_view", schema = "neurolib")
@Data
public class BookRateEntity {
    @Id
    @Column(name = "BookId")
    private Long bookId;
    
    @Column(name = "Rate")
    private Integer rate;
    
}
