/**
 * Mantém as configurações do sistema de elevadores.
 * Define parâmetros como número de andares, capacidade dos elevadores e tempos de operação.
 */
public class Configuracao {
    // Valores padrão do sistema
    private static final int NUMERO_ANDARES_PADRAO = 10;
    private static final int NUMERO_ELEVADORES_PADRAO = 2;
    private static final int CAPACIDADE_ELEVADOR_PADRAO = 10;
    private static final int TEMPO_MINIMO_VIAGEM_PADRAO = 5;
    private static final int TEMPO_MAXIMO_VIAGEM_PADRAO = 10;
    private static final int TEMPO_MAXIMO_ESPERA_PADRAO = 30;
    private static final int CONSUMO_ENERGIA_DESLOCAMENTO_PADRAO = 2;
    private static final int CONSUMO_ENERGIA_PARADA_PADRAO = 1;
    private static final int PESO_MAXIMO_ELEVADOR_PADRAO = 1000;

    // Configurações do sistema
    private final int numeroAndares;
    private final int numeroElevadores;
    private final int capacidadeElevador;
    private final int tempoMinimoViagem;
    private final int tempoMaximoViagem;
    private final int tempoMaximoEspera;
    private final int consumoEnergiaDeslocamento;
    private final int consumoEnergiaParada;
    private final int pesoMaximoElevador;

    /**
     * Construtor com valores padrão.
     */
    public Configuracao() {
        this.numeroAndares = NUMERO_ANDARES_PADRAO;
        this.numeroElevadores = NUMERO_ELEVADORES_PADRAO;
        this.capacidadeElevador = CAPACIDADE_ELEVADOR_PADRAO;
        this.tempoMinimoViagem = TEMPO_MINIMO_VIAGEM_PADRAO;
        this.tempoMaximoViagem = TEMPO_MAXIMO_VIAGEM_PADRAO;
        this.tempoMaximoEspera = TEMPO_MAXIMO_ESPERA_PADRAO;
        this.consumoEnergiaDeslocamento = CONSUMO_ENERGIA_DESLOCAMENTO_PADRAO;
        this.consumoEnergiaParada = CONSUMO_ENERGIA_PARADA_PADRAO;
        this.pesoMaximoElevador = PESO_MAXIMO_ELEVADOR_PADRAO;
    }

    /**
     * Retorna o número de andares do prédio.
     * @return Número de andares
     */
    public int getNumeroAndares() {
        return numeroAndares;
    }

    /**
     * Retorna o número de elevadores no sistema.
     * @return Número de elevadores
     */
    public int getNumeroElevadores() {
        return numeroElevadores;
    }

    /**
     * Retorna a capacidade máxima de passageiros por elevador.
     * @return Capacidade em número de pessoas
     */
    public int getCapacidadeMaximaPassageiros() {
        return capacidadeElevador;
    }

    /**
     * Retorna a capacidade máxima de peso por elevador.
     * @return Capacidade em kg
     */
    public int getCapacidadeMaximaPeso() {
        return pesoMaximoElevador;
    }

    /**
     * Retorna o peso máximo suportado pelo elevador.
     * @return Peso máximo em kg
     */
    public int getPesoMaximo() {
        return pesoMaximoElevador;
    }

    /**
     * Retorna o tempo mínimo de viagem entre andares.
     * @return Tempo em segundos
     */
    public int getTempoMinimoViagem() {
        return tempoMinimoViagem;
    }

    /**
     * Retorna o tempo máximo de viagem entre andares.
     * @return Tempo em segundos
     */
    public int getTempoMaximoViagem() {
        return tempoMaximoViagem;
    }

    /**
     * Retorna o tempo máximo de espera permitido.
     * @return Tempo em minutos
     */
    public int getTempoMaximoEspera() {
        return tempoMaximoEspera;
    }

    /**
     * Retorna o consumo de energia por andar percorrido.
     * @return Consumo em unidades de energia
     */
    public int getConsumoPorAndar() {
        return consumoEnergiaDeslocamento;
    }

    /**
     * Retorna o consumo de energia por parada.
     * @return Consumo em unidades de energia
     */
    public int getConsumoPorParada() {
        return consumoEnergiaParada;
    }

    /**
     * Retorna o número do último andar.
     * @return Número do último andar (número de andares - 1)
     */
    public int getAndarMaximo() {
        return numeroAndares - 1;
    }
}
