package com.liberty.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

/**
 * @author dkovalskyi
 * @since 01.06.2017
 */
@Entity
@Table(name = "quote", schema = "neurolib")
@Data
@NoArgsConstructor
public class QuoteEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "quote_author_id")
    private Integer quoteAuthorId;

    @Column(name = "flibusta_author_id")
    private Integer flibustaAuthorId;

    @Column(name = "quote_author_name")
    private String quoteAuthorName;

    @Column(name = "tags")
    @Convert(converter = MapConverter.class)
    private Map<Integer, String> tags;

    public static class MapConverter implements AttributeConverter<Map<Integer, String>, String> {
        private ObjectMapper mapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Map<Integer, String> map) {
            try {

                return mapper.writeValueAsString(map);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return null;
            }
        }

        @Override
        public Map<Integer, String> convertToEntityAttribute(String json) {
            try {
                return (Map<Integer, String>) mapper.readValue(json, new TypeReference<Map<Integer, String>>() {
                });
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return null;
            }
        }
    }
}
