package UI;

import Entidades.Especialidade;
import Entidades.Sintoma;
import Servicos.GestaoHospital;

public class SubSintoma {
    public void menuSintomas(GestaoHospital gestaoHospital) {
        int opcao;
        do {
            InputsAuxiliares.limparTela();
            InputsAuxiliares.imprimirCabecalho("GESTÃO DE SINTOMAS");
            System.out.println("|   1 - Adicionar sintoma                          |");
            System.out.println("|   2 - Listar sintomas                            |");
            System.out.println("|   3 - Atualizar sintoma                          |");
            System.out.println("|   4 - Remover sintoma                            |");
            System.out.println("|   0 - Voltar                                     |");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            switch (opcao) {
                case 1 -> adicionarSintoma(gestaoHospital);
                case 2 -> {
                    gestaoHospital.listarSintomas();
                    InputsAuxiliares.pausar();
                }
                case 3 -> atualizarSintoma(gestaoHospital);
                case 4 -> removerSintoma(gestaoHospital);
                case 0 -> System.out.println("<< A voltar...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void adicionarSintoma(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("ADICIONAR SINTOMA");
        InputsAuxiliares.exibirMsgCancelar();

        try {
            String nome = InputsAuxiliares.lerTextoComCancelamento("Nome do sintoma: ");

            System.out.println("\nNíveis de urgência:");
            System.out.println("1 - Verde (Baixa)");
            System.out.println("2 - Laranja (Média)");
            System.out.println("3 - Vermelho (Urgente)");

            int urgenciaOpcao = InputsAuxiliares.lerInteiroComCancelamento("Nível de urgência (1-3): ");
            if (urgenciaOpcao < 1 || urgenciaOpcao > 3) {
                InputsAuxiliares.imprimirErro("Valor fora do intervalo. A assumir 'Verde'");
                urgenciaOpcao = 1;
            }

            String urgenciaTexto = switch (urgenciaOpcao) {
                case 2 -> "Laranja";
                case 3 -> "Vermelho";
                default -> "Verde";
            };

            System.out.println("\nEspecialidade associada (Deixe vazio se não existir):");
            if (InputsAuxiliares.confirmar("Deseja ver a lista de especialidades?")) {
                System.out.println();
                gestaoHospital.listarEspecialidades();
                System.out.println();
            }

            String codigoEsp = InputsAuxiliares.lerTexto("Código da especialidade (0 para cancelar): ");

            if (codigoEsp.trim().equals("0")) {
                throw new IndexOutOfBoundsException();
            }

            Especialidade esp = null;
            if (!codigoEsp.isEmpty()) {
                esp = gestaoHospital.procurarEspecialidade(codigoEsp);
                if (esp == null) {
                    InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
                    InputsAuxiliares.pausar();
                    return;
                }
            }

            Sintoma s = new Sintoma(nome, urgenciaTexto, esp);

            if (gestaoHospital.adicionarSintoma(s)) {
                InputsAuxiliares.imprimirSucesso("Sintoma adicionado com sucesso!");
            } else {
                InputsAuxiliares.imprimirErro("Erro ao adicionar sintoma (nome duplicado ou capacidade máxima?).");
            }
            InputsAuxiliares.pausar();
        } catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operação cancelada pelo utilizador.");
            InputsAuxiliares.pausar();
        }
    }

    private void atualizarSintoma(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("ATUALIZAR SINTOMA");
        InputsAuxiliares.exibirMsgCancelar();

        try {
            if (InputsAuxiliares.confirmar("Deseja ver a lista de sintomas existentes?")) {
                System.out.println();
                gestaoHospital.listarSintomas();
                System.out.println();
            }

            String nome = InputsAuxiliares.lerTextoComCancelamento("Nome do sintoma a atualizar: ");
            Sintoma existente = gestaoHospital.procurarSintoma(nome);

            if (existente == null) {
                InputsAuxiliares.imprimirErro("Sintoma não encontrado.");
                InputsAuxiliares.pausar();
                return;
            }

            System.out.println("\n>> Sintoma encontrado: " + existente.getNome());
            System.out.println("Deixe em branco e prima ENTER para manter valor atual.");

            String novoNomeInput = InputsAuxiliares.lerTexto("Novo nome [" + existente.getNome() + "]: ");
            if (novoNomeInput.trim().equals("0")) {
                throw new InputsAuxiliares.OperacaoCanceladaException();
            }

            String novoNome;
            if (!novoNomeInput.isEmpty()) {
                novoNome = existente.getNome();
            } else {
                novoNome = novoNomeInput;
            }

            String marcaVerde = " ";
            String marcaLaranja = " ";
            String marcaVermelha = " ";

            if (existente.getNivelUrgencia().equalsIgnoreCase("Verde")) {
                marcaVerde = "✓";
            } else if (existente.getNivelUrgencia().equalsIgnoreCase("Laranja")) {
                marcaLaranja = "✓";
            } else {
                marcaVermelha = "✓";
            }

            System.out.println("\nNíveis de urgência:");
            System.out.println("1 - Verde (Baixa)      [" + marcaVerde + "]");
            System.out.println("2 - Laranja (Média)    [" + marcaLaranja + "]");
            System.out.println("3 - Vermelho (Urgente) [" + marcaVermelha + "]");

            String inputUrgencia = InputsAuxiliares.lerTexto("Novo nível (1-3, ENTER para manter): ");
            if (inputUrgencia.trim().equals("0")) {
                throw new InputsAuxiliares.OperacaoCanceladaException();
            }

            String urgenciaTexto = existente.getNivelUrgencia();
            if (!inputUrgencia.isEmpty()) {
                try {
                    int opcao = Integer.parseInt(inputUrgencia);
                    urgenciaTexto = switch (opcao) {
                        case 2 -> "Laranja";
                        case 3 -> "Vermelha";
                        default -> "Verde";
                    };
                } catch (NumberFormatException e) {
                    InputsAuxiliares.imprimirErro(">> Valor inválido. A manter anterior.");
                }
            }
            String nomeEspecialidadeAtual;
            if (existente.getEspecialidade() != null) {
                nomeEspecialidadeAtual = existente.getEspecialidade().getNome();
            } else {
                nomeEspecialidadeAtual = "Nenhuma";
            }
            System.out.println("\nEspecialidade atual: " + nomeEspecialidadeAtual);

            String codigoEsp = InputsAuxiliares.lerTexto("Novo cód. especialidade (ENTER=manter, 'none'=limpar): ");
            if (codigoEsp.trim().equals("0")) {
                throw new InputsAuxiliares.OperacaoCanceladaException();
            }

            Especialidade esp;

            if (codigoEsp.isEmpty()) {
                esp = existente.getEspecialidade();
            } else if (codigoEsp.equalsIgnoreCase("none")) {
                esp = null;
            } else {
                esp = gestaoHospital.procurarEspecialidade(codigoEsp);
                if (esp == null) {
                    InputsAuxiliares.imprimirErro("Especialidade não encontrada.A manter a anterior.");
                    esp = existente.getEspecialidade();
                }
            }

            Sintoma atualizado = new Sintoma(novoNome, urgenciaTexto, esp);

            if (gestaoHospital.atualizarSintoma(nome, atualizado)) {
                InputsAuxiliares.imprimirSucesso("Sintoma atualizado com sucesso.");
            } else {
                InputsAuxiliares.imprimirErro("Erro ao atualizar sintoma.");
            }
            InputsAuxiliares.pausar();
        } catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operação cancelada pelo utilizador.");
            InputsAuxiliares.pausar();
        }
    }

    private void removerSintoma(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("REMOVER SINTOMA");
        InputsAuxiliares.exibirMsgCancelar();

        try {
            if ( InputsAuxiliares.confirmar("Deseja ver a lista de sintomas existentes?")){
                System.out.println();
                gestaoHospital.listarSintomas();
                InputsAuxiliares.imprimirLinha();
                System.out.println();
            }

            String nome = InputsAuxiliares.lerTextoComCancelamento("Nome do sintoma a remover: ");

            if (!InputsAuxiliares.confirmar("Tem a certeza que deseja remover o sintoma '" + nome + "'?")) {
                System.out.println("Operação cancelada.");
                InputsAuxiliares.pausar();
                return;
            }

            if (gestaoHospital.removerSintoma(nome)) {
                InputsAuxiliares.imprimirSucesso("Sintoma removido com sucesso!");
            } else {
                InputsAuxiliares.imprimirErro("Sintoma não encontrado.");
            }
            InputsAuxiliares.pausar();
        } catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operação cancelada pelo utilizador.");
            InputsAuxiliares.pausar();
        }
    }
}