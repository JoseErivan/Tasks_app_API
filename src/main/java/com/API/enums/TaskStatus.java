package com.API.enums;

public enum TaskStatus {
    NOT_COMPLETED(0),
    COMPLETED(1);

    private final int id;

    TaskStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
