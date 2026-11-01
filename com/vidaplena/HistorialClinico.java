package com.vidaplena;

import java.util.ArrayList;
import java.util.List;

public class HistorialClinico {
    private List<ValoracionIMC> valoraciones;

    public HistorialClinico() {
        this.valoraciones = new ArrayList<>();
    }

    public void agregarValoracion(ValoracionIMC valoracion) {
        valoraciones.add(valoracion);
    }

    public List<ValoracionIMC> getValoraciones() {
        return valoraciones;
    }

    public Object[][] obtenerMatrizHistorial() {
        Object[][] matriz = new Object[valoraciones.size()][6];
        for (int i = 0; i < valoraciones.size(); i++) {
            ValoracionIMC v = valoraciones.get(i);
            matriz[i][0] = v.getPaciente().getNombre();
            matriz[i][1] = v.getPaciente().getEdad();
            matriz[i][2] = v.getPeso() + " kg";
            matriz[i][3] = v.getEstatura() + " m";
            matriz[i][4] = v.getImc();
            matriz[i][5] = v.getDiagnostico();
        }
        return matriz;
    }
}