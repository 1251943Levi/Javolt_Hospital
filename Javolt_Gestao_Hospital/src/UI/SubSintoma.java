package UI;

// Importação das classes necessárias
// Sintoma e Especialidade pertencem às Entidades
// GestaoHospital pertence à camada de Serviços

import Entidades.Especialidade;
import Entidades.Sintoma;
import Serviços.GestaoHospital;

/**
 * Submenu responsável pela gestão de Sintomas.
 * Esta classe trata apenas da interação com o utilizador (UI),
 * delegando toda a lógica de negócio para a classe GestaoHospital.
 */
public class SubSintoma {

    /**
     * Mostra o menu de gestão de sintomas e trata as opções do utilizador.
     * Recebe a instância de GestaoHospital para poder chamar os métodos de negócio.
     */
    public void menuSintomas(GestaoHospital gestaoHospital) {

        int opcao;

        // Ciclo do menu: repete até o utilizador escolher a opção 0
        do {
            System.out.println("\n=== GESTÃO DE SINTOMAS ===");
            System.out.println("1 - Adicionar sintoma");
            System.out.println("2 - Listar sintomas");
            System.out.println("3 - Atualizar sintoma");
            System.out.println("4 - Remover sintoma");
            System.out.println("0 - Voltar");

            // Leitura da opção com validação de intervalo
            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            // Estrutura switch para tratar a opção escolhida
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

    /**
     * Método responsável por adicionar um novo sintoma.
     * Recolhe os dados através da UI e cria o objeto Sintoma.
     */
    private void adicionarSintoma(GestaoHospital gestaoHospital) {

        // Leitura do nome do sintoma (não pode ser vazio)
        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma: ");

        // Leitura do nível de urgência (1 a 3)
        int urgencia = InputsAuxiliares.lerInteiroIntervalo(
                "Nível urgência (1-Verde | 2-Laranja | 3-Vermelho): ", 1, 3);

        // Leitura opcional do código da especialidade
        String codigoEsp = InputsAuxiliares.lerTexto(
                "Código especialidade (ENTER se não existir): ");

        // Especialidade associada ao sintoma (pode ser null)
        Especialidade esp = null;

        // Se o utilizador introduzir um código, tenta procurar a especialidade
        if (codigoEsp != null && codigoEsp.length() > 0) {
            esp = gestaoHospital.procurarEspecialidade(codigoEsp);
            if (esp == null) {
                InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
                return;
            }
        }

        // Conversão da urgência de int para String (compatível com a classe Sintoma)
        String urgenciaTexto;
        if (urgencia == 1) {
            urgenciaTexto = "VERDE";
        } else if (urgencia == 2) {
            urgenciaTexto = "LARANJA";
        } else {
            urgenciaTexto = "VERMELHO";
        }

        // Criação do objeto Sintoma
        Sintoma s = new Sintoma(nome, urgenciaTexto, esp);

        // Chamada ao método de negócio para adicionar o sintoma
        if (gestaoHospital.adicionarSintoma(s)) {
            InputsAuxiliares.imprimirSucesso("Sintoma adicionado com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao adicionar sintoma.");
        }
    }

    /**
     * Método responsável por atualizar um sintoma existente.
     */
    private void atualizarSintoma(GestaoHospital gestaoHospital) {

        // Leitura do nome do sintoma a atualizar
        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma a atualizar: ");

        // Verificação se o sintoma existe
        Sintoma existente = gestaoHospital.procurarSintoma(nome);
        if (existente == null) {
            InputsAuxiliares.imprimirErro("Sintoma não encontrado.");
            return;
        }

        // Leitura do novo nível de urgência
        int urgencia = InputsAuxiliares.lerInteiroIntervalo(
                "Novo nível urgência (1-Verde | 2-Laranja | 3-Vermelho): ", 1, 3);

        // Leitura opcional do novo código da especialidade
        String codigoEsp = InputsAuxiliares.lerTexto(
                "Novo código especialidade (ENTER se não existir): ");

        Especialidade esp = null;
        if (codigoEsp != null && codigoEsp.length() > 0) {
            esp = gestaoHospital.procurarEspecialidade(codigoEsp);
        }

        // Conversão da urgência de int para String
        String urgenciaTexto;
        if (urgencia == 1) {
            urgenciaTexto = "VERDE";
        } else if (urgencia == 2) {
            urgenciaTexto = "LARANJA";
        } else {
            urgenciaTexto = "VERMELHO";
        }

        // Criação do novo objeto Sintoma atualizado
        Sintoma atualizado = new Sintoma(nome, urgenciaTexto, esp);

        // Atualização no GestaoHospital
        if (gestaoHospital.atualizarSintoma(nome, atualizado)) {
            InputsAuxiliares.imprimirSucesso("Sintoma atualizado com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao atualizar sintoma.");
        }
    }

    /**
     * Método responsável por remover um sintoma existente.
     */
    private void removerSintoma(GestaoHospital gestaoHospital) {

        // Leitura do nome do sintoma a remover
        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma a remover: ");

        // Pedido de remoção ao GestaoHospital
        if (gestaoHospital.removerSintoma(nome)) {
            InputsAuxiliares.imprimirSucesso("Sintoma removido com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao remover sintoma.");
        }
    }
}
