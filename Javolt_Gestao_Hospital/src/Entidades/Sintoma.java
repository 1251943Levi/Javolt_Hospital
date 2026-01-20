package Entidades;

public class Sintoma {

    private String nome;
    private String nivelUrgencia;      // Verde, Laranja, Vermelha
    private Especialidade especialidade; // pode ser null

    public Sintoma(String nome, String nivelUrgencia, Especialidade especialidade) {
        this.nome = nome;
        this.nivelUrgencia = nivelUrgencia;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public String getNivelUrgencia() {
        return nivelUrgencia;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    @Override
    public String toString() {
        return nome + " (" + nivelUrgencia + ")" +
                (especialidade != null ? " -> " + especialidade.getNome() : "");
    }
}
