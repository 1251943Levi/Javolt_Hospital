package UI;

// Importação das entidades e serviços necessários

import Entidades.Especialidade;
import Entidades.Medico;
import Serviços.GestaoHospital;

/**
 * Submenu responsável pela gestão de Médicos.
 * Esta classe pertence à camada de Interface (UI) e
 * apenas trata da interação com o utilizador.
 * Toda a lógica de negócio é delegada para a classe GestaoHospital.
 */
public class SubMedicos {

    /**
     * Mostra o menu de gestão de médicos e trata as opções escolhidas
     * pelo utilizador.
     *
     * @param gestaoHospital instância principal do sistema
     */
    public void menuMedicos(GestaoHospital gestaoHospital) {

        int opcao;

        // Ciclo do menu: repete até o utilizador escolher a opção 0
        do {
            System.out.println("\n=== GESTÃO DE MÉDICOS ===");
            System.out.println("1 - Adicionar médico");
            System.out.println("2 - Listar médicos");
            System.out.println("3 - Atualizar médico");
            System.out.println("4 - Remover médico");
            System.out.println("0 - Voltar");

            // Leitura da opção com validação de intervalo
            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            // Estrutura switch clássica (compatível com Java 8)
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
                    System.out.println("A voltar...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    /**
     * Método responsável por adicionar um novo médico.
     * Recolhe os dados através do utilizador e cria o objeto Medico.
     */
    private void adicionarMedico(GestaoHospital gestaoHospital) {

        // Leitura do nome do médico
        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do médico: ");

        // Leitura do código da especialidade
        String codigoEsp = InputsAuxiliares.lerTextoNaoVazio("Código da especialidade: ");

        // Procura da especialidade associada
        Especialidade esp = gestaoHospital.procurarEspecialidade(codigoEsp);
        if (esp == null) {
            InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
            return;
        }

        // Leitura da hora de entrada do médico
        int horaEntrada = InputsAuxiliares.lerInteiroIntervalo(
                "Hora de entrada: ", 0, 23);

        // Leitura da hora de saída do médico
        int horaSaida = InputsAuxiliares.lerInteiroIntervalo(
                "Hora de saída: ", 0, 23);

        // Leitura do valor por hora
        double valorHora = InputsAuxiliares.lerDouble("Valor hora (€): ");


        // Criação do objeto Medico
        Medico medico = new Medico(nome, esp.getNome(), horaEntrada, horaSaida, valorHora);

        // Pedido ao GestaoHospital para adicionar o médico
        if (gestaoHospital.adicionarMedico(medico)) {
            InputsAuxiliares.imprimirSucesso("Médico adicionado com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao adicionar médico.");
        }
    }

    /**
     * Método responsável por atualizar os dados de um médico existente.
     */
    private void atualizarMedico(GestaoHospital gestaoHospital) {

        // Leitura do nome do médico a atualizar
        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do médico a atualizar: ");

        // Verificação se o médico existe
        Medico existente = gestaoHospital.procurarMedicoPorEspecialidade(nome);
        if (existente == null) {
            InputsAuxiliares.imprimirErro("Médico não encontrado.");
            return;
        }

        // Leitura do novo código da especialidade
        String codigoEsp = InputsAuxiliares.lerTextoNaoVazio("Novo código da especialidade: ");

        Especialidade esp = gestaoHospital.procurarEspecialidade(codigoEsp);
        if (esp == null) {
            InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
            return;
        }

        // Leitura da nova hora de entrada
        int horaEntrada = InputsAuxiliares.lerInteiroIntervalo(
                "Nova hora de entrada: ", 0, 23);

        // Leitura da nova hora de saída
        int horaSaida = InputsAuxiliares.lerInteiroIntervalo(
                "Nova hora de saída: ", 0, 23);

        // Leitura do novo valor por hora
        double valorHora = InputsAuxiliares.lerDouble("Valor hora (€): ");


        // Criação de um novo objeto Medico com os dados atualizados
        Medico atualizado = new Medico(nome, esp.getNome(), horaEntrada, horaSaida, valorHora);

        // Pedido de atualização ao GestaoHospital
        if (gestaoHospital.atualizarMedico(nome, atualizado)) {
            InputsAuxiliares.imprimirSucesso("Médico atualizado com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao atualizar médico.");
        }
    }

    /**
     * Método responsável por remover um médico existente.
     */
    private void removerMedico(GestaoHospital gestaoHospital) {

        // Leitura do nome do médico a remover
        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do médico a remover: ");

        // Pedido de remoção ao GestaoHospital
        if (gestaoHospital.removerMedico(nome)) {
            InputsAuxiliares.imprimirSucesso("Médico removido com sucesso.");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao remover médico.");
        }
    }
}
