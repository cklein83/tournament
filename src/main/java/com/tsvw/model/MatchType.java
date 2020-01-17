package com.tsvw.model;

public enum MatchType {

    PRELIM, EIGHTHFINAL, QUARTERFINAL, SEMIFINAL, SMALLFINAL, FINAL;

    @Override
    public String toString() {
        switch (super.toString().toUpperCase()) {
            case "PRELIM": return "Kleine Finalspiele";
            case "EIGHTHFINAL": return "Achtelfinale";
            case "QUARTERFINAL": return "Viertelfinale";
            case "SEMIFINAL": return "Halbfinale";
            case "SMALLFINAL": return "Spiel um Platz 3";
            case "FINAL": return "Finale";
            default: return "?";
        }
    }
}
