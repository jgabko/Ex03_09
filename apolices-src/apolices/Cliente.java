package apolices;

/**
 * Segurado concreto. O campo "nome" foi acrescentado em relação ao
 * diagrama apenas para que o resumo da apólice (RNF03) tenha como
 * exibir o segurado de forma legível.
 */
public class Cliente extends ClienteAbs {

    private final String nome;

    public Cliente(String nome, int idade) {
        super(idade);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
