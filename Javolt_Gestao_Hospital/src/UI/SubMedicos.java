package UI;

import Entidades.Medico;
import Serviços.GestaoHospital;

import java.util.Scanner;

public class SubMedicos {
    private Scanner sc = new Scanner(System.in);

    public void menuMedicos(GestaoHospital gestaoHospital) {

        int opcao;

        do {
            System.out.println("\n=== GESTÃO DE MÉDICOS ===");
            System.out.println("1 - Adicionar médico");
            System.out.println("2 - Listar médicos");
            System.out.println("3 - Atualizar médico");
            System.out.println("4 - Remover médico");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    adicionarMedico(gestaoHospital);
                    break;

                case 2:
                    gestaoHospital.listarMedicos();
                    break;

                case 3:
                    atualizarMedico(gestaoHospital);
                    break;

                case 4:
                    removerMedico(gestaoHospital);
                    break;

                case 0:
                    System.out.println("A voltar ao menu anterior...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private void adicionarMedico(GestaoHospital gestaoHospital) {

        System.out.print("Nome do médico: ");
        String nome = sc.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        System.out.print("Hora de entrada: ");
        int horaEntrada = lerInteiro();

        System.out.print("Hora de saída: ");
        int horaSaida = lerInteiro();

        System.out.print("Valor hora (€): ");
        double valorHora = lerDouble();

        Medico medico = new Medico(nome, especialidade, horaEntrada, horaSaida, valorHora);

        if (gestaoHospital.adicionarMedico(medico)) {
            System.out.println("Médico adicionado com sucesso.");
        } else {
            System.out.println("Erro ao adicionar médico.");
        }
    }

    private void atualizarMedico(GestaoHospital gestaoHospital) {

        System.out.print("Nome do médico a atualizar: ");
        String nome = sc.nextLine();

        Medico existente = gestaoHospital.procurarMedicoPorEspecialidade(nome);

        if (existente == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        String novoNome = sc.nextLine();

        System.out.print("Nova especialidade: ");
        String novaEspecialidade = sc.nextLine();

        System.out.print("Nova hora de entrada: ");
        int novaHoraEntrada = lerInteiro();

        System.out.print("Nova hora de saída: ");
        int novaHoraSaida = lerInteiro();

        System.out.print("Novo valor hora (€): ");
        double novoValorHora = lerDouble();

        Medico atualizado = new Medico(
                novoNome,
                novaEspecialidade,
                novaHoraEntrada,
                novaHoraSaida,
                novoValorHora
        );

        if (gestaoHospital.atualizarMedico(nome, atualizado)) {
            System.out.println("Médico atualizado com sucesso.");
        } else {
            System.out.println("Erro ao atualizar médico.");
        }
    }

    private void removerMedico(GestaoHospital gestaoHospital) {

        System.out.print("Nome do médico a remover: ");
        String nome = sc.nextLine();

        if (gestaoHospital.removerMedico(nome)) {
            System.out.println("Médico removido com sucesso.");
        } else {
            System.out.println("Erro ao remover médico.");
        }
    }
    // ================= Metodo auxiliar =================
    private int lerInteiro() {
        while (!sc.hasNextInt()) {
            System.out.print("Introduza um número inteiro válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); // limpar buffer
        return valor;
    }

    private double lerDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Introduza um número válido: ");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine(); // limpar buffer
        return valor;
    }
}

