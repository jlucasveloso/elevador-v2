/**
 * Implementação de uma fila dupla de prioridade.
 * Mantém duas filas separadas para pessoas subindo e descendo.
 */
public class FilaPrioridadeDupla {
    private static final int CAPACIDADE_PADRAO = 10;
    private final FilaPrioridade filaSubir;
    private final FilaPrioridade filaDescer;

    /**
     * Construtor da fila dupla de prioridade.
     * @param capacidade Capacidade inicial de cada fila
     */
    public FilaPrioridadeDupla(int capacidade) {
        int capacidadeFinal = capacidade > 0 ? capacidade : CAPACIDADE_PADRAO;
        this.filaSubir = new FilaPrioridade(capacidadeFinal);
        this.filaDescer = new FilaPrioridade(capacidadeFinal);
    }

    /**
     * Insere uma pessoa na fila apropriada baseado em seu destino.
     * @param pessoa Pessoa a ser inserida
     * @return true se a inserção foi bem sucedida, false caso contrário
     */
    public boolean inserir(Pessoa pessoa) {
        if (pessoa == null) return false;

        if (pessoa.getAndarDestino() > pessoa.getAndarOrigem()) {
            return filaSubir.inserir(pessoa);
        } else {
            return filaDescer.inserir(pessoa);
        }
    }

    /**
     * Remove e retorna a próxima pessoa da fila de subida.
     * @return Pessoa removida ou null se a fila estiver vazia
     */
    public Pessoa removerPessoaSubir() {
        return filaSubir.removerPessoa();
    }

    /**
     * Remove e retorna a próxima pessoa da fila de descida.
     * @return Pessoa removida ou null se a fila estiver vazia
     */
    public Pessoa removerPessoaDescer() {
        return filaDescer.removerPessoa();
    }

    /**
     * Verifica se a fila de subida está vazia.
     * @return true se a fila estiver vazia, false caso contrário
     */
    public boolean estaVaziaSubir() {
        return filaSubir.estaVazia();
    }

    /**
     * Verifica se a fila de descida está vazia.
     * @return true se a fila estiver vazia, false caso contrário
     */
    public boolean estaVaziaDescer() {
        return filaDescer.estaVazia();
    }

    /**
     * Verifica se há pessoas esperando em qualquer uma das filas.
     * @return true se houver pessoas esperando, false caso contrário
     */
    public boolean temPessoasEsperando() {
        return !filaSubir.estaVazia() || !filaDescer.estaVazia();
    }

    /**
     * Retorna uma cópia do array de pessoas da fila de subida.
     * @return Array contendo as pessoas na fila de subida
     */
    public Pessoa[] getArraySubir() {
        return filaSubir.toArray();
    }

    /**
     * Retorna uma cópia do array de pessoas da fila de descida.
     * @return Array contendo as pessoas na fila de descida
     */
    public Pessoa[] getArrayDescer() {
        return filaDescer.toArray();
    }

    /**
     * Incrementa o tempo de espera de todas as pessoas em ambas as filas.
     */
    public void incrementarTempoEspera() {
        filaSubir.incrementarTempoEspera();
        filaDescer.incrementarTempoEspera();
    }

    /**
     * Conta quantas pessoas estão esperando para subir em um andar específico.
     * @param andar Andar a ser verificado
     * @return Número de pessoas esperando para subir no andar
     */
    public int contarPessoasSubirNoAndar(int andar) {
        return contarPessoasNoAndar(filaSubir.toArray(), andar);
    }

    /**
     * Conta quantas pessoas estão esperando para descer em um andar específico.
     * @param andar Andar a ser verificado
     * @return Número de pessoas esperando para descer no andar
     */
    public int contarPessoasDescerNoAndar(int andar) {
        return contarPessoasNoAndar(filaDescer.toArray(), andar);
    }

    /**
     * Imprime o conteúdo de ambas as filas.
     */
    public void imprimirFilas() {
        System.out.println("[LOG] Fila de subir:");
        filaSubir.imprimirFila();
        System.out.println("[LOG] Fila de descer:");
        filaDescer.imprimirFila();
    }

    private int contarPessoasNoAndar(Pessoa[] pessoas, int andar) {
        int count = 0;
        for (Pessoa p : pessoas) {
            if (p != null && p.getAndarOrigem() == andar) {
                count++;
            }
        }
        return count;
    }
}
