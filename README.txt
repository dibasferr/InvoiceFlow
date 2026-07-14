# InvoiceFlow

Sistema de gestão financeira desenvolvido em **Java**, utilizando os princípios da **Programação Orientada aos Objetos (POO)**.

O projeto permite o gerenciamento de clientes, emissão de faturas, cálculo automático de IVA, importação/exportação de dados e persistência das informações, seguindo os requisitos propostos para a disciplina de Programação Orientada aos Objetos.

---

## Funcionalidades

- Cadastro de clientes
- Edição de clientes
- Emissão de faturas
- Visualização detalhada de faturas
- Listagem de clientes
- Listagem de faturas
- Cálculo automático de IVA conforme:
  - Tipo de produto
  - Localização do cliente
  - Regras fiscais específicas
- Importação de faturas através de ficheiro de texto
- Exportação de faturas
- Persistência dos dados utilizando serialização de objetos
- Estatísticas gerais do sistema

---

## Tecnologias utilizadas

- Java
- Programação Orientada aos Objetos
- Herança
- Polimorfismo
- Encapsulamento
- Serialização de Objetos
- Javadoc

---

## Como executar

1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/InvoiceFlow.git
```

2. Abra o projeto na sua IDE de preferência (IntelliJ IDEA, Eclipse ou NetBeans).

3. Compile e execute a classe principal "Poofs.java".

---

## Conceitos aplicados

Este projeto foi desenvolvido aplicando diversos conceitos de Programação Orientada aos Objetos, entre eles:

- Abstração
- Encapsulamento
- Herança
- Polimorfismo
- Sobrescrita de métodos
- Organização em pacotes
- Persistência de dados
- Boas práticas de programação

---

## Funcionalidades fiscais

O sistema realiza automaticamente:

- Cálculo do IVA conforme a localização do cliente
- Diferenciação entre produtos alimentares e farmacêuticos
- Aplicação de taxas reduzidas, intermédias e normais
- Aplicação de descontos e acréscimos previstos nas regras do projeto

---

## Objetivo

Este projeto foi desenvolvido com fins académicos para a disciplina de **Programação Orientada aos Objetos**, tendo como objetivo consolidar conhecimentos sobre modelagem de classes, herança, polimorfismo, persistência de dados e boas práticas de desenvolvimento em Java.

---
