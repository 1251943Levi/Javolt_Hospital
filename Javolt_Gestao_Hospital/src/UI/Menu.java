package UI;

// Importa a classe principal de gestão do hospital (camada de serviços)

import Serviços.GestaoHospital;

/**
 * Classe Menu
 * Responsável por toda a navegação da aplicação em consola.
 * Esta classe pertence à camada de Interface (UI) e não contém
 * lógica de negócio — apenas chama métodos do GestaoHospital.
 */
public class Menu {

    // Referência para a instância principal do sistema
    private GestaoHospital gestaoHospital;

    /**
     * Construtor do Menu.
     * Recebe a instância de GestaoHospital criada no Main
     * para que todos os menus trabalhem sobre o mesmo estado.
     */
    public Menu(GestaoHospital gestaoHospital) {
        this.gestaoHospital = gestaoHospital;
    }

    // ================= MENU PRINCIPAL =================

    /**
     * Método inicial da aplicação.
     * Mostra o cabeçalho e controla o ciclo principal do programa.
     */
    public void start() {
        InputsAuxiliares.limparTela();
        InputsAuxiliares.imprimirCabecalho("SISTEMA DE GESTÃO HOSPITALAR");
        System.out.println("|    Bem-vindo ao sistema Javolt Hospital    |");
        InputsAuxiliares.imprimirLinha();

        int opcao;

        // Ciclo principal da aplicação
        do {
            // Mostra o menu principal e lê a opção
            opcao = menuPrincipal();

            // Decide que submenu executar com base na opção escolhida
            switch (opcao) {
                case 1:
                    menuGestaoHospital();
                    break;
                case 2:
                    menuEstatisticas();
                    break;
                case 3:
                    menuConfiguracoes();
                    break;
                case 0:
                    System.out.println("A sair da aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    /**
     * Mostra o menu principal e devolve a opção escolhida.
     *
     * @return opção escolhida pelo utilizador
     */
    private int menuPrincipal() {
        InputsAuxiliares.imprimirCabecalho("MENU PRINCIPAL");
        System.out.println("|   1 - Gestão do Hospital                |");
        System.out.println("|   2 - Estatísticas                      |");
        System.out.println("|   3 - Configurações                     |");
        System.out.println("|   0 - Sair                              |");
        InputsAuxiliares.imprimirLinha();

        // Leitura da opção com validação de intervalo
        return InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 3);
    }

    // ================= GESTÃO DO HOSPITAL =================

    /**
     * Submenu responsável pelas operações do dia-a-dia do hospital:
     * registo de pacientes, avanço do tempo e listagens.
     */
    private void menuGestaoHospital() {
        int opcao;

        do {
            System.out.println("\n===== GESTÃO DO HOSPITAL =====");
            System.out.println("1 - Registar paciente");
            System.out.println("2 - Avançar tempo");
            System.out.println("3 - Listar médicos");
            System.out.println("4 - Listar pacientes");
            System.out.println("0 - Voltar");

            // Leitura simples da opção
            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1:
                    gestaoHospital.registarPaciente();
                    break;
                case 2:
                    gestaoHospital.avancarTempo();
                    break;
                case 3:
                    gestaoHospital.listarMedicos();
                    break;
                case 4:
                    gestaoHospital.listarPacientes();
                    break;
                case 0:
                    System.out.println("A voltar ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            // Pausa para o utilizador conseguir ler o output
            pausa();

        } while (opcao != 0);
    }

    // ================= ESTATÍSTICAS =================

    /**
     * Submenu responsável pela consulta de estatísticas do hospital.
     * Todos os cálculos são feitos na classe GestaoHospital.
     */
    private void menuEstatisticas() {
        int opcao;

        do {
            System.out.println("\n===== ESTATÍSTICAS =====");
            System.out.println("1 - Média de pacientes por dia");
            System.out.println("2 - Salários por médico");
            System.out.println("3 - Top especialidades");
            System.out.println("0 - Voltar");

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1:
                    gestaoHospital.mediaPacientesDia();
                    break;
                case 2:
                    gestaoHospital.tabelaSalarios();
                    break;
                case 3:
                    gestaoHospital.topEspecialidades();
                    break;
                case 0:
                    System.out.println("A voltar ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            pausa();

        } while (opcao != 0);
    }

    // ================= CONFIGURAÇÕES =================

    /**
     * Submenu responsável pelas configurações da aplicação.
     * Permite alterar valores não dinâmicos definidos no enunciado.
     */
    private void menuConfiguracoes() {
        int opcao;

        do {
            System.out.println("\n===== CONFIGURAÇÕES =====");
            System.out.println("1 - Alterar caminho ficheiros");
            System.out.println("2 - Alterar separador");
            System.out.println("3 - Alterar tempos de consulta");
            System.out.println("0 - Voltar");

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1:
                    gestaoHospital.alterarCaminhoFicheiros();
                    break;
                case 2:
                    gestaoHospital.alterarSeparador();
                    break;
                case 3:
                    gestaoHospital.alterarTemposConsulta();
                    break;
                case 0:
                    System.out.println("A voltar ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            pausa();

        } while (opcao != 0);
    }

    // ================= MÉTODOS AUXILIARES =================

    /**
     * Método auxiliar usado para pausar a execução do menu
     * até o utilizador pressionar ENTER.
     */
    private void pausa() {
        InputsAuxiliares.lerString("\nPrima ENTER para continuar...");
    }
}
