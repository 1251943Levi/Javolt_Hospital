package entidades;

public class Especialidade {
    private String codigo;
    private String nome;
    private Sintoma[] sintomasRelacionados;
    private int totalSintomas;
    private int contadorPacientes;

    public Especialidade() {
        this.sintomasRelacionados = new Sintoma[50];
        this.totalSintomas = 0;
        this.contadorPacientes = 0;
    }

    public Especialidade(String codigo, String nome) {
        this();
        this.codigo = codigo;
        this.nome = nome;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getContadorPacientes() {
        return contadorPacientes;
    }

    public void incrementarContador() {
        contadorPacientes++;
    }

    // Métodos
    public void adicionarSintoma(Sintoma sintoma) {
        if (totalSintomas < sintomasRelacionados.length) {
            sintomasRelacionados[totalSintomas] = sintoma;
            totalSintomas++;
        }
    }

    public boolean contemSintoma(String nomeSintoma) {
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomasRelacionados[i].getNome().equalsIgnoreCase(nomeSintoma)) {
                return true;
            }
        }
        return false;
    }

    public Sintoma getSintoma(String nomeSintoma) {
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomasRelacionados[i].getNome().equalsIgnoreCase(nomeSintoma)) {
                return sintomasRelacionados[i];
            }
        }
        return null;
    }

    public String listarSintomas() {
        if (totalSintomas == 0) return "Nenhum sintoma associado";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalSintomas; i++) {
            sb.append("\n  - ").append(sintomasRelacionados[i].toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + contadorPacientes + " pacientes)";
    }
}
