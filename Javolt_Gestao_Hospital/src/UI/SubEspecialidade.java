package UI;

import Entidades.Especialidade;
import Serviços.GestaoHospital;

public class SubEspecialidade {

    public void menuEspecialidades(GestaoHospital gestaoHospital) {

        int opcao;

        do {
            System.out.println("\n=== GESTÃO DE ESPECIALIDADES ===");
            System.out.println("1 - Adicionar especialidade");
            System.out.println("2 - Listar especialidades");
            System.out.println("3 - Atualizar especialidade");
            System.out.println("4 - Remover especialidade");
            System.out.println("0 - Voltar");

            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            switch (opcao) {
                case 1 -> adicionarEspecialidade(gestaoHospital);
                case 2 -> gestaoHospital.listarEspecialidades();
                case 3 -> atualizarEspecialidade(gestaoHospital);
                case 4 -> removerEspecialidade(gestaoHospital);
                case 0 -> System.out.println("A voltar ao menu anterior...");
            }

        } while (opcao != 0);
    }

    // ================= ADICIONAR =================
    private void adicionarEspecialidade(GestaoHospital gestaoHospital) {

        String codigo = InputsAuxiliares.lerTextoNaoVazio("Código da especialidade: ");
        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome da especialidade: ");

        Especialidade e = new Especialidade(codigo, nome);

        if (gestaoHospital.adicionarEspecialidade(e)) {
            InputsAuxiliares.imprimirSucesso("Especialidade adicionada com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao adicionar especialidade.");
        }
    }

    // ================= ATUALIZAR =================
    private void atualizarEspecialidade(GestaoHospital gestaoHospital) {

        String codigo = InputsAuxiliares.lerTextoNaoVazio("Código da especialidade a atualizar: ");

        Especialidade existente = gestaoHospital.procurarEspecialidade(codigo);

        if (existente == null) {
            InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
            return;
        }

        String novoCodigo = InputsAuxiliares.lerTextoNaoVazio("Novo código: ");
        String novoNome = InputsAuxiliares.lerTextoNaoVazio("Novo nome: ");

        Especialidade atualizada = new Especialidade(novoCodigo, novoNome);

        if (gestaoHospital.atualizarEspecialidade(codigo, atualizada)) {
            InputsAuxiliares.imprimirSucesso("Especialidade atualizada com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao atualizar especialidade.");
        }
    }

    // ================= REMOVER =================
    private void removerEspecialidade(GestaoHospital gestaoHospital) {

        String codigo = InputsAuxiliares.lerTextoNaoVazio("Código da especialidade a remover: ");

        if (gestaoHospital.removerEspecialidade(codigo)) {
            InputsAuxiliares.imprimirSucesso("Especialidade removida com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao remover especialidade.");
        }
    }
}
