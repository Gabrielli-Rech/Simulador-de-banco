// Conta.java
import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1; // Contador para gerar números de conta únicos

    // Atributos protegidos para serem acessíveis pelas classes filhas
    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente titular;
    protected List<String> transacoes;

    public Conta(Cliente titular) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.titular = titular;
        this.saldo = 0.0;
        this.transacoes = new ArrayList<>();
    }

    // --- MÉTODOS ---

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            transacoes.add(String.format("Depósito: R$ %.2f", valor));
        } else {
            System.out.println("Valor de depósito deve ser positivo.");
        }
    }

    /**
     * Tenta sacar um valor da conta.
     * @param valor O valor a ser sacado.
     * @throws SaldoInsuficienteException se o saldo for insuficiente.
     */
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= 0) {
            System.out.println("Valor de saque deve ser positivo.");
            return;
        }
        if (this.saldo < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para o saque de R$ " + valor);
        }
        this.saldo -= valor;
        transacoes.add(String.format("Saque: R$ %.2f", valor));
    }

    public void transferir(double valor, Conta contaDestino) throws SaldoInsuficienteException {
        this.sacar(valor); // Reutiliza o método sacar (que já tem a validação de saldo)
        contaDestino.depositar(valor);
        // O registro da transação já é feito pelos métodos sacar e depositar
    }

    public void imprimirExtrato() {
        System.out.println("--- Extrato da Conta ---");
        System.out.println(this.titular);
        System.out.printf("Agência: %d | Conta: %d%n", this.agencia, this.numero);
        System.out.println("------------------------");
        for (String transacao : transacoes) {
            System.out.println(transacao);
        }
        System.out.println("------------------------");
        System.out.printf("Saldo Atual: R$ %.2f%n", this.saldo);
        System.out.println("------------------------");
    }

    // --- GETTERS ---
    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }
}