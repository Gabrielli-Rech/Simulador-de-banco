// SimuladorBancoTerminal.java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimuladorBancoTerminal {

    private static Map<Integer, Conta> contas = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    depositar();
                    break;
                case 3:
                    sacar();
                    break;
                case 4:
                    transferir();
                    break;
                case 5:
                    exibirExtrato();
                    break;
                case 0:
                    System.out.println("Obrigado por usar nosso banco. Até logo!");
                    return; // Encerra o programa
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n--- Bem-vindo ao Banco Digital ---");
        System.out.println("1. Criar Conta");
        System.out.println("2. Depositar");
        System.out.println("3. Sacar");
        System.out.println("4. Transferir");
        System.out.println("5. Exibir Extrato");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    // --- MÉTODOS PARA AS OPERAÇÕES ---

    private static void criarConta() {
        System.out.print("Nome do titular: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do titular: ");
        String cpf = scanner.nextLine();
        Cliente cliente = new Cliente(nome, cpf);

        System.out.print("Tipo da conta (1-Corrente, 2-Poupanca): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        Conta novaConta;
        if (tipo == 1) {
            novaConta = new ContaCorrente(cliente);
        } else {
            novaConta = new ContaPoupanca(cliente);
        }
        
        contas.put(novaConta.getNumero(), novaConta);
        System.out.println("Conta criada com sucesso! Número da conta: " + novaConta.getNumero());
    }

    private static void depositar() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        Conta conta = contas.get(numero);

        if (conta != null) {
            System.out.print("Valor do depósito: ");
            double valor = scanner.nextDouble();
            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void sacar() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        Conta conta = contas.get(numero);

        if (conta != null) {
            System.out.print("Valor do saque: ");
            double valor = scanner.nextDouble();
            try {
                // Tenta executar a operação que pode lançar a exceção
                conta.sacar(valor);
                System.out.println("Saque realizado com sucesso.");
            } catch (SaldoInsuficienteException e) {
                // Captura a exceção e exibe a mensagem de erro amigável
                System.out.println("Erro ao sacar: " + e.getMessage());
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }
    
    private static void transferir() {
        System.out.print("Número da conta de origem: ");
        int numOrigem = scanner.nextInt();
        Conta contaOrigem = contas.get(numOrigem);

        System.out.print("Número da conta de destino: ");
        int numDestino = scanner.nextInt();
        Conta contaDestino = contas.get(numDestino);

        if (contaOrigem != null && contaDestino != null) {
            System.out.print("Valor da transferência: ");
            double valor = scanner.nextDouble();
            try {
                contaOrigem.transferir(valor, contaDestino);
                System.out.println("Transferência realizada com sucesso.");
            } catch (SaldoInsuficienteException e) {
                System.out.println("Erro na transferência: " + e.getMessage());
            }
        } else {
            System.out.println("Uma ou ambas as contas não foram encontradas.");
        }
    }

    private static void exibirExtrato() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        Conta conta = contas.get(numero);

        if (conta != null) {
            conta.imprimirExtrato();
        } else {
            System.out.println("Conta não encontrada.");
        }
    }
}