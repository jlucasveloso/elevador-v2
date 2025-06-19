public class PainelControle {
    private int[] botoesSubir;   // 0 = desligado, 1 = ligado
    private int[] botoesDescer;
    private int numeroAndares;

    // Construtor que recebe Configuracao (já existia)
    public PainelControle(Configuracao config) {
        this.numeroAndares = config.getNumeroAndares();
        this.botoesSubir = new int[numeroAndares];
        this.botoesDescer = new int[numeroAndares];
        for (int i = 0; i < numeroAndares; i++) {
            botoesSubir[i] = 0;
            botoesDescer[i] = 0;
        }
    }

    // Recebe número de andares diretamente
    public PainelControle(int numeroAndares) {
        this.numeroAndares = numeroAndares;
        this.botoesSubir = new int[numeroAndares];
        this.botoesDescer = new int[numeroAndares];
        for (int i = 0; i < numeroAndares; i++) {
            botoesSubir[i] = 0;
            botoesDescer[i] = 0;
        }
    }

    
    public void ativarBotaoSubir(int andar) {
        pressionarBotaoSubir(andar);
    }

    public void ativarBotaoDescer(int andar) {
        pressionarBotaoDescer(andar);
    }

    public void pressionarBotaoSubir(int andar) {
        if (andar >= 0 && andar < numeroAndares - 1 && botoesSubir[andar] == 0) {
            botoesSubir[andar] = 1;
            System.out.println("[LOG] Painel: Botão SUBIR pressionado no andar " + andar);
        }
    }

    public void pressionarBotaoDescer(int andar) {
        if (andar > 0 && andar < numeroAndares && botoesDescer[andar] == 0) {
            botoesDescer[andar] = 1;
            System.out.println("[LOG] Painel: Botão DESCER pressionado no andar " + andar);
        }
    }

    public void resetarBotaoSubir(int andar) {
        if (andar >= 0 && andar < numeroAndares && botoesSubir[andar] == 1) {
            botoesSubir[andar] = 0;
            System.out.println("[LOG] Painel: Botão SUBIR resetado no andar " + andar);
        }
    }

    public void resetarBotaoDescer(int andar) {
        if (andar >= 0 && andar < numeroAndares && botoesDescer[andar] == 1) {
            botoesDescer[andar] = 0;
            System.out.println("[LOG] Painel: Botão DESCER resetado no andar " + andar);
        }
    }

    public boolean isBotaoSubirPressionado(int andar) {
        return andar >= 0 && andar < numeroAndares && botoesSubir[andar] == 1;
    }

    public boolean isBotaoDescerPressionado(int andar) {
        return andar >= 0 && andar < numeroAndares && botoesDescer[andar] == 1;
    }

    public boolean isAndarSelecionado(int andar) {
        return isBotaoSubirPressionado(andar) || isBotaoDescerPressionado(andar);
    }

    public void selecionarAndar(int andar, String direcao) {
        if ("subir".equalsIgnoreCase(direcao)) {
            pressionarBotaoSubir(andar);
        } else if ("descer".equalsIgnoreCase(direcao)) {
            pressionarBotaoDescer(andar);
        } else {
            System.out.println("[AVISO] Direção inválida ao selecionar andar " + andar);
        }
    }

    public void selecionarAndar(int andar) {
        if (andar > 0 && andar < numeroAndares - 1) {
            pressionarBotaoSubir(andar);
            pressionarBotaoDescer(andar);
        } else if (andar == 0) {
            pressionarBotaoSubir(andar);
        } else if (andar == numeroAndares - 1) {
            pressionarBotaoDescer(andar);
        }
    }

    public void resetarAndar(int andar) {
        resetarBotaoSubir(andar);
        resetarBotaoDescer(andar);
    }

    public boolean devePararNoAndar(int andar, String direcao) {
        if ("subir".equalsIgnoreCase(direcao)) {
            return isBotaoSubirPressionado(andar);
        } else if ("descer".equalsIgnoreCase(direcao)) {
            return isBotaoDescerPressionado(andar);
        }
        return false;
    }

    public boolean isAndarChamado(int andar) {
        return isBotaoSubirPressionado(andar) || isBotaoDescerPressionado(andar);
    }

    public int getProximoAndarChamado(int andarAtual, boolean subindo) {
        if (subindo) {
            for (int i = andarAtual + 1; i < numeroAndares; i++) {
                if (isAndarChamado(i)) {
                    return i;
                }
            }
        } else {
            for (int i = andarAtual - 1; i >= 0; i--) {
                if (isAndarChamado(i)) {
                    return i;
                }
            }
        }
        return -1; // nenhum chamado na direção
    }

    public void imprimirStatus() {
        System.out.println("Botões ativos no painel:");
        for (int i = numeroAndares - 1; i >= 0; i--) {
            String subir = (botoesSubir[i] == 1) ? "[▲]" : "   ";
            String descer = (botoesDescer[i] == 1) ? "[▼]" : "   ";
            System.out.println("Andar " + i + ": " + subir + " " + descer);
        }
    }

    public void imprimirEstado() {
        imprimirStatus();
    }
}
