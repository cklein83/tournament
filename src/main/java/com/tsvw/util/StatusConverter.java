package com.tsvw.util;
/*
import com.tsvw.model.MatchType;
import com.tsvw.model.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status attribute) {
        switch (attribute) {
            case NEW:
                return "0";
            case STARTED:
                return "1";
            case FINISHED:
                return "2";
            default:
                throw new IllegalArgumentException("Unknown" + attribute);
        }
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "0":
                return Status.NEW;
            case "1":
                return Status.STARTED;
            case "2":
                return Status.FINISHED;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}
*/