package UI;

import Entidades.Especialidade;
import Entidades.Sintoma;
import Serviços.GestaoHospital;

public class SubSintoma {

    public void menuSintomas(GestaoHospital gestaoHospital) {

        int opcao;

        do {
            System.out.println("\n=== GESTÃO DE SINTOMAS ===");
            System.out.println("1 - Adicionar sintoma");
            System.out.println("2 - Listar sintomas");
            System.out.println("3 - Atualizar sintoma");
            System.out.println("4 - Remover sintoma");
            System.out.println("0 - Voltar");

            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            switch (opcao) {
                case 1:
                    adicionarSintoma(gestaoHospital);
                    break;
                case 2:
                    gestaoHospital.listarSintomas();
                    break;
                case 3:
                    atualizarSintoma(gestaoHospital);
                    break;
                case 4:
                    removerSintoma(gestaoHospital);
                    break;
                case 0:
                    System.out.println("A voltar...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private void adicionarSintoma(GestaoHospital gestaoHospital) {

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma: ");

        int urgencia = InputsAuxiliares.lerInteiroIntervalo(
                "Nível urgência (1-Verde | 2-Laranja | 3-Vermelho): ", 1, 3);

        String codigoEsp = InputsAuxiliares.lerTexto("Código especialidade (ENTER se não existir): ");

        Especialidade esp = null;
        if (codigoEsp != null && codigoEsp.length() > 0) {
            esp = gestaoHospital.procurarEspecialidade(codigoEsp);
            if (esp == null) {
                InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
                return;
            }
        }

        String urgenciaTexto;

        if (urgencia == 1) {
            urgenciaTexto = "VERDE";
        } else if (urgencia == 2) {
            urgenciaTexto = "LARANJA";
        } else {
            urgenciaTexto = "VERMELHO";
        }

        Sintoma s = new Sintoma(nome, urgenciaTexto, esp);

        if (gestaoHospital.adicionarSintoma(s)) {
            InputsAuxiliares.imprimirSucesso("Sintoma adicionado com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao adicionar sintoma.");
        }
    }

    private void atualizarSintoma(GestaoHospital gestaoHospital) {

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma a atualizar: ");

        Sintoma existente = gestaoHospital.procurarSintoma(nome);
        if (existente == null) {
            InputsAuxiliares.imprimirErro("Sintoma não encontrado.");
            return;
        }

        int urgencia = InputsAuxiliares.lerInteiroIntervalo(
                "Novo nível urgência (1-Verde | 2-Laranja | 3-Vermelho): ", 1, 3);

        String codigoEsp = InputsAuxiliares.lerTexto("Novo código especialidade (ENTER se não existir): ");

        Especialidade esp = null;
        if (codigoEsp != null && codigoEsp.length() > 0) {
            esp = gestaoHospital.procurarEspecialidade(codigoEsp);
        }

        String urgenciaTexto;

        if (urgencia == 1) {
            urgenciaTexto = "VERDE";
        } else if (urgencia == 2) {
            urgenciaTexto = "LARANJA";
        } else {
            urgenciaTexto = "VERMELHO";
        }

        Sintoma atualizado = new Sintoma(nome, urgenciaTexto, esp);


        if (gestaoHospital.atualizarSintoma(nome, atualizado)) {
            InputsAuxiliares.imprimirSucesso("Sintoma atualizado com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao atualizar sintoma.");
        }
    }

    private void removerSintoma(GestaoHospital gestaoHospital) {

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma a remover: ");

        if (gestaoHospital.removerSintoma(nome)) {
            InputsAuxiliares.imprimirSucesso("Sintoma removido com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao remover sintoma.");
        }
    }
}
