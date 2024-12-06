import java.io.*;
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
        File ficheiro = new File("ficheiro.obj");

        if(ficheiro.exists() && ficheiro.isFile()){
            System.out.println("Objeto");
            empresa.carregaFicheiroObjeto(ficheiro);
        }
        else{
            System.out.println("Texto");
            lerFicheiroTexto("ficheiroEntrada.txt");
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Criar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Criar fatura");
            System.out.println("4. Listar faturas");
            System.out.println("5. Editar clientes");
            System.out.println("6. Visualizar Fatura");
            System.out.println("7. Editar Fatura");
            System.out.println("0. Sair");

            int opcao = sc.nextInt();
            sc.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1 -> criarCliente();
                case 2 -> empresa.listarClientes();
                case 3 -> criarFatura();
                case 4 -> empresa.listarFaturas();
                case 5 -> editarClientes();
                case 6 -> empresa.visualizarFaturas(verificarFatura());
                case 7 -> editarFatura();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida!");
            }
        }

        try {
            if (!ficheiro.exists()) {
                ficheiro.createNewFile();  // Criar o arquivo se não existir
                System.out.println("Arquivo criado: " + ficheiro.getName());
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo: " + e.getMessage());
        }

        empresa.atualizaFicheiroObjetos(ficheiro);

        System.out.println("----- Estatisticas -----");
        System.out.println("Numero de faturas: " + empresa.quantidadeFaturas());
        System.out.println("Numero de produtos: " + empresa.quantidadeProdutos());
        System.out.printf("Valor total sem IVA: %.2f \n", empresa.valorTotalSemIVA());
        System.out.printf("Valor total do IVA: %.2f \n", empresa.valorTotalDoIVA());
        System.out.printf("Valor total com IVA: %.2f \n", empresa.valorTotalComIVA());

        sc.close();
    }

    public Fatura verificarFatura(){
        System.out.println("Qual o numero da fatura a visualizar? ");
        int numeroFatura = sc.nextInt();
        sc.nextLine();
        Fatura verificar = empresa.procuraFatura(numeroFatura);
        if(verificar != null){
            return empresa.procuraFatura(numeroFatura);
        }else{
            System.out.println("Fatura não existe");
        }
        return null;
    }

    public void criarCliente(){
        System.out.println("Nome do novo cliente: ");
        String nome = sc.nextLine();

        System.out.println("Contribuinte do novo cliente: ");
        int contribuinte = sc.nextInt();
        sc.nextLine();// Consumir nova linha
        boolean verifica= true;
        while(verifica) {
            int teste = empresa.verificaContribuinte(contribuinte);
            if (teste == 0) {
                verifica=false;
            }
            if (teste == 1){
                System.out.println("Tente novamente");
                contribuinte = sc.nextInt();
                sc.nextLine();
            }
        }
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
            boolean verifica= true;
            while(verifica) {
                int teste = empresa.verificaContribuinte(novoContribuinte);
                if (teste == 0) {
                    verifica=false;
                }
                if (teste == 1){
                    System.out.println("Tente novamente");
                    novoContribuinte = sc.nextInt();
                    sc.nextLine();
                }
            }
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
        boolean verifica= true;
        while(verifica) {
            int teste = empresa.verificaNumeroFatura(numeroFatura);
            if (teste == 0) {
                verifica=false;
            }
            if (teste == 1){
                System.out.println("Tente novamente");
                numeroFatura = sc.nextInt();
                sc.nextLine();
            }
        }

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
        Fatura faturaEditar = null;
        System.out.println("O numero da fatura");
        int numFatura = sc.nextInt();
        sc.nextLine();
        Fatura fatura = empresa.procuraFatura(numFatura);
        if(fatura == null){
            System.out.println("Fatura nao encontrada!");
            return;
        }else{
            faturaEditar = fatura;
        }
        System.out.println("Fatura encontrada: " + faturaEditar.getNumeroFatura());
        System.out.println("Novo numero (coloque 1 pra manter): ");
        int novoNumero = sc.nextInt();
        if(novoNumero==1){
            System.out.println(" Numero não alterado: "+ fatura.getNumeroFatura());
        }else{
            boolean verifica= true;
            while(verifica) {
                int teste = empresa.verificaNumeroFatura(numFatura);
                if (teste == 0) {
                    verifica=false;
                }
                if (teste == 1){
                    System.out.println("Tente novamente");
                    numFatura = sc.nextInt();
                    sc.nextLine();
                }
            }
            fatura.setNumeroFatura(novoNumero);
            System.out.println("Numero alterado");
        }
        System.out.println("Data atual "+ faturaEditar.getData());
        System.out.println("Nova data (coloque 1 pra manter): ");
        String novaData = sc.nextLine();
        sc.nextLine();
        if(novaData.equals("1")){
            System.out.println("Data não alterada: "+ fatura.getData());
        }else {
            fatura.setData(novaData);
        }
        System.out.println("Nome do Cliente atual: "+faturaEditar.getCliente().getNome());
        System.out.println("Novo Cliente (coloca o contribuinte do novo cliente, prime 1 pra manter)");
        int novoCliente = sc.nextInt();
        sc.nextLine();
        if(novoCliente==1){
            System.out.println("Cliente não alterado");
        }else{
            Cliente cliente = empresa.procurarCliente(novoCliente);
            if(cliente == null){
                System.out.println("Cliente não encontrado!");
            }else{
                fatura.setCliente(cliente);
            }
        }
        System.out.println("Quantidade de produtos atual: "+faturaEditar.calcularNumProdutos());
        System.out.println("1. Manter a quantidade");
        System.out.println("2. Adicionar produto");
        System.out.println("3. Eliminar produto");
        int opcao= sc.nextInt();
        switch (opcao){
            case 1 -> System.out.println("Quantidade não alterada");
            case 2 -> addProdutoLista(faturaEditar);
            case 3 -> removeProdutoLista(faturaEditar);
        }
        while(faturaEditar.getProdutos().isEmpty()){
            System.out.println("Erro, não pode deixar a fatura sem produtos");
            addProdutoLista(faturaEditar);
        }
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
                continuar = false;
            }
        }
    }

    public void removeProdutoLista(Fatura fatura){
        boolean continuar = true;
        while(continuar){
            ArrayList<Produto> produtos= fatura.getProdutos();
            int index=0;
            for(Produto produto: produtos){
                System.out.println("Produto "+(index+1)+": "+produto.getNome());
                index+=1;
            }
            System.out.println("Escolha o produto que quer eliminar");
            int escolha= sc.nextInt();
            sc.nextLine();
            if(escolha<=index) {
                fatura.getProdutos().remove(escolha - 1);
            }else{
                System.out.println("Escolha invalida");
            }
            System.out.println("Deseja eliminar mais um produto? (SIM - digite '1')  (NÃO - digite '0')");
            int conf = sc.nextInt();
            sc.nextLine();
            if(conf != 1){
                continuar = false;
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

    public void lerFicheiroTexto(String nomeFicheiro){
        File f = new File(nomeFicheiro);
        if (f.exists() && f.isFile()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while((line = br.readLine()) != null) {
                    String[] linha = line.split(" ");

                    if(linha[0].equals("Cliente")){
                        String nome = linha[1];
                        int contribuinte = Integer.parseInt(linha[2]);
                        Localizacao localizacao = null;
                        switch (linha[3]){
                            case "PORTUGAL_CONTINENTAL" -> localizacao = Localizacao.PORTUGAL_CONTINENTAL;
                            case "MADEIRA" -> localizacao = Localizacao.MADEIRA;
                            case "ACORES" -> localizacao = Localizacao.ACORES;
                            default -> System.out.println("Erro, nenhuma das opçoes estao presentes");
                        }
                        Cliente cliente = new Cliente(nome, contribuinte, localizacao);
                        empresa.addListaCliente(cliente);
                    }
                    else{
                        while(line != null){
                            int numeroFatura = Integer.parseInt(linha[1]);
                            int contribuinte = Integer.parseInt(linha[2]);
                            Cliente cliente = empresa.procurarCliente(contribuinte);
                            String data = linha[3];
                            Fatura fatura = new Fatura(numeroFatura, cliente, data);
                            empresa.addListaFatura(fatura);
                            line = br.readLine();
                            linha = line.split(" ");

                            while(!linha[0].equals("Fatura")){
                                String codigo = linha[1];
                                String nome = linha[2];
                                String descricao = linha[3];
                                int quantidade = Integer.parseInt(linha[4]);
                                double valorUnitario = Double.parseDouble(linha[5]);

                                if(linha[0].equals("Alimentar")){
                                    boolean isBiologico = linha[6].equals("True");
                                    Taxa taxa = null;
                                    ArrayList<Certificacao> certificacoes = new ArrayList<>();
                                    CategoriaAlimentar categoria = null;
                                    switch (linha[7]){
                                        case "NORMAL":
                                            taxa = Taxa.NORMAL;
                                            break;
                                        case "INTERMEDIARIA":
                                            taxa = Taxa.INTERMEDIARIA;
                                            switch (linha[8]){
                                                case "CONGELADOS" -> categoria = CategoriaAlimentar.CONGELADOS;
                                                case "ENLATADOS" -> categoria = CategoriaAlimentar.ENLATADOS;
                                                case "VINHOS" -> categoria = CategoriaAlimentar.VINHOS;
                                                case "OUTROS" -> categoria = CategoriaAlimentar.OUTROS;
                                                default -> System.out.println("Erro, nenhuma das opçoes estao presentes");
                                            }
                                            break;
                                        case "REDUZIDA":
                                            taxa = Taxa.REDUZIDA;
                                            for(int i = 8; i < 12; i++){
                                                if(!linha[i].equals("NULL")){
                                                    switch (linha[i]){
                                                        case "ISO22000" -> certificacoes.add(Certificacao.ISO22000);
                                                        case "FSSC22000" -> certificacoes.add(Certificacao.FSSC22000);
                                                        case "HACCP" -> certificacoes.add(Certificacao.HACCP);
                                                        case "GMP" -> certificacoes.add(Certificacao.GMP);
                                                    }
                                                }
                                            }
                                    }
                                    Produto produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, isBiologico, taxa, certificacoes, categoria);
                                    fatura.addProduto(produto);
                                }
                                else{
                                    boolean hasPrescricao = linha[6].equals("True");
                                    String medicoPrescritor = "";
                                    CategoriaFarmacia categoria = null;
                                    if(hasPrescricao){
                                        medicoPrescritor = linha[7];
                                    }
                                    else{
                                        switch (linha[7]){
                                            case "BELEZA" -> categoria = CategoriaFarmacia.BELEZA;
                                            case "BEM_ESTAR" -> categoria = CategoriaFarmacia.BEM_ESTAR;
                                            case "BEBES" -> categoria = CategoriaFarmacia.BEBES;
                                            case "ANIMAIS" -> categoria = CategoriaFarmacia.ANIMAIS;
                                            case "OUTROS" -> categoria = CategoriaFarmacia.OUTROS;
                                        }
                                    }
                                    Produto produto = new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, hasPrescricao, medicoPrescritor, categoria);
                                    fatura.addProduto(produto);
                                }
                                line = br.readLine();
                                if(line != null){
                                    linha = line.split(" ");
                                }
                                else {
                                    break;
                                }
                            }
                        }
                    }
                }
                br.close();
            }
            catch (FileNotFoundException ex) {
                System.out.println("Erro a abrir ficheiro de texto.");
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro de texto.");
            }
        } else {
            System.out.println("Ficheiro não existe.");
        }
    }

    public void lerFicheiroObjeto(){
    }
}