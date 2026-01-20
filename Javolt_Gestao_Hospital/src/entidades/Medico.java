package entidades;

public class Medico extends Pessoa {
    private String especialidade;
    private int horaEntrada;
    private int horaSaida;
    private double valorHora;
    private boolean disponivel;
    private boolean emConsulta;
    private int horasTrabalhadas;

    public Medico() {
        super();
        this.disponivel = false;
        this.emConsulta = false;
        this.horasTrabalhadas = 0;
    }

    public Medico(String nome, String especialidade, int horaEntrada, int horaSaida, double valorHora) {
        super(nome);
        this.especialidade = especialidade;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.valorHora = valorHora;
        this.disponivel = false;
        this.emConsulta = false;
        this.horasTrabalhadas = 0;
    }

    // Getters e Setters
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        if (horaEntrada < 1 || horaEntrada > 24) {
            throw new IllegalArgumentException("Hora de entrada deve estar entre 1 e 24");
        }
        this.horaEntrada = horaEntrada;
    }

    public int getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(int horaSaida) {
        if (horaSaida < 1 || horaSaida > 24) {
            throw new IllegalArgumentException("Hora de saída deve estar entre 1 e 24");
        }
        this.horaSaida = horaSaida;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        if (valorHora <= 0) {
            throw new IllegalArgumentException("Valor hora deve ser positivo");
        }
        this.valorHora = valorHora;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public boolean isEmConsulta() {
        return emConsulta;
    }

    public void setEmConsulta(boolean emConsulta) {
        this.emConsulta = emConsulta;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public void adicionarHorasTrabalhadas(int horas) {
        this.horasTrabalhadas += horas;
    }

    public double calcularSalario() {
        return horasTrabalhadas * valorHora;
    }

    public boolean estaNoHorario(int horaAtual) {
        if (horaEntrada <= horaSaida) {
            // Turno normal (ex: 8h-16h)
            return horaAtual >= horaEntrada && horaAtual <= horaSaida;
        } else {
            // Turno que passa da meia-noite (ex: 22h-6h)
            return horaAtual >= horaEntrada || horaAtual <= horaSaida;
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Especialidade: " + especialidade +
                " | Horário: " + horaEntrada + "h-" + horaSaida + "h" +
                " | Valor/h: " + valorHora + "€" +
                " | Status: " + (emConsulta ? "Em consulta" : disponivel ? "Disponível" : "Indisponível");
    }
}