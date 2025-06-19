/**
 * Representa um elevador no sistema de simulação.
 * Gerencia o movimento, embarque e desembarque de passageiros.
 */
public class Elevador {
    // Constantes para direção do elevador
    private static final int DIRECAO_DESCENDO = -1;
    private static final int DIRECAO_PARADO = 0;
    private static final int DIRECAO_SUBINDO = 1;

    // Atributos de identificação e configuração
    private final int id;
    private final Configuracao config;
    private final ResumoSimulacao resumo;

    // Atributos de estado
    private int andarAtual;
    private int direcao;
    private double energiaGastaCiclo;

    // Atributos relacionados aos passageiros
    private final Pessoa[] passageiros;
    private int numPassageiros;
    private final int capacidade;
    private final int pesoMaximo;
    private int pesoAtual;

    /**
     * Construtor do elevador.
     * @param id Identificador único do elevador
     * @param config Configurações do sistema
     * @param resumo Objeto para registro de estatísticas
     */
    public Elevador(int id, Configuracao config, ResumoSimulacao resumo) {
        this.id = id;
        this.config = config;
        this.resumo = resumo;
        this.andarAtual = 0;
        this.direcao = DIRECAO_PARADO;
        this.capacidade = config.getCapacidadeMaximaPassageiros();
        this.pesoMaximo = config.getCapacidadeMaximaPeso();
        this.passageiros = new Pessoa[capacidade];
        this.numPassageiros = 0;
        this.pesoAtual = 0;
        this.energiaGastaCiclo = 0;
    }

    /**
     * Atende a próxima chamada do elevador, realizando embarque e desembarque.
     * @param filas Array de filas de espera por andar
     */
    public void atenderProximo(FilaPrioridadeDupla[] filas) {
        energiaGastaCiclo = 0;
        exibirStatusAtual();

        int desembarcados = desembarcarPassageiros();
        if (desembarcados > 0) {
            registrarParada();
            System.out.println("Desembarcaram " + desembarcados + " passageiros no andar " + andarAtual);
        }

        int embarcados = embarcarPassageiros(filas);
        if (embarcados > 0) {
            registrarParada();
            System.out.println("Embarcaram " + embarcados + " passageiros no andar " + andarAtual);
        }

        if (numPassageiros == 0) {
            int proximoAndar = encontrarProximoAndar(filas);
            if (proximoAndar != -1) {
                moverParaAndar(proximoAndar);
            } else {
                System.out.println("Nenhuma chamada pendente");
            }
        } else {
            atualizarDirecao();
            if (direcao != DIRECAO_PARADO) {
                mover();
            }
        }
    }

    private void exibirStatusAtual() {
        System.out.println("\n=== Elevador " + id + " ===");
        System.out.println("Andar atual: " + andarAtual + " | Direção: " + getDirecaoTexto());
        System.out.println("Passageiros: " + numPassageiros + "/" + capacidade + " | Peso: " + pesoAtual + "/" + pesoMaximo);
    }

    private void registrarParada() {
        resumo.registrarEnergiaGasta(config.getConsumoPorParada());
    }

    private int desembarcarPassageiros() {
        int desembarcados = 0;
        for (int i = 0; i < numPassageiros; i++) {
            if (passageiros[i] != null && passageiros[i].getAndarDestino() == andarAtual) {
                resumo.registrarDesembarque();
                pesoAtual -= passageiros[i].getPeso();
                passageiros[i] = null;
                desembarcados++;
            }
        }
        reorganizarPassageiros();
        return desembarcados;
    }

    private void reorganizarPassageiros() {
        int posicaoAtual = 0;
        for (int i = 0; i < numPassageiros; i++) {
            if (passageiros[i] != null) {
                passageiros[posicaoAtual] = passageiros[i];
                posicaoAtual++;
            }
        }
        numPassageiros = posicaoAtual;
    }

    private int embarcarPassageiros(FilaPrioridadeDupla[] filas) {
        FilaPrioridadeDupla filaAndar = filas[andarAtual];
        int embarcados = 0;
        
        while (podeEmbarcarMaisPassageiros(filaAndar)) {
            Pessoa passageiro = obterProximoPassageiro(filaAndar);
            if (passageiro == null) break;
            
            if (podeAdicionarPassageiro(passageiro)) {
                adicionarPassageiro(passageiro);
                embarcados++;
            } else {
                filaAndar.inserir(passageiro);
                break;
            }
        }
        return embarcados;
    }

    private boolean podeEmbarcarMaisPassageiros(FilaPrioridadeDupla filaAndar) {
        return numPassageiros < capacidade && 
               (!filaAndar.estaVaziaSubir() || !filaAndar.estaVaziaDescer());
    }

    private Pessoa obterProximoPassageiro(FilaPrioridadeDupla filaAndar) {
        Pessoa passageiro = filaAndar.removerPessoaSubir();
        return passageiro != null ? passageiro : filaAndar.removerPessoaDescer();
    }

    private boolean podeAdicionarPassageiro(Pessoa passageiro) {
        return pesoAtual + passageiro.getPeso() <= pesoMaximo;
    }

    private void adicionarPassageiro(Pessoa passageiro) {
        passageiros[numPassageiros++] = passageiro;
        pesoAtual += passageiro.getPeso();
        resumo.registrarEmbarque(passageiro.getTempoEspera());
    }

    private int encontrarProximoAndar(FilaPrioridadeDupla[] filas) {
        int melhorAndar = encontrarAndarComPrioridade(filas);
        if (melhorAndar == -1) {
            melhorAndar = encontrarAndarMaisProximo(filas);
        }
        return melhorAndar;
    }

    private int encontrarAndarComPrioridade(FilaPrioridadeDupla[] filas) {
        int melhorAndar = -1;
        int menorDistancia = Integer.MAX_VALUE;
        
        for (int andar = 0; andar < filas.length; andar++) {
            if (filas[andar].temPessoasEsperando() && temPessoaPrioritaria(filas[andar])) {
                int distancia = Math.abs(andar - andarAtual);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    melhorAndar = andar;
                }
            }
        }
        return melhorAndar;
    }

    private boolean temPessoaPrioritaria(FilaPrioridadeDupla fila) {
        for (Pessoa p : fila.getArraySubir()) {
            if (p != null && p.isPrioridade()) return true;
        }
        for (Pessoa p : fila.getArrayDescer()) {
            if (p != null && p.isPrioridade()) return true;
        }
        return false;
    }

    private int encontrarAndarMaisProximo(FilaPrioridadeDupla[] filas) {
        int melhorAndar = -1;
        int menorDistancia = Integer.MAX_VALUE;
        
        for (int andar = 0; andar < filas.length; andar++) {
            if (filas[andar].temPessoasEsperando()) {
                int distancia = Math.abs(andar - andarAtual);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    melhorAndar = andar;
                }
            }
        }
        return melhorAndar;
    }

    private void atualizarDirecao() {
        if (numPassageiros == 0) {
            direcao = DIRECAO_PARADO;
            return;
        }

        boolean temParaCima = false;
        boolean temParaBaixo = false;

        for (int i = 0; i < numPassageiros; i++) {
            if (passageiros[i] != null) {
                if (passageiros[i].getAndarDestino() > andarAtual) {
                    temParaCima = true;
                } else if (passageiros[i].getAndarDestino() < andarAtual) {
                    temParaBaixo = true;
                }
            }
        }

        atualizarDirecaoBaseadoDestinos(temParaCima, temParaBaixo);
    }

    private void atualizarDirecaoBaseadoDestinos(boolean temParaCima, boolean temParaBaixo) {
        if (direcao == DIRECAO_PARADO) {
            direcao = temParaCima ? DIRECAO_SUBINDO : (temParaBaixo ? DIRECAO_DESCENDO : DIRECAO_PARADO);
        } else if (direcao == DIRECAO_SUBINDO && !temParaCima) {
            direcao = temParaBaixo ? DIRECAO_DESCENDO : DIRECAO_PARADO;
        } else if (direcao == DIRECAO_DESCENDO && !temParaBaixo) {
            direcao = temParaCima ? DIRECAO_SUBINDO : DIRECAO_PARADO;
        }
    }

    private void mover() {
        if (direcao == DIRECAO_PARADO) return;

        int tempoViagem = calcularTempoViagem();
        exibirMensagemMovimento(tempoViagem);

        andarAtual += direcao;
        registrarConsumoEnergia();
    }

    private int calcularTempoViagem() {
        return config.getTempoMinimoViagem() + 
               (int)(Math.random() * (config.getTempoMaximoViagem() - config.getTempoMinimoViagem()));
    }

    private void exibirMensagemMovimento(int tempoViagem) {
        System.out.printf("[ELEVADOR %d] Movendo do andar %d para o andar %d (tempo estimado: %d segundos)\n", 
                         id, andarAtual, andarAtual + direcao, tempoViagem);
    }

    private void registrarConsumoEnergia() {
        energiaGastaCiclo += config.getConsumoPorAndar();
        resumo.registrarEnergiaGasta(config.getConsumoPorAndar());
        resumo.registrarViagem();
    }

    private void moverParaAndar(int destino) {
        int tempoTotalViagem = 0;
        
        while (andarAtual != destino) {
            direcao = destino > andarAtual ? DIRECAO_SUBINDO : DIRECAO_DESCENDO;
            int tempoViagem = calcularTempoViagem();
            tempoTotalViagem += tempoViagem;
            
            exibirMensagemMovimento(tempoViagem);
            mover();
        }
        
        System.out.printf("[ELEVADOR %d] Chegou ao destino (andar %d) em %d segundos\n", 
                         id, destino, tempoTotalViagem);
        
        registrarParada();
    }

    // Getters
    public int getId() { return id; }
    public int getAndarAtual() { return andarAtual; }
    public int getDirecao() { return direcao; }
    public String getDirecaoTexto() { 
        return direcao == DIRECAO_SUBINDO ? "Subindo" : 
               (direcao == DIRECAO_DESCENDO ? "Descendo" : "Parado"); 
    }
    public int getNumPassageiros() { return numPassageiros; }
    public int getPesoAtual() { return pesoAtual; }
    public Pessoa[] getPassageiros() { return passageiros; }
    public int getCapacidade() { return capacidade; }
    public int getPesoMaximo() { return pesoMaximo; }
    public double getEnergiaGastaCiclo() { return energiaGastaCiclo; }
}
