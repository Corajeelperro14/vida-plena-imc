package com.vidaplena;

public class ValoracionIMC {
    private Paciente paciente;
    private double peso;
    private double estatura;
    private double imc;
    private String diagnostico;

    public ValoracionIMC(Paciente paciente, double peso, double estatura) {
        this.paciente = paciente;
        this.peso = peso;
        this.estatura = estatura;
        this.imc = calcularIMC();
        this.diagnostico = clasificarIMC(this.imc);
    }

    private double calcularIMC() {
        double resultado = peso / Math.pow(estatura, 2);
        return Math.round(resultado * 100.0) / 100.0;
    }

    private String clasificarIMC(double valorImc) {
        if (valorImc < 18.5) {
            return "Bajo peso";
        } else if (valorImc <= 24.9) {
            return "Normal";
        } else if (valorImc <= 29.9) {
            return "Sobrepeso";
        } else {
            return "Obesidad";
        }
    }

    public Paciente getPaciente() { return paciente; }
    public double getPeso() { return peso; }
    public double getEstatura() { return estatura; }
    public double getImc() { return imc; }
    public String getDiagnostico() { return diagnostico; }
}