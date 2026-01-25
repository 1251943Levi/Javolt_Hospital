package UI;

import Entidades.Especialidade;
import Entidades.Medico;
import Servicos.GestaoHospital;

import javax.swing.*;

public class SubMedicos {
    public void menuMedicos(GestaoHospital gestaoHospital) {
        int opcao;
        do {
            InputsAuxiliares.limparTela();
            InputsAuxiliares.imprimirCabecalho("GESTÃO DE MÉDICOS");
            System.out.println("|   1 - Adicionar médico                           |");
            System.out.println("|   2 - Listar médicos                             |");
            System.out.println("|   3 - Atualizar médico                           |");
            System.out.println("|   4 - Remover médico                             |");
            System.out.println("|   0 - Voltar                                     |");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            switch (opcao) {
                case 1 -> adicionarMedico(gestaoHospital);
                case 2 -> {
                    gestaoHospital.listarMedicos();
                    InputsAuxiliares.pausar();
                }
                case 3 -> atualizarMedico(gestaoHospital);
                case 4 -> removerMedico(gestaoHospital);
                case 0 -> System.out.println("<< A voltar...");
                default -> InputsAuxiliares.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void adicionarMedico(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("ADICIONAR NOVO MÉDICO");
        InputsAuxiliares.exibirMsgCancelar();

        try {
            String nome = InputsAuxiliares.lerTextoComCancelamento("Nome do médico: ");
            if (InputsAuxiliares.confirmar("Deseja ver a lista de especialidades disponível?")) {
                System.out.println();
                gestaoHospital.listarEspecialidades();
                System.out.println();
            }

            String codigoEsp = InputsAuxiliares.lerTextoComCancelamento("Código da especialidade: ");
            Especialidade esp = gestaoHospital.procurarEspecialidade(codigoEsp);

            if (esp == null) {
                InputsAuxiliares.imprimirErro("Especialidade não encontrada com o código '" + codigoEsp + "'.");
                InputsAuxiliares.pausar();
                return;
            }

            int horaEntrada = InputsAuxiliares.lerInteiroComCancelamento("Hora de entrada (0-23): ");
            if (horaEntrada < 0 || horaEntrada > 23) {
                InputsAuxiliares.imprimirErro("Hora inválida. Hora de entrada deve ser entre 0 e 23");
                InputsAuxiliares.pausar();
                return;
            }

            int horaSaida = InputsAuxiliares.lerInteiroIntervalo("Hora de saída (0-23): ", 0, 23);
            if (horaSaida < 0 || horaSaida > 23) {
                InputsAuxiliares.imprimirErro("Hora inválida. Hora de saída deve ser entre 0 e 23");
                InputsAuxiliares.pausar();
                return;
            }

            if (horaSaida <= horaEntrada) {
                InputsAuxiliares.imprimirErro("Hora de saída deve ser posterior à hora de entrada.");
                InputsAuxiliares.pausar();
                return;
            }

            double valorHora = InputsAuxiliares.lerDoubleComCancelamento("Valor hora (€): ");
            if (valorHora <= 0) {
                InputsAuxiliares.imprimirErro("Valor hora deve ser positivo.");
                InputsAuxiliares.pausar();
                return;
            }

            Medico medico = new Medico(nome, esp.getNome(), horaEntrada, horaSaida, valorHora);

            if (gestaoHospital.adicionarMedico(medico)) {
                InputsAuxiliares.imprimirSucesso("Médico adicionado com sucesso!");
            } else {
                InputsAuxiliares.imprimirErro("Erro ao adicionar médico (capacidade máxima?).");
            }
            InputsAuxiliares.pausar();
        } catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operação cancelada pelo utilizador.");
            InputsAuxiliares.pausar();
        }
    }

    private void atualizarMedico(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("ATUALIZAR MÉDICO");
        InputsAuxiliares.exibirMsgCancelar();

        try {
            if (InputsAuxiliares.confirmar("Deseja ver a lista de médicos ecistentes?")) {
                System.out.println();
                gestaoHospital.listarMedicos();
                InputsAuxiliares.imprimirLinha();
                System.out.println();
            }
            String nome = InputsAuxiliares.lerTextoComCancelamento("Nome do médico a atualizar: ");
            Medico existente = gestaoHospital.procurarMedicoPorNome(nome);

            if (existente == null) {
                InputsAuxiliares.imprimirErro("Médico não encontrado.");
                InputsAuxiliares.pausar();
                return;
            }

            System.out.println("\n>> Médico encontrado: " + existente.getNome());
            System.out.println("Deixe em branco e prima ENTER para manter valor atual.");

            String novoNome = InputsAuxiliares.lerTexto("Novo nome [" + existente.getNome() + "]: ");
            if (novoNome.isEmpty()) novoNome = existente.getNome();

            String codigoEsp = InputsAuxiliares.lerTexto(
                    "Novo código especialidade [" + existente.getEspecialidade() + "]: ");

            Especialidade esp;
            if (codigoEsp.isEmpty()) {
                esp = gestaoHospital.procurarEspecialidade(existente.getEspecialidade());
            } else {
                esp = gestaoHospital.procurarEspecialidade(codigoEsp);
                if (esp == null) {
                    InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
                    InputsAuxiliares.pausar();
                    return;
                }
            }

            String inputEntrada = InputsAuxiliares.lerTexto("Nova hora entrada [" + existente.getHoraEntrada() + "]: ");
            int horaEntrada;
            if (inputEntrada.isEmpty()) {
                horaEntrada = existente.getHoraEntrada();
            } else {
                horaEntrada = Integer.parseInt(inputEntrada);
            }

            String inputSaida = InputsAuxiliares.lerTexto("Nova hora saída [" + existente.getHoraSaida() + "]: ");
            int horaSaida;
            if (inputSaida.isEmpty()) {
                horaSaida = existente.getHoraSaida();
            } else {
                horaSaida = Integer.parseInt(inputSaida);
            }

            String inputValor = InputsAuxiliares.lerTexto("Novo valor hora [" + existente.getValorHora() + "]: ");
            double valorHora;
            if (inputValor.isEmpty()) {
                valorHora = existente.getValorHora();
            } else {
                valorHora = Double.parseDouble(inputValor.replace(",", "."));
            }

            Medico atualizado = new Medico(novoNome, esp.getNome(), horaEntrada, horaSaida, valorHora);

            if (gestaoHospital.atualizarMedico(nome, atualizado)) {
                InputsAuxiliares.imprimirSucesso("Médico atualizado com sucesso!");
            } else {
                InputsAuxiliares.imprimirErro("Erro ao atualizar médico.");
            }
            InputsAuxiliares.pausar();
        } catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operação cancelada pelo utilizador.");
        } catch (NumberFormatException e) {
            InputsAuxiliares.imprimirErro("Formato de número inválido.");
            InputsAuxiliares.pausar();
        }
    }

    private void removerMedico(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("REMOVER MÉDICO");

        try {
            if (InputsAuxiliares.confirmar("Deseja ver a lista de médicos existentes?")) {
                System.out.println();
                gestaoHospital.listarMedicos();
                System.out.println();
            }

            String nome = InputsAuxiliares.lerTextoComCancelamento("Nome do médico a remover: ");

            if (!InputsAuxiliares.confirmar("Tem a certeza que deseja remover o médico '" + nome + "'?")) {
                System.out.println("Operação cancelada.");
                InputsAuxiliares.pausar();
                return;
            }

            if (gestaoHospital.removerMedico(nome)) {
                InputsAuxiliares.imprimirSucesso("Médico removido com sucesso!");
            } else {
                InputsAuxiliares.imprimirErro("Erro ao remover médico (não encontrado?).");
            }
            InputsAuxiliares.pausar();
        } catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operação cancelada pelo utilizador.");
        }
    }
}