package apolices;

import java.util.List;

/**
 * RF02 — Apólice Residencial.
 * documentoImovelApresentado foi acrescentado em relação ao diagrama:
 * representa se a escritura ou o contrato de locação foi apresentado,
 * exigido pela especificação para validar a contratação.
 */
public class ResApolice extends ApoliceAbs {

    private final double valorImovel;
    private final boolean altoPadrao;
    private final boolean documentoImovelApresentado;

    public ResApolice(ClienteAbs cliente, double valorImovel, boolean altoPadrao,
                       boolean documentoImovelApresentado) {
        super(cliente);
        this.valorImovel = valorImovel;
        this.altoPadrao = altoPadrao;
        this.documentoImovelApresentado = documentoImovelApresentado;
    }

    @Override
    public double calcularPremio() {
        double premioAnual = valorImovel * 0.015;
        if (altoPadrao) {
            premioAnual *= 1.25;
        }
        return premioAnual / 12.0;
    }

    @Override
    public boolean validarCobertura() {
        return documentoImovelApresentado;
    }

    @Override
    public List<String> listarDocumentos() {
        return List.of("Escritura ou contrato de locação", "Comprovante de residência");
    }
}
