package aplicacao;

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
        System.out.println("Cliente nao encontrado na lista!");
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
                System.out.println("Numero de fatura já existente");
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

}
