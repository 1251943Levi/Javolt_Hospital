package Ficheiros;

// Classes necessárias para escrita em ficheiros
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe GestorFicheiros
 * Responsável exclusivamente pela ESCRITA em ficheiros.
 *
 * Esta classe NÃO contém lógica de negócio.
 * É utilizada pelo GestaoHospital para:
 *  - escrever logs
 *  - guardar informação quando necessário
 */
public class GestorFicheiros {

    // Separador usado nos ficheiros (ex: ; ou ,)
    private String separador;

    /**
     * Construtor do GestorFicheiros.
     * Recebe o separador definido na configuração do sistema.
     */
    public GestorFicheiros(String separador) {
        this.separador = separador;
    }

    // ================== ESCREVER LOG ==================

    /**
     * Escreve uma mensagem de log num ficheiro de texto.
     * O ficheiro é aberto em modo APPEND (não apaga conteúdo anterior).
     *
     * @param caminho caminho do ficheiro de log
     * @param texto mensagem a escrever
     */
    public void escreverLog(String caminho, String texto) {

        // try-with-resources garante o fecho automático do ficheiro
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho, true))) {

            // Escreve o texto no ficheiro
            bw.write(texto);
            // Nova linha após cada mensagem
            bw.newLine();

        } catch (IOException e) {
            // Mensagem de erro simples (não interrompe o programa)
            System.out.println("Erro ao escrever log: " + e.getMessage());
        }
    }
}
