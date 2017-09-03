package com.liberty.dto.sitemap;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by user on 03.09.2017.
 */
@Data
@XStreamAlias("sitemap")
public class SitemapLink implements Serializable{

    @XStreamAlias("loc")
    private String location ;
}
