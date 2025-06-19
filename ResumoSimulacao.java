/**
 * Mantém estatísticas e métricas da simulação do sistema de elevadores.
 * Registra informações sobre passageiros, viagens, energia e tempos de espera.
 */
public class ResumoSimulacao {
    // Contadores de eventos
    private int totalPassageirosGerados;
    private int totalEmbarques;
    private int totalDesembarques;
    private int totalViagens;
    
    // Métricas de consumo e tempo
    private double energiaGastaTotal;
    private int tempoEsperaTotal;

    /**
     * Construtor do resumo da simulação.
     * Inicializa todos os contadores e métricas com zero.
     */
    public ResumoSimulacao() {
        resetarEstatisticas();
    }

    /**
     * Registra a geração de um novo passageiro.
     */
    public void registrarPassageiroGerado() {
        totalPassageirosGerados++;
    }

    /**
     * Registra o embarque de um passageiro.
     * @param tempoEspera Tempo de espera do passageiro em minutos
     */
    public void registrarEmbarque(int tempoEspera) {
        totalEmbarques++;
        tempoEsperaTotal += tempoEspera;
    }

    /**
     * Registra o desembarque de um passageiro.
     */
    public void registrarDesembarque() {
        totalDesembarques++;
    }

    /**
     * Registra uma viagem realizada por um elevador.
     */
    public void registrarViagem() {
        totalViagens++;
    }

    /**
     * Registra o consumo de energia.
     * @param energia Quantidade de energia gasta
     */
    public void registrarEnergiaGasta(double energia) {
        energiaGastaTotal += energia;
    }

    /**
     * Retorna o total de energia gasta na simulação.
     * @return Energia total em unidades
     */
    public double getEnergiaGastaTotal() {
        return energiaGastaTotal;
    }

    /**
     * Calcula e retorna o tempo médio de espera dos passageiros.
     * @return Tempo médio de espera em minutos
     */
    public double getTempoEsperaMedio() {
        if (totalEmbarques == 0) return 0.0;
        return (double) tempoEsperaTotal / totalEmbarques;
    }

    /**
     * Imprime um resumo completo das estatísticas da simulação.
     */
    public void imprimirResumo() {
        System.out.println("\n=== RESUMO DA SIMULAÇÃO ===");
        System.out.println("Total de passageiros gerados: " + totalPassageirosGerados);
        System.out.println("Total de embarques realizados: " + totalEmbarques);
        System.out.println("Total de desembarques realizados: " + totalDesembarques);
        System.out.println("Total de viagens realizadas pelos elevadores: " + totalViagens);
        System.out.printf("Energia total consumida: %.2f unidades\n", energiaGastaTotal);
        System.out.printf("Tempo médio de espera dos passageiros: %.2f minutos\n", getTempoEsperaMedio());
        System.out.println("===========================\n");
    }

    /**
     * Reseta todas as estatísticas para zero.
     */
    public void resetarEstatisticas() {
        totalPassageirosGerados = 0;
        totalEmbarques = 0;
        totalDesembarques = 0;
        totalViagens = 0;
        energiaGastaTotal = 0;
        tempoEsperaTotal = 0;
    }
}
