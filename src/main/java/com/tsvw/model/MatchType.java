package com.tsvw.model;

public enum MatchType {

    PRELIM, EIGHTHFINAL, QUARTERFINAL, SEMIFINAL, FINAL;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replace("_", " ");
    }
}
