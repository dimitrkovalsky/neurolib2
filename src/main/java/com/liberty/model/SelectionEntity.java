package com.liberty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by user on 15.06.2017.
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "selection", schema = "neurolib")
@NoArgsConstructor
public class SelectionEntity {
    @Id
    @Column(name = "selection_id")
    private Long selectionId;

    @Column(name = "title")
    private String title;

    @Column(name = "user_made")
    private String userMade;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "description")
    private String description;

    @Column(name = "likes_count")
    private Integer likes;

    @Column(name = "votes_count")
    private Integer votes;

    @Override
    public String toString() {
        return "SelectionEntity{" +
                "selectionId=" + selectionId +
                ", title='" + title + '\'' +
                ", userMade='" + userMade + '\'' +
                ", createTime='" + createTime + '\'' +
                ", description='" + description + '\'' +
                ", likes=" + likes +
                ", votes=" + votes +
                '}';
    }
}
