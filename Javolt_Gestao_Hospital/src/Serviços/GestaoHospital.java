package Serviços;

// Importação das entidades do domínio

import Entidades.*;
import Ficheiros.GestorFicheiros;
import Ficheiros.LeitorFicheiros;

/**
 * Classe GestaoHospital
 *
 * Esta é a CLASSE CENTRAL do sistema.
 * Contém toda a lógica de negócio da aplicação:
 *  - gestão de médicos, pacientes, sintomas e especialidades
 *  - simulação do tempo
 *  - estatísticas
 *  - ligação com leitura e escrita em ficheiros
 *
 * A camada UI (menus) apenas chama métodos desta classe.
 */
public class GestaoHospital {

    // ================= ATRIBUTOS =================

    // Arrays para armazenamento dos dados
    private Medico[] medicos;
    private Paciente[] pacientes;
    private Sintoma[] sintomas;
    private Especialidade[] especialidades;
    private Consulta[] consultas;

    // Contadores associados aos arrays
    private int totalMedicos;
    private int totalPacientes;
    private int totalSintomas;
    private int totalEspecialidades;
    private int totalConsultas;

    // Classes responsáveis por ficheiros
    private GestorFicheiros gestorFicheiros;
    private LeitorFicheiros leitorFicheiros;

    // Controlo do tempo do hospital
    private int unidadeTempoAtual;
    private int diaAtual;

    // ================= CONSTRUTOR =================

    /**
     * Construtor da classe GestaoHospital.
     *
     * Inicializa os arrays, os contadores e
     * carrega os dados iniciais a partir dos ficheiros.
     */
    public GestaoHospital() {

        medicos = new Medico[100];
        pacientes = new Paciente[100];
        sintomas = new Sintoma[100];
        especialidades = new Especialidade[50];
        consultas = new Consulta[200];

        totalMedicos = 0;
        totalPacientes = 0;
        totalSintomas = 0;
        totalEspecialidades = 0;
        totalConsultas = 0;

        gestorFicheiros = new GestorFicheiros(";");
        leitorFicheiros = new LeitorFicheiros(";");

        unidadeTempoAtual = 0;
        diaAtual = 1;

        // Carregamento dos dados iniciais
        carregarDados();
    }

    // ================= CARREGAMENTO DE DADOS =================

    /**
     * Lê os ficheiros iniciais e carrega os dados para a memória.
     * Este método é chamado apenas no arranque da aplicação.
     */
    private void carregarDados() {
        // leitura de médicos, sintomas, especialidades, etc.
    }

    // ================= GESTÃO DE ESPECIALIDADES =================

    /**
     * Adiciona uma nova especialidade ao sistema.
     */
    public boolean adicionarEspecialidade(Especialidade e) {
        // valida duplicados e adiciona ao array
        return false;
    }

    /**
     * Lista todas as especialidades registadas.
     */
    public void listarEspecialidades() {
        // percorre o array e imprime
    }

    /**
     * Procura uma especialidade pelo código.
     */
    public Especialidade procurarEspecialidade(String codigo) {
        // pesquisa sequencial
        return null;
    }

    /**
     * Atualiza uma especialidade existente.
     */
    public boolean atualizarEspecialidade(String codigo, Especialidade atualizada) {
        return false;
    }

    /**
     * Remove uma especialidade do sistema.
     * É feito shift manual do array.
     */
    public boolean removerEspecialidade(String codigo) {
        return false;
    }

    // ================= GESTÃO DE MÉDICOS =================

    /**
     * Adiciona um médico ao sistema.
     */
    public boolean adicionarMedico(Medico medico) {
        return false;
    }

    /**
     * Procura um médico pelo nome.
     */
    public Medico procurarMedicoPorNome(String nome) {
        return null;
    }

    /**
     * Atualiza os dados de um médico.
     */
    public boolean atualizarMedico(String nome, Medico atualizado) {
        return false;
    }

    /**
     * Remove um médico do sistema.
     */
    public boolean removerMedico(String nome) {
        return false;
    }

    /**
     * Lista todos os médicos registados.
     */
    public void listarMedicos() {
        // impressão simples
    }

    // ================= GESTÃO DE SINTOMAS =================

    /**
     * Adiciona um novo sintoma.
     */
    public boolean adicionarSintoma(Sintoma s) {
        return false;
    }

    /**
     * Procura um sintoma pelo nome.
     */
    public Sintoma procurarSintoma(String nome) {
        return null;
    }

    /**
     * Atualiza um sintoma existente.
     */
    public boolean atualizarSintoma(String nome, Sintoma atualizado) {
        return false;
    }

    /**
     * Remove um sintoma do sistema.
     */
    public boolean removerSintoma(String nome) {
        return false;
    }

    /**
     * Lista todos os sintomas registados.
     */
    public void listarSintomas() {
        // impressão simples
    }

    // ================= GESTÃO DE PACIENTES =================

    /**
     * Regista um novo paciente no hospital.
     * Normalmente chamado a partir do menu de gestão.
     */
    public void registarPaciente() {
        // criação e adição de paciente
    }

    /**
     * Lista os pacientes registados.
     */
    public void listarPacientes() {
        // percorre o array de pacientes
    }

    // ================= TEMPO E FUNCIONAMENTO =================

    /**
     * Avança uma unidade de tempo no hospital.
     * Controla mudanças de estado e eventos.
     */
    public void avancarTempo() {
        // incrementa tempo e verifica eventos
    }

    // ================= ESTATÍSTICAS =================

    /**
     * Calcula e mostra a média de pacientes atendidos por dia.
     */
    public void mediaPacientesDia() {
    }

    /**
     * Mostra a tabela de salários por médico.
     */
    public void tabelaSalarios() {
    }

    /**
     * Mostra o top de especialidades.
     */
    public void topEspecialidades() {
    }

    // ================= PERSISTÊNCIA =================

    /**
     * Guarda todos os dados do sistema em ficheiros.
     * Deve ser chamado ao sair da aplicação.
     */
    public void guardarDados() {
        // escrita de médicos, pacientes, sintomas, etc.
    }
}
