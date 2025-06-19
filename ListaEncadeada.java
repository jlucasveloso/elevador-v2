/**
 * Implementação de uma lista encadeada simples.
 * Mantém uma sequência de pessoas em ordem de inserção.
 */
public class ListaEncadeada {
    /**
     * Classe que representa um nó da lista encadeada.
     */
    private class Nodo {
        private final Pessoa pessoa;
        private Nodo proximo;

        private Nodo(Pessoa pessoa) {
            this.pessoa = pessoa;
            this.proximo = null;
        }
    }

    private Nodo inicio;
    private Nodo fim;
    private int tamanho;

    /**
     * Construtor da lista encadeada.
     */
    public ListaEncadeada() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    /**
     * Adiciona uma pessoa ao final da lista.
     * @param pessoa Pessoa a ser adicionada
     * @return true se a adição foi bem sucedida, false caso contrário
     */
    public boolean adicionarPessoa(Pessoa pessoa) {
        if (pessoa == null) return false;

        Nodo novo = new Nodo(pessoa);
        
        if (estaVazia()) {
            inicio = novo;
        } else {
            fim.proximo = novo;
        }
        fim = novo;
        tamanho++;
        
        System.out.println("[LOG] Pessoa adicionada à lista encadeada: " + pessoa.getNome());
        return true;
    }

    /**
     * Remove e retorna a primeira pessoa da lista.
     * @return Pessoa removida ou null se a lista estiver vazia
     */
    public Pessoa removerPessoa() {
        if (estaVazia()) return null;

        Pessoa pessoa = inicio.pessoa;
        inicio = inicio.proximo;
        
        if (inicio == null) {
            fim = null;
        }
        
        tamanho--;
        System.out.println("[LOG] Pessoa removida da lista encadeada: " + pessoa.getNome());
        return pessoa;
    }

    /**
     * Verifica se a lista está vazia.
     * @return true se a lista estiver vazia, false caso contrário
     */
    public boolean estaVazia() {
        return inicio == null;
    }

    /**
     * Retorna o tamanho atual da lista.
     * @return Número de pessoas na lista
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * Verifica se a lista contém uma pessoa específica.
     * @param pessoa Pessoa a ser verificada
     * @return true se a pessoa estiver na lista, false caso contrário
     */
    public boolean contem(Pessoa pessoa) {
        if (pessoa == null) return false;

        Nodo atual = inicio;
        while (atual != null) {
            if (atual.pessoa.equals(pessoa)) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    /**
     * Limpa a lista, removendo todos os elementos.
     */
    public void limpar() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    /**
     * Retorna um array contendo todas as pessoas da lista.
     * @return Array com as pessoas da lista
     */
    public Pessoa[] toArray() {
        Pessoa[] array = new Pessoa[tamanho];
        Nodo atual = inicio;
        int i = 0;
        
        while (atual != null) {
            array[i++] = atual.pessoa;
            atual = atual.proximo;
        }
        
        return array;
    }
}
