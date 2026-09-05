package apolices;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe abstrata de criador do Factory Method.
 * criarApolice() é o método fábrica (abstrato) — cada subclasse
 * concreta decide qual linha de produto instanciar. processarContratacao()
 * é concreto e final: centraliza o algoritmo comum (RNF02/RNF03) e só
 * enxerga a apólice através da abstração ApoliceAbs.
 */
public abstract class ApoliceFactoryAbs {

    private static final AtomicInteger SEQUENCIAL = new AtomicInteger(0);

    public abstract ApoliceAbs criarApolice(ClienteAbs cliente);

    public abstract String getPrefixo();

    public final String processarContratacao(ClienteAbs cliente) {
        ApoliceAbs apolice = criarApolice(cliente);

        if (!apolice.validarCobertura()) {
            throw new IllegalStateException(
                    "Contratação rejeitada: cobertura mínima ou documentação obrigatória não atendida ("
                            + getPrefixo() + ").");
        }

        apolice.definirNumero(getPrefixo() + "-" + proximoSequencial());
        apolice.definirDataEmissao(LocalDate.now());

        return apolice.gerarResumo();
    }

    private static String proximoSequencial() {
        return String.format("%04d", SEQUENCIAL.incrementAndGet());
    }
}
