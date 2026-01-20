package entidades;

public class Sintoma {
    private String nome;
    private String nivelUrgencia; // "Baixa", "Media", "Urgente"
    private String especialidadeAssociada; // Opcional

    public Sintoma() {
    }

    public Sintoma(String nome, String nivelUrgencia) {
        this.nome = nome;
        this.nivelUrgencia = nivelUrgencia;
    }

    public Sintoma(String nome, String nivelUrgencia, String especialidadeAssociada) {
        this.nome = nome;
        this.nivelUrgencia = nivelUrgencia;
        this.especialidadeAssociada = especialidadeAssociada;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivelUrgencia() {
        return nivelUrgencia;
    }

    public void setNivelUrgencia(String nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public String getEspecialidadeAssociada() {
        return especialidadeAssociada;
    }

    public void setEspecialidadeAssociada(String especialidadeAssociada) {
        this.especialidadeAssociada = especialidadeAssociada;
    }

    public String getNivelUrgenciaCor() {
        return switch (nivelUrgencia) {
            case "Baixa" -> "Verde";
            case "Media" -> "Laranja";
            case "Urgente" -> "Vermelha";
            default -> "Não definido";
        };
    }

    @Override
    public String toString() {
        return nome + " [" + getNivelUrgenciaCor() + "]" +
                (especialidadeAssociada != null ? " → " + especialidadeAssociada : "");
    }
}