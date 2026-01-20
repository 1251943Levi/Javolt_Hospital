package UI;

import Serviços.GestaoHospital;
import UI.InputsAuxiliares;

public class Menu {

    private GestaoHospital gestaoHospital;

    public Menu(GestaoHospital gestaoHospital) {
        this.gestaoHospital = gestaoHospital;
    }

    // ================= MENU PRINCIPAL =================

    public void start() {
        int opcao;

        do {
            opcao = menuPrincipal();

            switch (opcao) {
                case 1 -> menuGestaoHospital();
                case 2 -> menuEstatisticas();
                case 3 -> menuConfiguracoes();
                case 0 -> System.out.println("A sair da aplicação...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private int menuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1 - Gestão do Hospital");
        System.out.println("2 - Estatísticas");
        System.out.println("3 - Configurações");
        System.out.println("0 - Sair");
        return InputsAuxiliares.lerInt("Opção: ");
    }

    // ================= GESTÃO HOSPITAL =================

    private void menuGestaoHospital() {
        int opcao;

        do {
            System.out.println("\n===== GESTÃO DO HOSPITAL =====");
            System.out.println("1 - Registar paciente");
            System.out.println("2 - Avançar tempo");
            System.out.println("3 - Listar médicos");
            System.out.println("4 - Listar pacientes");
            System.out.println("0 - Voltar");

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1 -> gestaoHospital.registarPaciente();
                case 2 -> gestaoHospital.avancarTempo();
                case 3 -> gestaoHospital.listarMedicos();
                case 4 -> gestaoHospital.listarPacientes();
                case 0 -> System.out.println("A voltar ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }

            pausa();

        } while (opcao != 0);
    }

    // ================= ESTATÍSTICAS =================

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
                case 1 -> gestaoHospital.mediaPacientesDia();
                case 2 -> gestaoHospital.tabelaSalarios();
                case 3 -> gestaoHospital.topEspecialidades();
                case 0 -> System.out.println("A voltar ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }

            pausa();

        } while (opcao != 0);
    }

    // ================= CONFIGURAÇÕES =================

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
                case 1 -> gestaoHospital.alterarCaminhoFicheiros();
                case 2 -> gestaoHospital.alterarSeparador();
                case 3 -> gestaoHospital.alterarTemposConsulta();
                case 0 -> System.out.println("A voltar ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }

            pausa();

        } while (opcao != 0);
    }

    // ================= MÉTODOS AUXILIARES =================

    private void pausa() {
        InputsAuxiliares.lerString("\nPrima ENTER para continuar...");
    }
}
