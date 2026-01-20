package Entidades;

public class Paciente {
        private String nome;
        private String nivelUrgencia;
        private String especialidadeDesejada;
        private int tempoEspera;
    private boolean emAtendimento = false;

        public Paciente(String nome, String nivelUrgencia, String especialidadeDesejada) {
            this.nome = nome;
            this.nivelUrgencia = nivelUrgencia;
            this.especialidadeDesejada = especialidadeDesejada;
            this.tempoEspera = 0;
        }

        public String getNome() {
            return nome;
        }

        public String getNivelUrgencia() {
            return nivelUrgencia;
        }

        public String getEspecialidadeDesejada() {
            return especialidadeDesejada;
        }

        public int getTempoEspera() {
            return tempoEspera;
        }

        public void incrementarTempoEspera() {
            tempoEspera++;
        }

    public boolean isEmAtendimento() {
        return emAtendimento;
    }

    public void setEmAtendimento(boolean emAtendimento) {
        this.emAtendimento = emAtendimento;
    }

    // Método para agravamento
    public void setNivelUrgencia(String nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

}

