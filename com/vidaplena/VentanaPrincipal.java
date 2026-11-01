package com.vidaplena;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JTextField txtNombre, txtEdad, txtPeso, txtEstatura;
    private JLabel lblResultadoIMC, lblDiagnostico;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private HistorialClinico historial;
    private int contadorPacientes = 1;

    public VentanaPrincipal() {
        historial = new HistorialClinico();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Centro Médico VidaPlena - Valoración Nutricional e IMC");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        JPanel pnlFormulario = new JPanel(new GridLayout(5, 2, 8, 8));
        pnlFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Paciente"));

        pnlFormulario.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        pnlFormulario.add(txtNombre);

        pnlFormulario.add(new JLabel("Edad (años):"));
        txtEdad = new JTextField();
        pnlFormulario.add(txtEdad);

        pnlFormulario.add(new JLabel("Peso (kg, ej: 70.5):"));
        txtPeso = new JTextField();
        pnlFormulario.add(txtPeso);

        pnlFormulario.add(new JLabel("Estatura (m, ej: 1.72):"));
        txtEstatura = new JTextField();
        pnlFormulario.add(txtEstatura);

        JButton btnCalcular = new JButton("Calcular y Registrar");
        JButton btnLimpiar = new JButton("Limpiar Campos");
        pnlFormulario.add(btnCalcular);
        pnlFormulario.add(btnLimpiar);

        JPanel pnlResultado = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlResultado.setBorder(BorderFactory.createTitledBorder("Resultado Inmediato"));
        lblResultadoIMC = new JLabel("IMC: --");
        lblResultadoIMC.setFont(new Font("Arial", Font.BOLD, 14));
        lblDiagnostico = new JLabel("Diagnóstico: --");
        lblDiagnostico.setFont(new Font("Arial", Font.BOLD, 14));
        pnlResultado.add(lblResultadoIMC);
        pnlResultado.add(lblDiagnostico);

        JPanel pnlNorte = new JPanel(new BorderLayout());
        pnlNorte.add(pnlFormulario, BorderLayout.CENTER);
        pnlNorte.add(pnlResultado, BorderLayout.SOUTH);
        add(pnlNorte, BorderLayout.NORTH);

        String[] columnas = {"Nombre", "Edad", "Peso", "Estatura", "IMC", "Diagnóstico OMS"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaHistorial = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Historia Clínica - Registro de Valoraciones"));
        add(scrollTabla, BorderLayout.CENTER);

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRegistro();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    private void procesarRegistro() {
        try {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int edad = Integer.parseInt(txtEdad.getText().trim());
            double peso = Double.parseDouble(txtPeso.getText().trim().replace(',', '.'));
            double estatura = Double.parseDouble(txtEstatura.getText().trim().replace(',', '.'));

            if (edad <= 0 || peso <= 0 || estatura <= 0) {
                JOptionPane.showMessageDialog(this, "Edad, peso y estatura deben ser mayores a cero.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (estatura > 2.5) {
                JOptionPane.showMessageDialog(this, "La estatura debe ser en metros (ejemplo: 1.70).", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = "PAC-" + contadorPacientes++;
            Paciente paciente = new Paciente(id, nombre, edad);
            ValoracionIMC valoracion = new ValoracionIMC(paciente, peso, estatura);

            historial.agregarValoracion(valoracion);

            lblResultadoIMC.setText("IMC: " + valoracion.getImc());
            lblDiagnostico.setText("Diagnóstico: " + valoracion.getDiagnostico());

            modeloTabla.addRow(new Object[]{
                    paciente.getNombre(),
                    paciente.getEdad(),
                    valoracion.getPeso() + " kg",
                    valoracion.getEstatura() + " m",
                    valoracion.getImc(),
                    valoracion.getDiagnostico()
            });

            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos en Edad, Peso y Estatura.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEdad.setText("");
        txtPeso.setText("");
        txtEstatura.setText("");
        txtNombre.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}