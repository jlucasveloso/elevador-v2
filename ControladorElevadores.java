public class ControladorElevadores {
    private Configuracao config;
    private Elevador[] elevadores;
    private FilaPrioridadeDupla[] filas;
    private PainelControle painelControle;
    private ResumoSimulacao resumo;
    private int tempoAtual;
    private int contadorPessoas;

    public ControladorElevadores(Configuracao config) {
        this.config = config;
        this.tempoAtual = 0;
        this.contadorPessoas = 1;
        this.resumo = new ResumoSimulacao();

        this.elevadores = new Elevador[config.getNumeroElevadores()];
        for (int i = 0; i < elevadores.length; i++) {
            elevadores[i] = new Elevador(i + 1, config, resumo);
        }

        this.filas = new FilaPrioridadeDupla[config.getNumeroAndares()];
        for (int i = 0; i < config.getNumeroAndares(); i++) {
            filas[i] = new FilaPrioridadeDupla(100);
        }

        this.painelControle = new PainelControle(config);
    }

    public void simularCiclo() {
        // 1. Incrementa tempo de espera de todas as pessoas nas filas
        for (FilaPrioridadeDupla fila : filas) {
            fila.incrementarTempoEspera();
        }

        // 2. Movimenta os elevadores
        double energiaGastaCiclo = 0;
        for (Elevador elevador : elevadores) {
            elevador.atenderProximo(filas);
            energiaGastaCiclo += elevador.getEnergiaGastaCiclo();
        }

        // Mostra o gasto de energia do ciclo
        System.out.printf("\n[ENERGIA] Gasto no ciclo atual: %.2f unidades\n", energiaGastaCiclo);

        // 3. Atualiza o painel de controle
        atualizarPainelControle();

        tempoAtual++;
    }

    public void gerarPessoas() {
        // Verifica se é horário de pico (7-9h ou 17-19h)
        int hora = (tempoAtual / 60) % 24;
        boolean horarioPico = (hora >= 7 && hora <= 9) || (hora >= 17 && hora <= 19);
        
        // Gera 1 pessoa em horário normal e 2 em horário de pico
        int quantidade = horarioPico ? 2 : 1;
        
        for (int i = 0; i < quantidade; i++) {
            int andarOrigem = (int)(Math.random() * config.getNumeroAndares());
            int andarDestino = (int)(Math.random() * config.getNumeroAndares());
            while (andarDestino == andarOrigem) {
                andarDestino = (int)(Math.random() * config.getNumeroAndares());
            }

            int idade = 10 + (int)(Math.random() * 70); // 10 a 80 anos
            boolean cadeirante = Math.random() < 0.1;   // 10% cadeirantes
            int peso = 40 + (int)(Math.random() * 60);  // 40 a 100 kg
            String nome = "P" + contadorPessoas++;

            Pessoa nova = new Pessoa(nome, idade, cadeirante, peso, andarOrigem, andarDestino);
            resumo.registrarPassageiroGerado();

            // Mostra informações detalhadas da pessoa gerada em uma única linha
            String tipo = cadeirante ? "Cadeirante" : (idade >= 60 ? "Idoso" : "Normal");
            System.out.printf("[NOVA PESSOA] %s | %d anos | %s | Origem: %d | Destino: %d | Peso: %d kg\n", 
                            nome, idade, tipo, andarOrigem, andarDestino, peso);

            if (filas[andarOrigem].inserir(nova)) {
                if (andarDestino > andarOrigem) {
                    painelControle.ativarBotaoSubir(andarOrigem);
                } else {
                    painelControle.ativarBotaoDescer(andarOrigem);
                }
            } else {
                System.out.println("[AVISO] Não foi possível adicionar " + nome + " à fila do andar " + andarOrigem);
            }
        }
    }

    private void atualizarPainelControle() {
        for (int andar = 0; andar < config.getNumeroAndares(); andar++) {
            // Se existem pessoas querendo subir neste andar
            if (!filas[andar].estaVaziaSubir()) {
                painelControle.ativarBotaoSubir(andar);
            } else {
                painelControle.resetarBotaoSubir(andar);
            }
            // Se existem pessoas querendo descer neste andar
            if (!filas[andar].estaVaziaDescer()) {
                painelControle.ativarBotaoDescer(andar);
            } else {
                painelControle.resetarBotaoDescer(andar);
            }
        }
    }

    public Elevador[] getElevadores() {
        return elevadores;
    }

    public FilaPrioridadeDupla[] getFilas() {
        return filas;
    }

    public PainelControle getPainelControle() {
        return painelControle;
    }

    public ResumoSimulacao getResumo() {
        return resumo;
    }

    public void imprimirResumoFinal() {
        resumo.imprimirResumo();
    }
}
