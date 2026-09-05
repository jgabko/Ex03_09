package apolices;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe cliente do padrão Factory Method.
 * Guarda a fábrica registrada para cada tipo de apólice e delega toda
 * a emissão a ela — nunca instancia AutoApolice, ResApolice, VidApolice
 * ou ViaApolice diretamente, e nunca decide a lógica de cálculo/validação
 * de cada linha (isso vive inteiramente nas hierarquias de produto e
 * criador). Adicionar uma quinta linha de produto exige só registrar
 * uma nova fábrica aqui — nenhuma classe existente precisa mudar.
 */
public class SistemaEmissao {

    private final Map<String, ApoliceFactoryAbs> fabricas = new HashMap<>();

    public void registrarFabrica(String tipo, ApoliceFactoryAbs fabrica) {
        fabricas.put(tipo, fabrica);
    }

    public String emitirApolice(String tipo, ClienteAbs cliente) {
        ApoliceFactoryAbs fabrica = fabricas.get(tipo);
        if (fabrica == null) {
            throw new IllegalArgumentException("Nenhuma fábrica registrada para o tipo: " + tipo);
        }
        return fabrica.processarContratacao(cliente);
    }
}
