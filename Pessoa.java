/**
 * Representa uma pessoa no sistema de elevadores.
 * Mantém informações sobre características físicas, localização e tempo de espera.
 */
public class Pessoa {
    private static final int IDADE_IDOSO = 60;
    
    private final String nome;
    private final int idade;
    private final boolean cadeirante;
    private final int peso;
    private final int andarOrigem;
    private final int andarDestino;
    private int tempoEspera;

    /**
     * Construtor da classe Pessoa.
     * @param nome Nome da pessoa
     * @param idade Idade da pessoa
     * @param cadeirante Indica se a pessoa é cadeirante
     * @param peso Peso da pessoa em kg
     * @param andarOrigem Andar de origem
     * @param andarDestino Andar de destino
     */
    public Pessoa(String nome, int idade, boolean cadeirante, int peso, int andarOrigem, int andarDestino) {
        this.nome = nome;
        this.idade = idade;
        this.cadeirante = cadeirante;
        this.peso = peso;
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
        this.tempoEspera = 0;
    }

    /**
     * Retorna o nome da pessoa.
     * @return Nome da pessoa
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a idade da pessoa.
     * @return Idade da pessoa
     */
    public int getIdade() {
        return idade;
    }

    /**
     * Verifica se a pessoa é cadeirante.
     * @return true se a pessoa é cadeirante, false caso contrário
     */
    public boolean isCadeirante() {
        return cadeirante;
    }

    /**
     * Retorna o peso da pessoa.
     * @return Peso em kg
     */
    public int getPeso() {
        return peso;
    }

    /**
     * Retorna o andar de origem da pessoa.
     * @return Andar de origem
     */
    public int getAndarOrigem() {
        return andarOrigem;
    }

    /**
     * Retorna o andar de destino da pessoa.
     * @return Andar de destino
     */
    public int getAndarDestino() {
        return andarDestino;
    }

    /**
     * Verifica se a pessoa tem prioridade no sistema.
     * @return true se a pessoa é idosa ou cadeirante, false caso contrário
     */
    public boolean isPrioridade() {
        return isIdoso() || cadeirante;
    }

    /**
     * Verifica se a pessoa é idosa.
     * @return true se a pessoa tem 60 anos ou mais, false caso contrário
     */
    public boolean isIdoso() {
        return idade >= IDADE_IDOSO;
    }

    /**
     * Retorna o texto descritivo da prioridade da pessoa.
     * @return "Cadeirante", "Idoso" ou "Normal"
     */
    public String getPrioridadeTexto() {
        if (isCadeirante()) return "Cadeirante";
        if (isIdoso()) return "Idoso";
        return "Normal";
    }

    /**
     * Retorna o tempo de espera da pessoa.
     * @return Tempo de espera em minutos
     */
    public int getTempoEspera() {
        return tempoEspera;
    }

    /**
     * Incrementa o tempo de espera da pessoa em 1 minuto.
     */
    public void incrementarTempoEspera() {
        tempoEspera++;
    }

    @Override
    public String toString() {
        return String.format("%s (idade: %d, peso: %d, origem: %d, destino: %d%s, espera: %dmin)",
            nome, idade, peso, andarOrigem, andarDestino,
            isPrioridade() ? ", PRIORITÁRIA" : "",
            tempoEspera);
    }
}
