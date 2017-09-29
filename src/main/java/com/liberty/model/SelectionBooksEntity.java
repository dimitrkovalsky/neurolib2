package com.liberty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by user on 16.06.2017.
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "selection_books", schema = "neurolib")
@NoArgsConstructor
public class SelectionBooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "selection_id")
    private Long selectionId;
    @Column(name = "livelib_book_id")
    private Long livelibBookId;
    @Column(name = "neurolib_book_id")
    private Long neurolibBookId;
    @Column(name = "votes_count")
    private Integer votes;
    @Column(name = "description")
    private String description;

}
