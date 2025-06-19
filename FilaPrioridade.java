/**
 * Implementação de uma fila de prioridade usando array.
 * Mantém os elementos ordenados por prioridade.
 */
public class FilaPrioridade {
    private static final int CAPACIDADE_PADRAO = 10;
    private Pessoa[] pessoas;
    private int quantidade;
    private int capacidade;

    /**
     * Construtor da fila de prioridade.
     * @param capacidade Capacidade inicial da fila
     */
    public FilaPrioridade(int capacidade) {
        this.capacidade = capacidade > 0 ? capacidade : CAPACIDADE_PADRAO;
        this.pessoas = new Pessoa[this.capacidade];
        this.quantidade = 0;
    }

    /**
     * Insere uma pessoa na fila mantendo a ordem de prioridade.
     * @param pessoa Pessoa a ser inserida
     * @return true se a inserção foi bem sucedida, false caso contrário
     */
    public boolean inserir(Pessoa pessoa) {
        if (pessoa == null) return false;
        
        if (quantidade >= capacidade) {
            if (!aumentarCapacidade()) {
                System.out.println("[ERRO] Fila cheia. Pessoa não adicionada: " + pessoa.getNome());
                return false;
            }
        }

        int posicao = encontrarPosicaoInsercao(pessoa);
        deslocarElementos(posicao);
        pessoas[posicao] = pessoa;
        quantidade++;
        return true;
    }

    /**
     * Remove e retorna a pessoa com maior prioridade.
     * @return Pessoa removida ou null se a fila estiver vazia
     */
    public Pessoa removerPessoa() {
        if (estaVazia()) return null;
        
        Pessoa pessoa = pessoas[0];
        deslocarElementosParaEsquerda();
        quantidade--;
        return pessoa;
    }

    /**
     * Verifica se a fila está vazia.
     * @return true se a fila estiver vazia, false caso contrário
     */
    public boolean estaVazia() {
        return quantidade == 0;
    }

    /**
     * Retorna a quantidade atual de pessoas na fila.
     * @return Número de pessoas na fila
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Retorna uma cópia do array de pessoas.
     * @return Array contendo as pessoas na fila
     */
    public Pessoa[] toArray() {
        Pessoa[] copia = new Pessoa[quantidade];
        System.arraycopy(pessoas, 0, copia, 0, quantidade);
        return copia;
    }

    /**
     * Incrementa o tempo de espera de todas as pessoas na fila.
     */
    public void incrementarTempoEspera() {
        for (int i = 0; i < quantidade; i++) {
            if (pessoas[i] != null) {
                pessoas[i].incrementarTempoEspera();
            }
        }
    }

    /**
     * Imprime o conteúdo da fila.
     */
    public void imprimirFila() {
        System.out.println("[LOG] Fila de espera (" + quantidade + " pessoas):");
        for (int i = 0; i < quantidade; i++) {
            if (pessoas[i] != null) {
                System.out.println(" - " + pessoas[i]);
            }
        }
    }

    private boolean aumentarCapacidade() {
        try {
            int novaCapacidade = capacidade * 2;
            Pessoa[] novoArray = new Pessoa[novaCapacidade];
            System.arraycopy(pessoas, 0, novoArray, 0, quantidade);
            pessoas = novoArray;
            capacidade = novaCapacidade;
            return true;
        } catch (OutOfMemoryError e) {
            System.out.println("[ERRO] Não foi possível aumentar a capacidade da fila");
            return false;
        }
    }

    private int encontrarPosicaoInsercao(Pessoa novaPessoa) {
        int i = quantidade - 1;
        while (i >= 0 && comparaPrioridade(pessoas[i], novaPessoa) < 0) {
            i--;
        }
        return i + 1;
    }

    private void deslocarElementos(int posicao) {
        for (int i = quantidade; i > posicao; i--) {
            pessoas[i] = pessoas[i - 1];
        }
    }

    private void deslocarElementosParaEsquerda() {
        for (int i = 0; i < quantidade - 1; i++) {
            pessoas[i] = pessoas[i + 1];
        }
        pessoas[quantidade - 1] = null;
    }

    private int comparaPrioridade(Pessoa a, Pessoa b) {
        return Integer.compare(prioridadePessoa(b), prioridadePessoa(a));
    }

    private int prioridadePessoa(Pessoa p) {
        if (p == null) return 0;
        if (p.isCadeirante()) return 3;
        if (p.isIdoso()) return 2;
        return 1;
    }
}
