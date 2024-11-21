import java.util.Scanner;

public class Poofs {
    private Empresa empresa;
    private Scanner sc;

    public Poofs() {
        empresa = new Empresa();
        sc = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Poofs app = new Poofs();
        app.executar();
    }

    public void executar() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Criar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Criar fatura");
            System.out.println("4. Listar faturas");
            System.out.println("0. Sair");

            int opcao = sc.nextInt();
            sc.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1 -> criarCliente();
                case 2 -> empresa.listarClientes();
                case 3 -> criarFatura();
                case 4 -> empresa.listarFaturas();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida!");
            }
        }
        sc.close();
    }

    public void criarCliente(){
        System.out.println("Nome do novo cliente: ");
        String nome = sc.nextLine();

        System.out.println("Contribuinte do novo cliente: ");
        int contribuinte = sc.nextInt();
        sc.nextLine(); // Consumir nova linha

        System.out.println("Localizacao do novo cliente: (1 - Portugal Continental, 2 - Madeira, 3 - Açores)");
        Localizacao localizacao = null;

        int escolha = sc.nextInt();
        sc.nextLine();
        switch (escolha){
            case 1 -> localizacao = Localizacao.PORTUGAL_CONTINENTAL;
            case 2 -> localizacao = Localizacao.MADEIRA;
            case 3 -> localizacao = Localizacao.ACORES;
            default -> System.out.println("Nao existe essa opçao!");
        }
        if(localizacao == null){
            System.out.println("Nao foi possivel adicionar o cliente a lista!");
            return;
        }
        Cliente cliente = new Cliente(nome, contribuinte, localizacao);
        empresa.addListaCliente(cliente);
        System.out.println("Cliente adicionado com sucesso a lista!");
    }

    public void criarFatura(){
        System.out.println("Digite o numero da fatura: ");
        int numeroFatura = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite o contribuinte do cliente da fatura: ");
        int contribuinte = sc.nextInt();
        sc.nextLine();
        Cliente cliente = empresa.procurarCliente(contribuinte);
        if(cliente == null){
            System.out.println("Cliente nao encontrado!");
            return;
        }

        System.out.println("Insira a data da fatura: ");
        String data = sc.nextLine();

        Fatura fatura = new Fatura(numeroFatura, cliente, data);

        System.out.println("Insira o(s) produto(s) da fatura: ");
        addProduto(fatura);

        empresa.addListaFatura(fatura);
        System.out.println("Fatura adicionada com sucesso a lista!");
    }

    public void addProduto(Fatura fatura){
        boolean continuar = true;
        while (continuar){
            System.out.println("Qual o tipo de produto que gostaria de adicionar a fatura?  (1 - Alimentar, 2 - Farmacia)");
            int escolhaProduto = sc.nextInt();
            sc.nextLine();
            switch (escolhaProduto){
                case 1 -> fatura.addProduto(criarProdutoAlimentar());
                case 2 -> fatura.addProduto(criarProdutoFarmacia());
                default -> System.out.println("Opção inválida!");
            }

            System.out.println("Deseja continuar a adicionar produtos na fatura? (SIM - digite '1')");
            int conf = sc.nextInt();
            sc.nextLine();
            if(conf != 1){
                break;
            }
        }
    }

    public Produto criarProdutoAlimentar(){
        System.out.println("O produto e biologico?  (SIM - 1, NAO - 0)");
        int verificaBiologico = sc.nextInt();
        sc.nextLine();
        if(verificaBiologico == 1){
            boolean isBiologico = true;
        }
        else{
            boolean isBiologico = false;
        }

    }

    public Produto criarProdutoFarmacia(){

    }
}
