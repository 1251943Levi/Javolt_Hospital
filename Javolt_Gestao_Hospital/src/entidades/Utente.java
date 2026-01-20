package entidades;

public class Utente extends Pessoa {
    private Sintoma[] sintomas;
    private int totalSintomas;
    private String nivelUrgencia; // "Baixa", "Media", "Urgente"
    private String especialidadeAtribuida;
    private int tempoEspera;
    private boolean triagemConcluida;
    private boolean atendido;
    private int horaChegada;
    private int horaAtendimento;
    private int horaSaida;

    public Utente() {
        super();
        this.sintomas = new Sintoma[10];
        this.totalSintomas = 0;
        this.triagemConcluida = false;
        this.atendido = false;
        this.tempoEspera = 0;
    }

    public Utente(String nome) {
        super(nome);
        this.sintomas = new Sintoma[10];
        this.totalSintomas = 0;
        this.triagemConcluida = false;
        this.atendido = false;
        this.tempoEspera = 0;
    }

    // Getters e Setters
    public Sintoma[] getSintomas() {
        return sintomas;
    }

    public int getTotalSintomas() {
        return totalSintomas;
    }

    public String getNivelUrgencia() {
        return nivelUrgencia;
    }

    public void setNivelUrgencia(String nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public String getEspecialidadeAtribuida() {
        return especialidadeAtribuida;
    }

    public void setEspecialidadeAtribuida(String especialidadeAtribuida) {
        this.especialidadeAtribuida = especialidadeAtribuida;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public void incrementarTempoEspera() {
        this.tempoEspera++;
    }

    public boolean isTriagemConcluida() {
        return triagemConcluida;
    }

    public void setTriagemConcluida(boolean triagemConcluida) {
        this.triagemConcluida = triagemConcluida;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
        if (atendido) {
            this.horaAtendimento = GestaoHospital.getUnidadeTempoAtual();
        }
    }

    public int getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(int horaChegada) {
        this.horaChegada = horaChegada;
    }

    public int getHoraAtendimento() {
        return horaAtendimento;
    }

    public int getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(int horaSaida) {
        this.horaSaida = horaSaida;
    }

    // Métodos
    public void adicionarSintoma(Sintoma sintoma) {
        if (totalSintomas < sintomas.length) {
            sintomas[totalSintomas] = sintoma;
            totalSintomas++;
        }
    }

    public boolean contemSintoma(String nomeSintoma) {
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(nomeSintoma)) {
                return true;
            }
        }
        return false;
    }

    public String getNivelUrgenciaCor() {
        if (nivelUrgencia == null) return "Não definido";

        return switch (nivelUrgencia) {
            case "Baixa" -> "Verde";
            case "Media" -> "Laranja";
            case "Urgente" -> "Vermelha";
            default -> "Não definido";
        };
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Urgência: " + getNivelUrgenciaCor() +
                " | Espera: " + tempoEspera + " UT" +
                " | Especialidade: " + (especialidadeAtribuida != null ? especialidadeAtribuida : "Não definida") +
                " | Status: " + (atendido ? "Atendido" : triagemConcluida ? "Aguardando" : "Sem triagem");
    }

    public String listarSintomas() {
        if (totalSintomas == 0) return "Nenhum sintoma registado";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalSintomas; i++) {
            sb.append("\n  - ").append(sintomas[i].toString());
        }
        return sb.toString();
    }
}