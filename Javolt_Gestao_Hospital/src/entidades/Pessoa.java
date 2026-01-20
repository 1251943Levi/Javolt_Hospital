package entidades;

public abstract class Pessoa {
    private String nome;
    private int id;
    private static int proximoId = 1;

    public Pessoa() {
        this.id = proximoId++;
    }

    public Pessoa(String nome) {
        this();
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome;
    }
}