package com.bonidev.api.model;

public enum Especialidad {
    ORTOPEDIA,
    CARDIOLOGIA,
    GINECOLOGIA,
    PEDIATRIA;

    public String capitalize() {
        var word = this.toString().toLowerCase();
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}

