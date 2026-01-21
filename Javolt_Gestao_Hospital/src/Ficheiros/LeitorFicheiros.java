package Ficheiros;

// Importação das entidades que vão ser criadas a partir dos ficheiros

/**
 * Classe LeitorFicheiros
 * Responsável exclusivamente pela LEITURA de ficheiros.
 *
 * Esta classe é usada no arranque da aplicação
 * para carregar dados iniciais para a memória.
 */
public class LeitorFicheiros {

    // Separador usado nos ficheiros (ex: ; ou ,)
    private String separador;

    // ================= CONSTRUTOR =================

    /**
     * Construtor do LeitorFicheiros.
     * Recebe o separador definido nas configurações.
     */
    public LeitorFicheiros(String separador) {
        this.separador = separador;
    }

    /*
     * NOTA PARA A EQUIPA:
     * Os métodos seguintes seguem todos o mesmo padrão:
     *
     * 1. Abrir ficheiro
     * 2. Ler linha a linha
     * 3. Separar a linha usando o separador
     * 4. Criar objetos (Medico, Sintoma, Especialidade)
     * 5. Guardar num array
     *
     * Os arrays são depois devolvidos ao GestaoHospital.
     */

    // (resto do código permanece exatamente igual ao original)

    /*
     * Exemplo de comportamento típico dos métodos:
     *
     * while (linha != null) {
     *     String[] dados = linha.split(separador);
     *     // criação do objeto
     * }
     *
     * O contador 'i' controla quantas posições do array estão preenchidas.
     */
}
