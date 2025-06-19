/**
 * Mantém um registro das mensagens da simulação.
 * Armazena as mensagens em um buffer circular com capacidade limitada.
 */
public class Log {
    private final String[] mensagens;
    private final int capacidade;
    private int tamanho;

    /**
     * Construtor do log.
     * @param capacidade Capacidade máxima de mensagens a serem armazenadas
     * @throws IllegalArgumentException se a capacidade for menor ou igual a zero
     */
    public Log(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade do log deve ser maior que zero");
        }
        this.capacidade = capacidade;
        this.mensagens = new String[capacidade];
        this.tamanho = 0;
    }

    /**
     * Adiciona uma mensagem ao log.
     * Se o log estiver cheio, a mensagem mais antiga é removida.
     * @param mensagem Mensagem a ser adicionada
     */
    public void adicionar(String mensagem) {
        if (mensagem == null || mensagem.trim().isEmpty()) {
            return;
        }

        if (tamanho < capacidade) {
            mensagens[tamanho] = mensagem;
            tamanho++;
        } else {
            // Desloca todas as mensagens uma posição para a esquerda
            System.arraycopy(mensagens, 1, mensagens, 0, capacidade - 1);
            mensagens[capacidade - 1] = mensagem;
        }
    }

    /**
     * Imprime todas as mensagens do log.
     */
    public void imprimir() {
        System.out.println("\n=== LOG DE SIMULAÇÃO ===");
        for (int i = 0; i < tamanho; i++) {
            System.out.println(mensagens[i]);
        }
        System.out.println("=== FIM DO LOG ===\n");
    }

    /**
     * Retorna o número de mensagens no log.
     * @return Quantidade de mensagens
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * Verifica se o log está vazio.
     * @return true se não houver mensagens, false caso contrário
     */
    public boolean estaVazio() {
        return tamanho == 0;
    }

    /**
     * Limpa todas as mensagens do log.
     */
    public void limpar() {
        for (int i = 0; i < tamanho; i++) {
            mensagens[i] = null;
        }
        tamanho = 0;
    }
}
