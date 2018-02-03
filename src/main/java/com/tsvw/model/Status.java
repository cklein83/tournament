package com.tsvw.model;

public enum Status  {
        STARTED, FINISHED, NEW;

        @Override
        public String toString() {
                return super.toString().toLowerCase();
        }
}
