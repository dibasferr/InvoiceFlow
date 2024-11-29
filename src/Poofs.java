import java.util.ArrayList;
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
            System.out.println("5. Editar clientes");
            System.out.println("0. Sair");

            int opcao = sc.nextInt();
            sc.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1 -> criarCliente();
                case 2 -> empresa.listarClientes();
                case 3 -> criarFatura();
                case 4 -> empresa.listarFaturas();
                case 5 -> editarClientes();
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
    public void editarClientes(){
        Scanner sc = new Scanner(System.in);
        Cliente clienteEditar = null;
        System.out.println("Digite o contribuinte do cliente");
        int contribuite = sc.nextInt();
        sc.nextLine();
        Cliente cliente = empresa.procurarCliente(contribuite);
        if(cliente == null){
            System.out.println("Cliente nao encontrado!");
            return;
        }else{
            clienteEditar = cliente;
        }
        System.out.println("Cliente encontrado " + clienteEditar.getNome());
        System.out.println("Novo nome (coloque 1 pra manter): ");
        String novoNome = sc.nextLine();
        if(novoNome.equals("1")){
            System.out.println(" Nome não alterado: "+ cliente.getNome());
        }else{
            cliente.setNome(novoNome);
            System.out.println("Nome alterado");
        }
        System.out.println("Contribuinte atual: "+ clienteEditar.getContribuinte());
        System.out.println("Novo contribuinte (coloque 1 para manter): ");
        int novoContribuinte = sc.nextInt();
        if(novoContribuinte == 1){
            System.out.println("Contribuinte não alterado: "+ cliente.getContribuinte());
        }else{
            cliente.setContribuinte(novoContribuinte);
        }
        System.out.println("Localização atual: "+clienteEditar.getLocalizacao());
        System.out.println("Editar localização do cliente: (1 - Portugal Continental, 2 - Madeira, 3 - Açores, 0 - Manter)");
        Localizacao localizacao = null;
        int escolha = sc.nextInt();
        sc.nextLine();
        switch (escolha){
            case 1 -> localizacao = Localizacao.PORTUGAL_CONTINENTAL;
            case 2 -> localizacao = Localizacao.MADEIRA;
            case 3 -> localizacao = Localizacao.ACORES;
            case 0 -> localizacao = cliente.getLocalizacao();
            default -> System.out.println("Nao existe essa opçao!");
        }
        cliente.setLocalizacao(localizacao);
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

        addProdutoLista(fatura);

        empresa.addListaFatura(fatura);
        System.out.println("Fatura adicionada com sucesso a lista!");
    }

    public void editarFatura(){
        Scanner sc = new Scanner(System.in);
        Fatura FaturaEditar = null;
        System.out.println("O numero da fatura");
    }

    public void addProdutoLista(Fatura fatura){
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

    // Funcao para a atribuiçao de dados dos atributos da classe ProdutoAlimentar
    public ProdutoAlimentar criarProdutoAlimentar(){
        System.out.println("Qual o codigo do produto? ");
        String codigo = sc.nextLine();

        System.out.println("Qual o nome do produto? ");
        String nome = sc.nextLine();

        System.out.println("Qual a descricao do produto? ");
        String descricao = sc.nextLine();

        System.out.println("Qual a quantidade do produto? ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.println("Qual o valor da unidade do produto? ");
        double valorUnitario = Double.parseDouble(sc.nextLine());

        System.out.println("O produto e biologico?  (SIM - digite '1')");
        int verificaBiologico = sc.nextInt();
        sc.nextLine();
        boolean isBiologico = false;
        if(verificaBiologico == 1){
            isBiologico = true;
        }

        System.out.println("Qual o tipo de taxa do produto?  (1 - REDUZIDA, 2 - INTERMEDIARIA, 3 - NORMAL)");
        Taxa taxa = null;
        int verificaTaxa = sc.nextInt();
        sc.nextLine();
        switch (verificaTaxa){
            case 1 -> taxa = Taxa.REDUZIDA;
            case 2 -> taxa = Taxa.INTERMEDIARIA;
            case 3 -> taxa = Taxa.NORMAL;
            default -> System.out.println("Opçao invalida!");
        }
        if(taxa == null){
            taxa = Taxa.NORMAL;
        }

        ArrayList<Certificacao> certificacoes = new ArrayList<>();
        if(taxa == Taxa.REDUZIDA){
            formatoCertificacao(Certificacao.ISO22000, certificacoes);
            formatoCertificacao(Certificacao.FSSC22000, certificacoes);
            formatoCertificacao(Certificacao.HACCP, certificacoes);
            formatoCertificacao(Certificacao.GMP, certificacoes);
        }

        CategoriaAlimentar categoria = CategoriaAlimentar.OUTROS;
        if(taxa == Taxa.INTERMEDIARIA){
            System.out.println("Qual a categoria do produto? (1 - CONGELADOS, 2 - ENLATADOS, 3 - VINHOS)");
            int verificaCategoria = sc.nextInt();
            sc.nextLine();
            switch (verificaCategoria){
                case 1 -> categoria = CategoriaAlimentar.CONGELADOS;
                case 2 -> categoria = CategoriaAlimentar.ENLATADOS;
                case 3 -> categoria = CategoriaAlimentar.VINHOS;
                default -> System.out.println("Opçao invalida!");
            }
        }

        return new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, isBiologico, taxa, certificacoes, categoria);
    }

    public void formatoCertificacao(Certificacao certificacao, ArrayList<Certificacao> certificacoes){
        System.out.println("O produto vai ter a certificacao %s?  (SIM - digite '1')" + certificacao);
        int verificaCertificacao = sc.nextInt();
        sc.nextLine();
        if(verificaCertificacao == 1){
            certificacoes.add(certificacao);
        }
    }

    // Funcao para a atribuiçao de dados dos atributos da classe ProdutoFarmacia
    public ProdutoFarmacia criarProdutoFarmacia(){
        System.out.println("Qual o codigo do produto? ");
        String codigo = sc.nextLine();

        System.out.println("Qual o nome do produto? ");
        String nome = sc.nextLine();

        System.out.println("Qual a descricao do produto? ");
        String descricao = sc.nextLine();

        System.out.println("Qual a quantidade do produto? ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.println("Qual o valor da unidade do produto? ");
        double valorUnitario = sc.nextDouble();
        sc.nextLine();

        System.out.println("O produto tem prescriçao?  (SIM - digite '1')");
        int verificaPrescricao = sc.nextInt();
        sc.nextLine();
        boolean hasPrescricao = false;
        if(verificaPrescricao == 1){
            hasPrescricao = true;
        }

        String medicoPrescritor = "";
        CategoriaFarmacia categoria = CategoriaFarmacia.OUTROS;
        if(hasPrescricao){
            System.out.println("Qual o nome do medico que fez a prescriçao? ");
            medicoPrescritor = sc.nextLine();
        }
        else{
            System.out.println("Qual a categoria do produto? (1 - BELEZA, 2 - BEM-ESTAR, 3 - BEBES, 4 - ANIMAIS)");
            int verificaCategoria = sc.nextInt();
            sc.nextLine();
            switch (verificaCategoria){
                case 1 -> categoria = CategoriaFarmacia.BELEZA;
                case 2 -> categoria = CategoriaFarmacia.BEM_ESTAR;
                case 3 -> categoria = CategoriaFarmacia.BEBES;
                case 4 -> categoria = CategoriaFarmacia.ANIMAIS;
                default -> System.out.println("Opçao invalida!");
            }
        }

        return new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, hasPrescricao, medicoPrescritor, categoria);
    }
}