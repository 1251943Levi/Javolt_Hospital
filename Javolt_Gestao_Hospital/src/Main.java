import Serviços.GestaoHospital;
import UI.Menu;

public class Main {
    public static void main(String[] args) {

        // Criar o gestor principal do sistema
        GestaoHospital gestaoHospital = new GestaoHospital();

        // Injetar o gestor no menu
        Menu menu = new Menu(gestaoHospital);

        // Iniciar aplicação
        menu.start();
    }
}
