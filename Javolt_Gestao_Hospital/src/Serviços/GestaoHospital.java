package Serviços;

import Entidades.*;
import Ficheiros.*;
import UI.InputsAuxiliares;

public class GestaoHospital {

    // ================== ATRIBUTOS ==================
    private Medico[] medicos;
    private Paciente[] pacientes;
    private Consulta[] consultas;
    private Sintoma[] sintomas;
    private Especialidade[] especialidades;

    private LeitorFicheiros leitor;
    private GestorFicheiros gestor;

    private int totalMedicos;
    private int totalPacientes;
    private int totalConsultas;

    // ================== CONSTRUTOR ==================
    public GestaoHospital() {

        medicos = new Medico[100];
        pacientes = new Paciente[200];
        consultas = new Consulta[100];

        totalMedicos = 0;
        totalPacientes = 0;
        totalConsultas = 0;

        leitor = new LeitorFicheiros(";");
        gestor = new GestorFicheiros(";");

        especialidades = leitor.lerEspecialidades("especialidades.txt");
        medicos = leitor.lerMedicos("medicos.txt");
        sintomas = leitor.lerSintomas("sintomas.txt", especialidades);

        totalMedicos = 0;
        for (Medico m : medicos) {
            if (m != null) totalMedicos++;
        }

        gestor.escreverLog("logs.txt", "Sistema iniciado com sucesso");
    }

    // ================== MÉDICOS ==================
    public boolean adicionarMedico(Medico m) {
        if (totalMedicos >= medicos.length) return false;
        medicos[totalMedicos++] = m;
        return true;
    }

    public void listarMedicos() {
        if (totalMedicos == 0) {
            System.out.println("Não existem médicos registados.");
            return;
        }

        for (int i = 0; i < totalMedicos; i++) {
            System.out.println(medicos[i]);
        }
    }

    public Medico procurarMedicoPorEspecialidade(String especialidade) {
        for (int i = 0; i < totalMedicos; i++) {
            if (medicos[i].getEspecialidade().equalsIgnoreCase(especialidade)
                    && medicos[i].isDisponivel()) {
                return medicos[i];
            }
        }
        return null;
    }

    // ================== PACIENTES ==================
    public boolean adicionarPaciente(Paciente p) {
        if (totalPacientes >= pacientes.length) return false;
        pacientes[totalPacientes++] = p;
        return true;
    }

    public void listarPacientes() {
        if (totalPacientes == 0) {
            System.out.println("Não existem pacientes registados.");
            return;
        }

        for (int i = 0; i < totalPacientes; i++) {
            System.out.println(pacientes[i]);
        }
    }

    public void registarPaciente() {
        String nome = InputsAuxiliares.lerString("Nome do paciente: ");
        Paciente p = new Paciente(nome);

        if (adicionarPaciente(p)) {
            System.out.println("Paciente registado com sucesso.");
        } else {
            System.out.println("Lista de pacientes cheia.");
        }
    }

    // ================== CONSULTAS ==================
    public boolean criarConsulta(Medico medico, Paciente paciente, int tempoConsulta) {
        if (totalConsultas >= consultas.length) return false;

        Consulta c = new Consulta(medico, paciente, tempoConsulta);
        consultas[totalConsultas++] = c;

        medico.setDisponivel(false);
        paciente.setEmAtendimento(true);

        gestor.escreverLog("logs.txt",
                "Consulta iniciada: " + paciente.getNome() + " com Dr. " + medico.getNome());

        return true;
    }

    public void avancarTempo() {

        int unidades = InputsAuxiliares.lerInt("Quantas unidades de tempo avançar: ");

        for (int u = 0; u < unidades; u++) {
            avancarTempoInterno();
        }

        System.out.println("Tempo avançado com sucesso.");
    }

    private void avancarTempoInterno() {
        for (int i = 0; i < totalConsultas; i++) {
            consultas[i].avancarTempo();

            if (consultas[i].terminou()) {
                terminarConsulta(i);
                i--;
            }
        }
    }

    private void terminarConsulta(int indice) {
        Consulta c = consultas[indice];

        c.getMedico().setDisponivel(true);
        c.getPaciente().setEmAtendimento(false);

        gestor.escreverLog("logs.txt",
                "Consulta terminada: " + c.getPaciente().getNome() +
                        " com Dr. " + c.getMedico().getNome());

        for (int i = indice; i < totalConsultas - 1; i++) {
            consultas[i] = consultas[i + 1];
        }

        consultas[--totalConsultas] = null;
    }

    // ================== GETTERS ==================
    public int getTotalMedicos() {
        return totalMedicos;
    }

    public int getTotalPacientes() {
        return totalPacientes;
    }

    public int getTotalConsultas() {
        return totalConsultas;
    }
}


















/*
    // ==========================================================================================

    // confirmar as variaveis usadas
    public static int unidadeTempoAtual = 0; // O nosso relógio

    public static Medico[] listaMedicos = new Medico[50];
    public static int totalMedicos = 0; // Quantos médicos existem na lista
    public static Paciente[] filaEspera = new Paciente[100];
    public static int totalPacientes = 0; // Quantos pacientes estão na fila

    // confirmar configs do do aluno 4
    static int TEMPO_BAIXA = 1;
    static int TEMPO_MEDIA = 2;
    static int TEMPO_URGENTE = 3;
    static int TEMPO_PARA_SUBIR_NIVEL = 3;
    static int TRABALHO_PARA_DESCANSO = 5; // 5 horas seguidas
    static int TEMPO_DE_DESCANSO = 1;      // 1 hora de descanso

    public static void avancarUnidadeTempo() {
        // O relógio avança
        unidadeTempoAtual++;

        if (unidadeTempoAtual > 24) {
            unidadeTempoAtual = 1;
            System.out.println("-- Bom dia! Inicio de um novo dia. --");
        }

        System.out.println(" Hora Atual: " + unidadeTempoAtual + " ---");

        // Verificar se estamos em emergência (para decidir regras de descanso)
        boolean modoEmergencia = verificarSeEstamosEmEmergencia();

        // (Entradas, Saídas, Cansaço)
        atualizarMedicos(modoEmergencia);

        // Gerir Pacientes
        atualizarPacientes();

        // Atribuir Pacientes A Médicos Livres
        atribuirConsultas();
    }

    private static void atualizarMedicos(boolean modoEmergencia) {
        for (int i = 0; i < totalMedicos; i++) {
            Medico m = listaMedicos[i];

            // Verificar Entrada
            if (m.horaEntrada == unidadeTempoAtual) {
                m.disponivel = true;
                System.out.println("Entrada: Dr. " + m.nome + " iniciou o turno.");
            }

            // Se o médico está a descansar
            if (m.emDescanso) {
                m.tempoDescansoRestante--;
                if (m.tempoDescansoRestante <= 0) {
                    m.emDescanso = false;
                    m.disponivel = true;
                    m.horasSeguidasTrabalho = 0; // Reset total porque descansou
                    System.out.println("Dr. " + m.nome + " acabou o descanso e voltou.");
                }
                continue; // Passa ao próximo médico
            }

            // Se o médico esta em consulta
            if (m.ocupado) {
                m.horasSeguidasTrabalho++; // Acumular cnasaço
                m.tempoRestanteConsulta--; // A consulta avança

                if (m.tempoRestanteConsulta <= 0) {
                    m.ocupado = false;
                    System.out.println("Dr. " + m.nome + " acabou a consulta.");

                    // Regra Descanso a cada 5h
                    if (m.horasSeguidasTrabalho >= TRABALHO_PARA_DESCANSO) {
                        m.emDescanso = true;
                        m.disponivel = false;
                        m.tempoDescansoRestante = TEMPO_DE_DESCANSO;
                        System.out.println("! AVISO: Dr. " + m.nome + " está exausto. Vai descansar.");
                    } else {
                        m.disponivel = true; // Fica livre para o próximo
                    }
                }
            }
            // Se o medico está livre, descansa 1 hora
            else if (m.disponivel) {
                if (modoEmergencia) {
                    // Caso hospital esteja em estado de emergencia aproveita a pausa para "zerar" o cansaço
                    if (m.horasSeguidasTrabalho > 0) {
                        m.horasSeguidasTrabalho = 0;
                        System.out.println("Emergência: Dr. " + m.nome + " recuperou fôlego na pausa.");
                    }
                }
                // Se nao for emergência, descansar a cada 5 horas como normal
            }

            // Verificar Saída, turno acaba
            // Só sai se já não estiver ocupado. Se estiver ocupado, sai quando acabar a consulta
            if (m.horaSaida == unidadeTempoAtual && !m.ocupado) {
                m.disponivel = false;
                System.out.println("Saída: Dr. " + m.nome + " terminou o turno e foi para casa.");
            }
        }
    }

    private static void atualizarPacientes() {
        for (int i = 0; i < totalPacientes; i++) {
            Paciente p = filaEspera[i];
            p.tempoEspera++;

            // Regra: Baixa - Média
            if (p.nivelUrgencia.equals("Baixa") && p.tempoEspera >= TEMPO_PARA_SUBIR_NIVEL) {
                p.nivelUrgencia = "Media";
                p.tempoEspera = 0;
                System.out.println("AGRAVAMENTO: " + p.nome + " passou para MÉDIA.");
            }
            // Regra: Média - Urgente
            else if (p.nivelUrgencia.equals("Media") && p.tempoEspera >= TEMPO_PARA_SUBIR_NIVEL) {
                p.nivelUrgencia = "Urgente";
                System.out.println("AGRAVAMENTO: " + p.nome + " passou para URGENTE.");
            }
        }
    }

    private static void atribuirConsultas() {
        // Percorrer a fila de espera
        for (int i = 0; i < totalPacientes; i++) {
            Paciente p = filaEspera[i];

            // Procurar médico para este paciente
            for (int j = 0; j < totalMedicos; j++) {
                Medico m = listaMedicos[j];

                // Confirmar a especialidade correta e se esta disponivel e se nao esta em descanso

                if (m.disponivel && !m.ocupado && !m.emDescanso && m.especialidade.equals(p.especialidadeDesejada)) {

                    // Iniciar Consulta.java
                    m.ocupado = true;
                    m.tempoRestanteConsulta = obterTempoConsulta(p.nivelUrgencia);

                    System.out.println("Atendimento: Dr. " + m.nome + " chamou " + p.nome);

                    // Remover paciente da fila e ajustar array
                    removerPaciente(i);
                    i--; // Recuar o índice i porque a fila encolheu
                    totalPacientes--;

                    break; // Sai do loop dos médicos, vai para o próximo paciente
                }
            }
        }
    }

    // Definir se esta em estado de emergencia
    private static boolean verificarSeEstamosEmEmergencia() {
        int contaUrgentes = 0;
        for (int i = 0; i < totalPacientes; i++) {
            if (filaEspera[i].nivelUrgencia.equals("Urgente")) contaUrgentes++;
        }
        // Se houver mais de 10 pessoas ou 3 urgentes, ativa modo emergência
        if (totalPacientes > 10 || contaUrgentes >= 3) {
            System.out.println("!ESTADO DE EMERGÊNCIA ATIVO!");
            return true;
        }
        return false;
    }

    // Remove paciente do array e "empurra" os outros para trás (Shift Left)
    private static void removerPaciente(int posicaoParaRemover) {
        for (int k = posicaoParaRemover; k < totalPacientes - 1; k++) {
            filaEspera[k] = filaEspera[k + 1];
        }
        filaEspera[totalPacientes - 1] = null; // Limpa o último
    }

    // Calcula tempo com base na urgência
    private static int obterTempoConsulta(String urgencia) {
        return switch (urgencia) {
            case "Baixa" -> TEMPO_BAIXA;      // 1 un.
            case "Media" -> TEMPO_MEDIA;      // 2 un.
            case "Urgente" -> TEMPO_URGENTE;  // 3 un.
            default -> 1;
        };
    }

    /*Classe de dados
    public static class Entidades.Medico {
        String nome, especialidade;
        int horaEntrada, horaSaida;
        boolean disponivel = false;      // Está no hospital?
        boolean ocupado = false;         // Está a atender?
        boolean emDescanso = false;      // Está na pausa obrigatória?
        int horasSeguidasTrabalho = 0;   // Contador de cansaço
        int tempoRestanteConsulta = 0;   // Quanto falta para acabar consulta
        int tempoDescansoRestante = 0;   // Quanto falta para acabar descanso
    }*/

    /*public static class Entidades.Paciente {
        String nome, nivelUrgencia, especialidadeDesejada;
        int tempoEspera = 0;
    }*/


