package com.showmejobs.domain.enums;

public enum Seniority {
    INTERN("Intern"),
    JUNIOR("Junior"),
    MID("Mid"),
    SENIOR("Senior");

    private String name;

    Seniority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Seniority fromName(String name) {
        for (Seniority seniority : Seniority.values()) {
            if (seniority.name.equalsIgnoreCase(name)) {
                return seniority;
            }
        }
        throw new IllegalArgumentException("Invalid Seniority name: " + name);
    }
}
