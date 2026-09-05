package apolices;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Classe abstrata de produto do Factory Method.
 * Declara os métodos comuns a toda apólice (RF01-RF04) e concentra
 * a montagem do resumo padronizado (RNF03), que é comum a todas as
 * linhas — só o que entra em cada peça do texto varia por subclasse.
 */
public abstract class ApoliceAbs {

    private final UUID id;
    private final ClienteAbs cliente;
    private String numero;
    private LocalDate dataEmissao;

    protected ApoliceAbs(ClienteAbs cliente) {
        this.id = UUID.randomUUID();
        this.cliente = cliente;
    }

    public UUID getId() {
        return id;
    }

    public ClienteAbs getCliente() {
        return cliente;
    }

    public String getNumero() {
        return numero;
    }

    /** Só a superclasse de criador (ApoliceFactoryAbs) deve chamar isto. */
    void definirNumero(String numero) {
        this.numero = numero;
    }

    /** Só a superclasse de criador (ApoliceFactoryAbs) deve chamar isto. */
    void definirDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public abstract double calcularPremio();

    public abstract boolean validarCobertura();

    public abstract List<String> listarDocumentos();

    /**
     * Resumo textual padronizado (RNF03): número, segurado, data de
     * emissão, prêmio calculado e documentos exigidos.
     */
    public String gerarResumo() {
        String segurado = (cliente instanceof Cliente) ? ((Cliente) cliente).getNome() : cliente.getId().toString();
        String data = dataEmissao == null ? "-" : dataEmissao.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return String.format(
                "Apólice: %s | Segurado: %s | Emissão: %s | Prêmio mensal: R$ %.2f | Documentos exigidos: %s",
                numero, segurado, data, calcularPremio(), String.join(", ", listarDocumentos()));
    }
}
