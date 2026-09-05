package apolices;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Classe abstrata do segurado (cliente da seguradora).
 * Não confundir com a "classe cliente" do padrão Factory Method
 * (essa é a SistemaEmissao) — ClienteAbs é apenas o dado de domínio
 * carregado por toda ApoliceAbs.
 */
public abstract class ClienteAbs {

    private final UUID id;
    private final Map<String, String> docs;
    private final int idade;

    protected ClienteAbs(int idade) {
        this.id = UUID.randomUUID();
        this.docs = new HashMap<>();
        this.idade = idade;
    }

    public UUID getId() {
        return id;
    }

    public int getIdade() {
        return idade;
    }

    public Map<String, String> getDocs() {
        return docs;
    }

    /**
     * Registra os documentos apresentados pelo segurado.
     * Cada documento recebido é guardado com uma chave sequencial.
     */
    public Map<String, String> processarDocs(String[] documentos) {
        for (int i = 0; i < documentos.length; i++) {
            docs.put("doc" + (i + 1), documentos[i]);
        }
        return docs;
    }
}
