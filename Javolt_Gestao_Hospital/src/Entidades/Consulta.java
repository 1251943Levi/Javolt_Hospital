package Entidades;

public class Consulta {

    // ================== ATRIBUTOS ==================
    private Medico medico;
    private Paciente paciente;

    private int tempoRestante;   // unidades de tempo até terminar a consulta
    private int tempoTotal;      // duração original da consulta

    // ================== CONSTRUTOR ==================
    public Consulta(Medico medico, Paciente paciente, int tempoConsulta) {
        this.medico = medico;
        this.paciente = paciente;
        this.tempoRestante = tempoConsulta;
        this.tempoTotal = tempoConsulta;
    }

    // ================== LÓGICA DE TEMPO ==================

    // Avança 1 unidade de tempo na consulta
    public void avancarTempo() {
        if (tempoRestante > 0) {
            tempoRestante--;
        }
    }

    // Verifica se a consulta terminou
    public boolean terminou() {
        return tempoRestante <= 0;
    }

    // ================== GETTERS ==================

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public int getTempoRestante() {
        return tempoRestante;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    // ================== toString ==================

    @Override
    public String toString() {return "Consulta{" +"Paciente=" + paciente.getNome() + ", Médico=" + medico.getNome() + ", Tempo restante=" + tempoRestante + "/" + tempoTotal + '}';
    }
}
