package Serviços;

import Entidades.*;
import Ficheiros.GestorFicheiros;
import Ficheiros.LeitorFicheiros;
import UI.InputsAuxiliares;

import java.io.File;

public class GestaoHospital {

    // ================== ATRIBUTOS ==================
    private Medico[] medicos;
    private Paciente[] pacientes;
    private Consulta[] consultas;
    private Sintoma[] sintomas;
    private Especialidade[] especialidades;

    // Configuração como objeto, não importar a classe
    private Configuracao configuracao;

    private LeitorFicheiros leitor;
    private GestorFicheiros gestor;

    private int totalMedicos;
    private int totalPacientes;
    private int totalConsultas;
    private int totalSintomas;
    private int totalEspecialidades;

    private int totalPacientesAtendidos = 0;
    private int diasDecorridos = 1;
    private int unidadeTempoAtual = 1;
    private final int UNIDADES_POR_DIA = 24;

    // ================== CONSTRUTOR ==================
    public GestaoHospital() {
        // Inicializar arrays
        medicos = new Medico[100];
        pacientes = new Paciente[200];
        consultas = new Consulta[100];
        sintomas = new Sintoma[50];
        especialidades = new Especialidade[20];

        // Inicializar contadores
        totalMedicos = 0;
        totalPacientes = 0;
        totalConsultas = 0;
        totalSintomas = 0;
        totalEspecialidades = 0;

        // Criar configuração (usando nome completo da classe)
        configuracao = new Entidades.Configuracao();

        // Inicializar leitor e gestor de ficheiros
        leitor = new LeitorFicheiros(String.valueOf(configuracao.getSeparador()));
        gestor = new GestorFicheiros(String.valueOf(configuracao.getSeparador()));

        carregarDadosIniciais();

        gestor.escreverLog("logs.txt", "Sistema iniciado com sucesso");
    }

    // ================== CARREGAMENTO DE DADOS ==================
    private void carregarDadosIniciais() {
        try {
            String caminho = configuracao.getCaminhoFicheiros();

            File dir = new File(caminho);
            if (!dir.exists()) {
                dir.mkdirs();
                InputsAuxiliares.imprimirAviso("Diretório de dados criado: " + caminho);
            }

            // Carregar especialidades
            Especialidade[] especialidadesLidas = leitor.lerEspecialidades(caminho + "especialidades.txt");
            if (especialidadesLidas != null) {
                for (Especialidade esp : especialidadesLidas) {
                    if (esp != null && totalEspecialidades < especialidades.length) {
                        especialidades[totalEspecialidades++] = esp;
                    }
                }
            }

            // Carregar médicos
            Medico[] medicosLidos = leitor.lerMedicos(caminho + "medicos.txt");
            if (medicosLidos != null) {
                for (Medico med : medicosLidos) {
                    if (med != null && totalMedicos < medicos.length) {
                        medicos[totalMedicos++] = med;
                    }
                }
            }

            // Carregar sintomas
            Sintoma[] sintomasLidos = leitor.lerSintomas(caminho + "sintomas.txt", especialidades);
            if (sintomasLidos != null) {
                for (Sintoma sint : sintomasLidos) {
                    if (sint != null && totalSintomas < sintomas.length) {
                        sintomas[totalSintomas++] = sint;
                    }
                }
            }

            InputsAuxiliares.imprimirSucesso("Dados carregados com sucesso!");

        } catch (Exception e) {
            InputsAuxiliares.imprimirErro("Erro ao carregar dados: " + e.getMessage());
            InputsAuxiliares.imprimirAviso("Continuando com dados vazios...");
        }
    }

    // ================== CONFIGURAÇÕES ==================
    public void alterarCaminhoFicheiros() {
        InputsAuxiliares.imprimirCabecalho("ALTERAR CAMINHO DOS FICHEIROS");

        String novoCaminho = InputsAuxiliares.lerTextoNaoVazio("Novo caminho (ex: dados/): ");

        if (!novoCaminho.endsWith("/") && !novoCaminho.endsWith("\\")) {
            novoCaminho += "/";
        }

        if (configuracao.verificarPassword(InputsAuxiliares.lerTexto("Password: "))) {
            configuracao.setCaminhoFicheiros(novoCaminho);

            // Recarregar dados com novo caminho
            leitor = new LeitorFicheiros(String.valueOf(configuracao.getSeparador()));
            carregarDadosIniciais();

            InputsAuxiliares.imprimirSucesso("Caminho alterado e dados recarregados!");
        } else {
            InputsAuxiliares.imprimirErro("Password incorreta!");
        }
    }

    public void alterarSeparador() {
        InputsAuxiliares.imprimirCabecalho("ALTERAR SEPARADOR");

        char novoSeparador = InputsAuxiliares.lerSeparador("Novo separador");

        if (configuracao.verificarPassword(InputsAuxiliares.lerTexto("Password: "))) {
            configuracao.setSeparador(novoSeparador);

            // Atualizar leitor e gestor
            leitor = new LeitorFicheiros(String.valueOf(novoSeparador));
            gestor = new GestorFicheiros(String.valueOf(novoSeparador));

            InputsAuxiliares.imprimirSucesso("Separador alterado para: " + novoSeparador);
        } else {
            InputsAuxiliares.imprimirErro("Password incorreta!");
        }
    }

    public void alterarTemposConsulta() {
        InputsAuxiliares.imprimirCabecalho("ALTERAR TEMPOS DE CONSULTA");

        if (!configuracao.verificarPassword(InputsAuxiliares.lerTexto("Password: "))) {
            InputsAuxiliares.imprimirErro("Password incorreta!");
            return;
        }

        System.out.println("\nTempos atuais:");
        System.out.println("Baixa: " + configuracao.getTempoConsultaBaixa() + " UT");
        System.out.println("Média: " + configuracao.getTempoConsultaMedia() + " UT");
        System.out.println("Urgente: " + configuracao.getTempoConsultaUrgente() + " UT");

        InputsAuxiliares.imprimirTitulo("Novos valores (0 para manter atual)");

        int tempoBaixa = InputsAuxiliares.lerInteiro("Tempo Baixa: ");
        if (tempoBaixa > 0) configuracao.setTempoConsultaBaixa(tempoBaixa);

        int tempoMedia = InputsAuxiliares.lerInteiro("Tempo Média: ");
        if (tempoMedia > 0) configuracao.setTempoConsultaMedia(tempoMedia);

        int tempoUrgente = InputsAuxiliares.lerInteiro("Tempo Urgente: ");
        if (tempoUrgente > 0) configuracao.setTempoConsultaUrgente(tempoUrgente);

        // Tempos de agravamento
        int baixaParaMedia = InputsAuxiliares.lerInteiro("Tempo Baixa->Média: ");
        if (baixaParaMedia > 0) configuracao.setTempoBaixaParaMedia(baixaParaMedia);

        int mediaParaUrgente = InputsAuxiliares.lerInteiro("Tempo Média->Urgente: ");
        if (mediaParaUrgente > 0) configuracao.setTempoMediaParaUrgente(mediaParaUrgente);

        int urgenteParaSaida = InputsAuxiliares.lerInteiro("Tempo Urgente->Saída: ");
        if (urgenteParaSaida > 0) configuracao.setTempoUrgenteParaSaida(urgenteParaSaida);

        InputsAuxiliares.imprimirSucesso("Tempos de consulta atualizados!");
    }

    // ================== ESTATÍSTICAS ==================
    public void mediaPacientesDia() {
        InputsAuxiliares.imprimirCabecalho("MÉDIA DE PACIENTES POR DIA");

        if (diasDecorridos == 1) {
            System.out.println("Ainda não há dados suficientes (1º dia em andamento)");
            return;
        }

        double media = (double) totalPacientesAtendidos / (diasDecorridos - 1);
        System.out.printf("Média de pacientes atendidos por dia: %.2f%n", media);
        System.out.println("Total de pacientes atendidos: " + totalPacientesAtendidos);
        System.out.println("Dias decorridos: " + (diasDecorridos - 1));
    }

    public void tabelaSalarios() {
        InputsAuxiliares.imprimirCabecalho("SALÁRIOS POR MÉDICO");

        if (totalMedicos == 0) {
            System.out.println("Não há médicos registados.");
            return;
        }

        double totalSalarios = 0;
        System.out.printf("%-20s %-15s %-15s %-15s%n",
                "MÉDICO", "HORAS TRABALHO", "VALOR/HORA", "SALÁRIO");
        System.out.println("-".repeat(65));

        for (int i = 0; i < totalMedicos; i++) {
            Medico medico = medicos[i];
            if (medico != null) {
                double horasTrabalhadas = 8.0 * diasDecorridos; // 8h por dia
                double salario = horasTrabalhadas * medico.getValorHora();
                totalSalarios += salario;

                System.out.printf("%-20s %-15.1f %-15.2f € %-15.2f €%n",
                        medico.getNome(),
                        horasTrabalhadas,
                        medico.getValorHora(),
                        salario);
            }
        }

        System.out.println("-".repeat(65));
        System.out.printf("TOTAL GERAL: %.2f €%n", totalSalarios);
    }

    public void topEspecialidades() {
        InputsAuxiliares.imprimirCabecalho("TOP ESPECIALIDADES");

        if (totalEspecialidades == 0) {
            System.out.println("Não há especialidades carregadas.");
            return;
        }

        int[] contagem = new int[totalEspecialidades];

        // Contar pacientes por especialidade
        for (int i = 0; i < totalPacientes; i++) {
            if (pacientes[i] != null) {
                String espPaciente = pacientes[i].getEspecialidadeDesejada();
                if (espPaciente != null) {
                    for (int j = 0; j < totalEspecialidades; j++) {
                        if (especialidades[j] != null &&
                                especialidades[j].getNome().equalsIgnoreCase(espPaciente)) {
                            contagem[j]++;
                            break;
                        }
                    }
                }
            }
        }

        // Criar arrays temporários para ordenação
        Especialidade[] espOrdenadas = new Especialidade[totalEspecialidades];
        int[] contOrdenadas = new int[totalEspecialidades];

        // Copiar dados
        for (int i = 0; i < totalEspecialidades; i++) {
            espOrdenadas[i] = especialidades[i];
            contOrdenadas[i] = contagem[i];
        }

        // Ordenar por contagem (bubble sort)
        for (int i = 0; i < totalEspecialidades - 1; i++) {
            for (int j = 0; j < totalEspecialidades - i - 1; j++) {
                if (contOrdenadas[j] < contOrdenadas[j + 1]) {
                    // Trocar contagens
                    int tempCont = contOrdenadas[j];
                    contOrdenadas[j] = contOrdenadas[j + 1];
                    contOrdenadas[j + 1] = tempCont;

                    // Trocar especialidades
                    Especialidade tempEsp = espOrdenadas[j];
                    espOrdenadas[j] = espOrdenadas[j + 1];
                    espOrdenadas[j + 1] = tempEsp;
                }
            }
        }

        // Calcular total de pacientes com especialidade
        int totalPacientesComEspecialidade = 0;
        for (int count : contagem) {
            totalPacientesComEspecialidade += count;
        }

        // Mostrar top 3
        System.out.println("ESPECIALIDADES MAIS SOLICITADAS:");
        System.out.println("-".repeat(40));

        int limite = Math.min(3, totalEspecialidades);
        for (int i = 0; i < limite; i++) {
            if (espOrdenadas[i] != null) {
                double percentagem = totalPacientesComEspecialidade > 0 ?
                        (contOrdenadas[i] * 100.0) / totalPacientesComEspecialidade : 0;

                System.out.printf("%dº %-20s: %d pacientes (%.1f%%)%n",
                        i + 1,
                        espOrdenadas[i].getNome(),
                        contOrdenadas[i],
                        percentagem);
            }
        }
    }

    // ================== GESTÃO DE MÉDICOS ==================
    public boolean adicionarMedico(Medico m) {
        if (totalMedicos >= medicos.length) {
            return false;
        }
        medicos[totalMedicos++] = m;
        return true;
    }

    public void listarMedicos() {
        InputsAuxiliares.imprimirCabecalho("LISTA DE MÉDICOS");

        if (totalMedicos == 0) {
            System.out.println("Não existem médicos registados.");
            return;
        }

        for (int i = 0; i < totalMedicos; i++) {
            Medico medico = medicos[i];
            System.out.printf("%d. %-20s | %-15s | %02d:00-%02d:00 | %.2f€/h | %s%n",
                    i + 1,
                    medico.getNome(),
                    medico.getEspecialidade(),
                    medico.getHoraEntrada(),
                    medico.getHoraSaida(),
                    medico.getValorHora(),
                    medico.isDisponivel() ? "DISPONÍVEL" : "OCUPADO");
        }
    }

    public boolean atualizarMedico(String nome, Medico medicoAtualizado) {

        for (int i = 0; i < totalMedicos; i++) {
            if (medicos[i].getNome().equalsIgnoreCase(nome)) {
                medicos[i] = medicoAtualizado;
                return true;
            }
        }

        System.out.println("Médico não encontrado.");
        return false;
    }

    public boolean removerMedico(String nome) {

        for (int i = 0; i < totalMedicos; i++) {

            if (medicos[i].getNome().equalsIgnoreCase(nome)) {

                // deslocar os elementos
                for (int j = i; j < totalMedicos - 1; j++) {
                    medicos[j] = medicos[j + 1];
                }

                medicos[totalMedicos - 1] = null;
                totalMedicos--;

                return true;
            }
        }

        System.out.println("Médico não encontrado.");
        return false;
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

    // ================== GESTÃO DE ESPECIALIDADES ==================
    public boolean adicionarEspecialidade(Especialidade e) {

        if (totalEspecialidades >= especialidades.length) {
            InputsAuxiliares.imprimirErro("Limite de especialidades atingido.");
            return false;
        }

        if (procurarEspecialidade(e.getCodigo()) != null) {
            InputsAuxiliares.imprimirErro("Já existe uma especialidade com esse código.");
            return false;
        }

        especialidades[totalEspecialidades++] = e;
        gestor.escreverLog("logs.txt", "Especialidade adicionada: " + e.getNome());
        return true;
    }

    public void listarEspecialidades() {

        InputsAuxiliares.imprimirCabecalho("LISTA DE ESPECIALIDADES");

        if (totalEspecialidades == 0) {
            System.out.println("Não existem especialidades registadas.");
            return;
        }

        for (int i = 0; i < totalEspecialidades; i++) {
            Especialidade e = especialidades[i];
            System.out.printf("%d. [%s] %s%n",
                    i + 1,
                    e.getCodigo(),
                    e.getNome());
        }
    }

    public Especialidade procurarEspecialidade(String codigo) {

        for (int i = 0; i < totalEspecialidades; i++) {
            if (especialidades[i].getCodigo().equalsIgnoreCase(codigo)) {
                return especialidades[i];
            }
        }

        return null;
    }

    public boolean atualizarEspecialidade(String codigo, Especialidade atualizada) {

        for (int i = 0; i < totalEspecialidades; i++) {
            if (especialidades[i].getCodigo().equalsIgnoreCase(codigo)) {
                especialidades[i] = atualizada;
                gestor.escreverLog("logs.txt", "Especialidade atualizada: " + atualizada.getNome());
                return true;
            }
        }

        InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
        return false;
    }

    public boolean removerEspecialidade(String codigo) {

        for (int i = 0; i < totalEspecialidades; i++) {

            if (especialidades[i].getCodigo().equalsIgnoreCase(codigo)) {

                for (int j = i; j < totalEspecialidades - 1; j++) {
                    especialidades[j] = especialidades[j + 1];
                }

                especialidades[--totalEspecialidades] = null;
                gestor.escreverLog("logs.txt", "Especialidade removida: " + codigo);
                return true;
            }
        }

        InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
        return false;
    }
// ================== GESTÃO DE SISTOMA ==================
public boolean adicionarSintoma(Sintoma s) {

    if (totalSintomas >= sintomas.length) {
        return false;
    }

    if (procurarSintoma(s.getNome()) != null) {
        return false;
    }

    sintomas[totalSintomas++] = s;
    gestor.escreverLog("logs.txt", "Sintoma adicionado: " + s.getNome());
    return true;
}

    public void listarSintomas() {

        InputsAuxiliares.imprimirCabecalho("LISTA DE SINTOMAS");

        if (totalSintomas == 0) {
            System.out.println("Não existem sintomas registados.");
            return;
        }

        for (int i = 0; i < totalSintomas; i++) {
            Sintoma s = sintomas[i];
            System.out.printf("%d. %-20s | Urgência: %d | Especialidade: %s%n",
                    i + 1,
                    s.getNome(),
                    s.getUrgencia(),
                    (s.getEspecialidade() != null ? s.getEspecialidade().getNome() : "N/A"));
        }
    }

    public Sintoma procurarSintoma(String nome) {

        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(nome)) {
                return sintomas[i];
            }
        }
        return null;
    }

    public boolean atualizarSintoma(String nome, Sintoma atualizado) {

        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(nome)) {
                sintomas[i] = atualizado;
                gestor.escreverLog("logs.txt", "Sintoma atualizado: " + nome);
                return true;
            }
        }
        return false;
    }

    public boolean removerSintoma(String nome) {

        for (int i = 0; i < totalSintomas; i++) {

            if (sintomas[i].getNome().equalsIgnoreCase(nome)) {

                for (int j = i; j < totalSintomas - 1; j++) {
                    sintomas[j] = sintomas[j + 1];
                }

                sintomas[--totalSintomas] = null;
                gestor.escreverLog("logs.txt", "Sintoma removido: " + nome);
                return true;
            }
        }
        return false;
    }

    // ================== GESTÃO DE PACIENTES ==================
    public boolean adicionarPaciente(Paciente p) {
        if (totalPacientes >= pacientes.length) {
            return false;
        }
        pacientes[totalPacientes++] = p;
        return true;
    }

    public void listarPacientes() {
        InputsAuxiliares.imprimirCabecalho("LISTA DE PACIENTES");

        if (totalPacientes == 0) {
            System.out.println("Não existem pacientes registados.");
            return;
        }

        System.out.printf("%-3s %-20s %-12s %-15s %-10s %-15s%n",
                "#", "NOME", "URGÊNCIA", "ESPECIALIDADE", "ESPERA", "ESTADO");
        System.out.println("-".repeat(85));

        for (int i = 0; i < totalPacientes; i++) {
            Paciente p = pacientes[i];
            System.out.printf("%-3d %-20s %-12s %-15s %-10d %-15s%n",
                    i + 1,
                    p.getNome(),
                    p.getNivelUrgencia(),
                    p.getEspecialidadeDesejada() != null ? p.getEspecialidadeDesejada() : "N/D",
                    p.getTempoEspera(),
                    p.isEmAtendimento() ? "EM ATENDIMENTO" : "EM ESPERA");
        }
    }

    public void registarPaciente() {
        InputsAuxiliares.imprimirCabecalho("REGISTAR PACIENTE");

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do paciente: ");

        // Solicitar sintomas simples (versão simplificada)
        System.out.println("\nSintomas disponíveis:");
        for (int i = 0; i < totalSintomas && i < 10; i++) {
            if (sintomas[i] != null) {
                System.out.println((i + 1) + ". " + sintomas[i].getNome());
            }
        }

        // Calcular urgência e especialidade (simplificado)
        String nivelUrgencia = "Média"; // Exemplo - implementar lógica real
        String especialidade = "Clínica Geral"; // Exemplo

        Paciente p = new Paciente(nome, nivelUrgencia, especialidade);

        if (adicionarPaciente(p)) {
            InputsAuxiliares.imprimirSucesso("Paciente registado com sucesso.");
            System.out.println("Nível de urgência: " + nivelUrgencia);
            System.out.println("Especialidade atribuída: " + especialidade);
        } else {
            InputsAuxiliares.imprimirErro("Lista de pacientes cheia.");
        }
    }

    // ================== GESTÃO DE CONSULTAS ==================
    public boolean criarConsulta(Medico medico, Paciente paciente, int tempoConsulta) {
        if (totalConsultas >= consultas.length) {
            return false;
        }

        Consulta c = new Consulta(medico, paciente, tempoConsulta);
        consultas[totalConsultas++] = c;

        // Atualizar estados
        medico.setDisponivel(false);
        paciente.setEmAtendimento(true);

        gestor.escreverLog("logs.txt",
                "Consulta iniciada: " + paciente.getNome() + " com Dr. " + medico.getNome());

        return true;
    }

    private void terminarConsulta(int indice) {
        if (indice < 0 || indice >= totalConsultas) {
            return;
        }

        Consulta c = consultas[indice];

        // Liberar médico e paciente
        c.getMedico().setDisponivel(true);
        c.getPaciente().setEmAtendimento(false);

        // Registrar atendimento
        totalPacientesAtendidos++;

        // Log
        gestor.escreverLog("logs.txt",
                "Consulta terminada: " + c.getPaciente().getNome() +
                        " com Dr. " + c.getMedico().getNome());

        // Remover consulta do array
        for (int i = indice; i < totalConsultas - 1; i++) {
            consultas[i] = consultas[i + 1];
        }

        consultas[--totalConsultas] = null;
    }

    // ================== LÓGICA DE AVANÇO DE TEMPO ==================
    public void avancarTempo() {
        InputsAuxiliares.imprimirCabecalho("AVANÇAR TEMPO");

        // Verificar se é novo dia
        if (unidadeTempoAtual >= UNIDADES_POR_DIA) {
            unidadeTempoAtual = 1;
            diasDecorridos++;
            System.out.println(">>> NOVO DIA INICIADO: Dia " + diasDecorridos + " <<<");
        } else {
            unidadeTempoAtual++;
        }

        System.out.println("Tempo atual: " + unidadeTempoAtual + " UT");
        System.out.println("Dia: " + diasDecorridos);

        // 1. Avançar tempo nas consultas
        avancarConsultas();

        // 2. Atualizar tempo de espera dos pacientes
        atualizarEsperaPacientes();

        // 3. Verificar horários dos médicos
        verificarHorariosMedicos();

        // 4. Tentar alocar pacientes automaticamente
        alocarPacientesAutomaticamente();

        gestor.escreverLog("logs.txt", "Tempo avançado para: " + unidadeTempoAtual + " UT");
    }

    private void avancarConsultas() {
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i] != null) {
                consultas[i].avancarTempo();

                // Verificar se consulta terminou
                if (consultas[i].terminou()) {
                    System.out.println(">>> Consulta terminada: " +
                            consultas[i].getPaciente().getNome() + " <<<");
                    terminarConsulta(i);
                    i--; // Ajustar índice após remoção
                }
            }
        }
    }

    private void atualizarEsperaPacientes() {
        for (int i = 0; i < totalPacientes; i++) {
            if (pacientes[i] != null && !pacientes[i].isEmAtendimento()) {
                pacientes[i].incrementarTempoEspera();
                verificarAgravamento(pacientes[i]);
            }
        }
    }

    private void verificarAgravamento(Paciente paciente) {
        int tempoEspera = paciente.getTempoEspera();
        String nivelAtual = paciente.getNivelUrgencia();

        if (nivelAtual.equals("Baixa") && tempoEspera >= configuracao.getTempoBaixaParaMedia()) {
            paciente.setNivelUrgencia("Média");
            System.out.println(">>> PACIENTE AGRAVADO: " + paciente.getNome() +
                    " (Baixa -> Média) <<<");
        } else if (nivelAtual.equals("Média") && tempoEspera >= configuracao.getTempoMediaParaUrgente()) {
            paciente.setNivelUrgencia("Urgente");
            System.out.println(">>> PACIENTE AGRAVADO: " + paciente.getNome() +
                    " (Média -> Urgente) <<<");
        } else if (nivelAtual.equals("Urgente") && tempoEspera >= configuracao.getTempoUrgenteParaSaida()) {
            System.out.println(">>> ALERTA: Paciente " + paciente.getNome() +
                    " espera há " + tempoEspera + " UT com urgência máxima! <<<");
        }
    }

    private void verificarHorariosMedicos() {
        for (int i = 0; i < totalMedicos; i++) {
            Medico medico = medicos[i];

            // Verificar se está no horário de trabalho
            boolean noHorario = (unidadeTempoAtual >= medico.getHoraEntrada() &&
                    unidadeTempoAtual < medico.getHoraSaida());

            if (!noHorario && medico.isDisponivel()) {
                System.out.println(">>> Médico " + medico.getNome() + " fora do horário <<<");
            }
        }
    }

    private void alocarPacientesAutomaticamente() {
        for (int i = 0; i < totalPacientes; i++) {
            Paciente paciente = pacientes[i];

            if (paciente != null &&
                    !paciente.isEmAtendimento() &&
                    paciente.getEspecialidadeDesejada() != null) {

                Medico medico = procurarMedicoPorEspecialidade(paciente.getEspecialidadeDesejada());

                if (medico != null && medico.isDisponivel()) {
                    int tempoConsulta = calcularTempoConsulta(paciente.getNivelUrgencia());
                    if (criarConsulta(medico, paciente, tempoConsulta)) {
                        System.out.println(">>> Paciente " + paciente.getNome() +
                                " alocado para Dr. " + medico.getNome() + " <<<");
                    }
                }
            }
        }
    }

    private int calcularTempoConsulta(String nivelUrgencia) {
        if (nivelUrgencia == null) {
            return configuracao.getTempoConsultaBaixa();
        }

        switch (nivelUrgencia) {
            case "Baixa":
                return configuracao.getTempoConsultaBaixa();
            case "Média":
                return configuracao.getTempoConsultaMedia();
            case "Urgente":
                return configuracao.getTempoConsultaUrgente();
            default:
                return configuracao.getTempoConsultaBaixa();
        }
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

    public int getUnidadeTempoAtual() {
        return unidadeTempoAtual;
    }

    public int getDiasDecorridos() {
        return diasDecorridos;
    }

    public Entidades.Configuracao getConfiguracao() {
        return configuracao;
    }

    // ================== MÉTODOS AUXILIARES ==================
    public void mostrarEstadoSistema() {
        InputsAuxiliares.imprimirCabecalho("ESTADO DO SISTEMA");
        System.out.println("Dia: " + diasDecorridos);
        System.out.println("Hora: " + unidadeTempoAtual + " UT");
        System.out.println("Médicos registados: " + totalMedicos);
        System.out.println("Pacientes em espera: " + (totalPacientes - contarPacientesEmAtendimento()));
        System.out.println("Pacientes em atendimento: " + contarPacientesEmAtendimento());
        System.out.println("Consultas em andamento: " + totalConsultas);
        System.out.println("Total atendidos: " + totalPacientesAtendidos);
    }

    private int contarPacientesEmAtendimento() {
        int count = 0;
        for (int i = 0; i < totalPacientes; i++) {
            if (pacientes[i] != null && pacientes[i].isEmAtendimento()) {
                count++;
            }
        }
        return count;
    }
}