// ContaCorrente.java
public class ContaCorrente extends Conta {

    public ContaCorrente(Cliente titular) {
        super(titular); // Chama o construtor da classe mãe (Conta)
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Corrente ===");
        super.imprimirExtrato(); // Aproveita o método da classe mãe
    }
}