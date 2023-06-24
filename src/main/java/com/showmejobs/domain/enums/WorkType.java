package com.showmejobs.domain.enums;

public enum WorkType {
    HYBRID("Hybrid"),
    REMOTE("Remote"),
    ONSITE("Onsite");

    private String name;

    WorkType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WorkType fromName(String name) {
        for (WorkType workType : WorkType.values()) {
            if (workType.name.equalsIgnoreCase(name)) {
                return workType;
            }
        }
        throw new IllegalArgumentException("Invalid Work Type name: " + name);
    }
}
