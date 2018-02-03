package com.tsvw.util;

import com.tsvw.model.MatchType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MatchTypeConverter implements AttributeConverter<MatchType, String> {

    @Override
    public String convertToDatabaseColumn(MatchType attribute) {
        switch (attribute) {
            case PRELIM:
                return "P";
            case EIGHTHFINAL:
                return "1/8";
            case QUARTERFINAL:
                return "1/4";
            case SEMIFINAL:
                return "1/2";
            case FINAL:
                return "1";
            default:
                throw new IllegalArgumentException("Unknown" + attribute);
        }
    }

    @Override
    public MatchType convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "P":
                return MatchType.PRELIM;
            case "1/8":
                return MatchType.EIGHTHFINAL;
            case "1/4":
                return MatchType.QUARTERFINAL;
            case "1/2":
                return MatchType.SEMIFINAL;
            case "1":
                return MatchType.QUARTERFINAL;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}