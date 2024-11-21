import java.util.Scanner;

public class Poofs {
    public static void main(String[] args) {
        Empresa empresa = new Empresa();
        Scanner sc = new Scanner(System.in);

        // Adicionar clientes a lista
        boolean verifica = true;
        while(verifica){
            System.out.println("Nome do novo cliente: ");
            String nome = sc.nextLine();
            System.out.println("Contribuinte do novo cliente: ");
            int contribuinte = sc.nextInt();
            System.out.println("Localizacao do novo cliente: (1 - Portugal Continental, 2 - Madeira, 3 - Açores)");
            Localizacao localizacao = null;
            int escolha = sc.nextInt();
            switch (escolha){
                case 1:
                    localizacao = Localizacao.PORTUGAL_CONTINENTAL;
                    break;
                case 2:
                    localizacao = Localizacao.MADEIRA;
                    break;
                case 3:
                    localizacao = Localizacao.ACORES;
                    break;
                default:
                    System.out.println("Nao existe essa opçao!");
            }
            if(localizacao == null){
                System.out.println("Nao foi possivel adicionar o cliente a lista!");
                break;
            }
            Cliente cliente = new Cliente(nome, contribuinte, localizacao);
            empresa.addListaCliente(cliente);
            System.out.println("Cliente adicionado com sucesso a lista!");
            System.out.println("Gostaria de continuar a adicionar clientes? (Sim - digite 1)");
            int continuar = sc.nextInt();
            if(continuar != 1){
                verifica = false;
            }
        }

        empresa.listarClientes();
    }
}
