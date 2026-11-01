package com.vidaplena;

public class Paciente extends Persona {
    private String idRegistro;

    public Paciente(String idRegistro, String nombre, int edad) {
        super(nombre, edad);
        this.idRegistro = idRegistro;
    }

    public String getIdRegistro() {
        return idRegistro;
    }

    @Override
    public String obtenerFichaResumen() {
        return "Paciente [" + idRegistro + "]: " + getNombre() + " (" + getEdad() + " años)";
    }
}