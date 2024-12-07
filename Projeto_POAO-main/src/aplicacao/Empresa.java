package aplicacao;

import enums.CategoriaAlimentar;
import enums.CategoriaFarmacia;
import enums.Certificacao;
import enums.Taxa;

import java.io.*;
import java.util.ArrayList;

public class Empresa {
    private ArrayList<Fatura> faturas;
    private ArrayList<Cliente> clientes;

    Empresa(){
        faturas = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    // Adiciona um cliente na lista de clientes
    void addListaCliente(Cliente cliente){
        clientes.add(cliente);
    }

    // Adiciona uma fatura na lista de faturas
    void addListaFatura(Fatura fatura){
        faturas.add(fatura);
    }

    // Procura um cliente na lista pelo numero de contribuinte
    Cliente procurarCliente(int contribuinte){
        for(Cliente cliente : clientes){
            if(cliente.getContribuinte() == contribuinte){
                return cliente;
            }
        }
        System.out.printf("O cliente com contribuinte (%d) nao se encontra na lista!\n", contribuinte);
        return null;
    }

    // Procura uma fatura na lista pelo numero da fatura
    Fatura procuraFatura(int numeroFatura){
        for (Fatura fatura : faturas) {
            if (fatura.getNumeroFatura() == numeroFatura) {
                return fatura;
            }
        }
        return null;
    }
    int verificaContribuinte(int contribuinte){
        for(Cliente cliente : clientes){
            if(cliente.getContribuinte() == contribuinte){
                System.out.println("Contribuinte já existente");
                return 1;
            }
        }
        return 0;
    }

    int verificaNumeroFatura(int numeroFatura){
        for(Fatura fatura : faturas){
            if(fatura.getNumeroFatura() == numeroFatura){
                System.out.printf("A fatura nº %d ja existe na lista\n", numeroFatura);
                return 1;
            }
        }
        return 0;
    }

    // Lista as todas as informaçoes dos clientes da lista de clientes
    void listarClientes(){
        System.out.println("----- Lista de clientes -----");
        for(Cliente cliente : clientes){
            System.out.println("nome: " + cliente.getNome());
            System.out.println("contribuinte: " + cliente.getContribuinte());
            System.out.println("localizacao: " + cliente.getLocalizacao());
            System.out.println("----------");
        }
    }

    // Lista as informacoes das faturas da lista de faturas
    void listarFaturas(){
        System.out.println("----- Lista de faturas -----");
        for(Fatura fatura : faturas){
            System.out.println("numero da fatura: " + fatura.getNumeroFatura());
            System.out.println("data da fatura: " + fatura.getData());
            System.out.println("cliente: " + fatura.getCliente().getNome());
            System.out.println("localizacao do cliente: " + fatura.getCliente().getLocalizacao());
            System.out.println("quantidade de produtos: " + fatura.calcularNumProdutos());
            System.out.printf("valor total sem IVA: %.2f \n", fatura.calcularValorSemIVA());
            System.out.printf("valor total com IVA: %.2f \n", fatura.calcularValorComIVA());
            System.out.println("----------");
        }
    }

    // Visualizar as informacoes de uma determinada fatura
    void visualizarFaturas(Fatura fatura){
        if(fatura != null){
            System.out.println("----- Visualizacao da fatura -----");
            System.out.println("numero da fatura: " + fatura.getNumeroFatura());
            System.out.println("nome do cliente: " + fatura.getCliente().getNome());
            System.out.println("contribuinte: " + fatura.getCliente().getContribuinte());
            System.out.println("localizacao: " + fatura.getCliente().getLocalizacao());
            fatura.listarProdutos();
            System.out.printf("valor total sem IVA: %.2f \n", fatura.calcularValorSemIVA());
            System.out.printf("valor total com IVA: %.2f \n", fatura.calcularValorComIVA());
        }
        else{
            System.out.println("Fatura nao encontrada na lista!");
        }
    }

    int quantidadeFaturas(){
        return faturas.size();
    }

    int quantidadeProdutos(){
        int quantidade = 0;
        for(Fatura fatura : faturas){
            quantidade += fatura.calcularNumProdutos();
        }
        return quantidade;
    }

    double valorTotalSemIVA(){
        double valor = 0;
        for(Fatura fatura : faturas){
            valor += fatura.calcularValorSemIVA();
        }
        return valor;
    }

    double valorTotalComIVA(){
        double valor = 0;
        for(Fatura fatura : faturas){
            valor += fatura.calcularValorComIVA();
        }
        return valor;
    }

    double valorTotalDoIVA(){
        return valorTotalComIVA() - valorTotalSemIVA();
    }

    void atualizaFicheiroObjetos(File ficheiro){
        try {
            FileOutputStream fos = new FileOutputStream(ficheiro);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(clientes);
            oos.writeObject(faturas);

            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro a criar ficheiro.");
        } catch (IOException ex) {
            System.out.println("Erro a escrever para o ficheiro.");
        }
    }

    void carregaFicheiroObjeto(File ficheiro) {
        try {
            FileInputStream fis = new FileInputStream(ficheiro);
            ObjectInputStream ois = new ObjectInputStream(fis);

            clientes = (ArrayList<Cliente>) ois.readObject();
            faturas = (ArrayList<Fatura>) ois.readObject();

            ois.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro a abrir ficheiro.");
        } catch (IOException ex) {
            System.out.println("Erro a ler ficheiro.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro a converter objeto.");
        }
    }

    void importarFaturas(File ficheiro){
        if(ficheiro != null){
            try {
                FileReader fr = new FileReader(ficheiro);
                BufferedReader br = new BufferedReader(fr);
                String line;
                String[] linha;
                while((line = br.readLine()) != null){
                    linha = line.split(" ");
                    if(linha[0].equals("Fatura")){
                        int numeroFatura = Integer.parseInt(linha[1]);
                        // Executa caso a fatura ainda nao exista na lista
                        if(verificaNumeroFatura(numeroFatura) == 0){
                            int contribuinte = Integer.parseInt(linha[2]);
                            Cliente cliente = procurarCliente(contribuinte);
                            if(cliente == null)
                                continue;
                            String data = linha[3];
                            Fatura fatura = new Fatura(numeroFatura, cliente, data);
                            addListaFatura(fatura);
                            System.out.println("Nova fatura adicionada!");
                            line = br.readLine();
                            linha = line.split(" ");

                            while(!linha[0].equals("Fatura")){
                                lerProdutosFicheiro(linha, fatura);
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
            } catch (FileNotFoundException ex) {
                System.out.println("Erro a abrir ficheiro de texto.");
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro de texto.");
            }

        }
        else{
            System.out.println("Nenhuma fatura para importar!");
        }
    }

    void exportarFaturas(File ficheiro, Fatura fatura){
        if(fatura != null){
            try {
                FileWriter fw = new FileWriter(ficheiro, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("---------- Fatura Nº " + fatura.getNumeroFatura() + "----------");
                bw.newLine();
                bw.write("Cliente: " + fatura.getCliente().getNome() + "    Contribuinte: " + fatura.getCliente().getContribuinte() + "    Localizaçao: " + fatura.getCliente().getLocalizacao());
                bw.newLine();
                bw.write("Data " + fatura.getData());
                bw.newLine();
                bw.newLine();
                bw.write("Produtos:");
                bw.newLine();
                for(Produto produto : fatura.getProdutos()){
                    bw.write("Codigo: " + produto.getCodigo() + "    Nome: " + produto.getNome());
                    bw.newLine();
                    bw.write("Descriçao: " + produto.getDescricao());
                    bw.newLine();
                    bw.write("Quantidade: " + produto.getQuantidade() + "    Preço da unidade: " + produto.getValorUnitario());
                    bw.newLine();
                }
                bw.write("-----------------------------------");
                bw.newLine();
                bw.newLine();
                bw.close();
                System.out.println("Exportado com sucesso!");
            } catch (IOException ex) {
                System.out.println("Erro a escrever no ficheiro.");
            }
        }
        else{
            System.out.println("Fatura nao encontrada na lista!");
        }
    }

    void lerProdutosFicheiro(String[] linha, Fatura fatura){
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
    }
}
