public class PainelAscii {
    private int numeroAndares;
    private Elevador[] elevadores;
    private FilaPrioridadeDupla[] filas;

    public PainelAscii(Configuracao config, ControladorElevadores controlador) {
        this.numeroAndares = config.getNumeroAndares();
        this.elevadores = controlador.getElevadores();
        this.filas = controlador.getFilas();
    }

    public void imprimirPainel() {
        System.out.println(getPainelAsString());
    }

    public String getPainelAsString() {
        StringBuilder sb = new StringBuilder();
        
        // Cabeçalho com bordas e largura fixas por coluna
        sb.append("╔════════╦" + repetir("════════╦", elevadores.length - 1) + "════════╦==========================================╗\n");
        sb.append("║ Andar  "); // 8 caracteres fixos para andar
        for (int i = 0; i < elevadores.length; i++) {
            sb.append(String.format("║  E%-4d", (i + 1))); // 8 chars cada coluna elevador
        }
        sb.append("║    Chamadas            ║\n");
        sb.append("╠════════╬" + repetir("════════╬", elevadores.length - 1) + "════════╬==========================================╣\n");

        for (int andar = numeroAndares - 1; andar >= 0; andar--) {
            StringBuilder linha = new StringBuilder();
           
            linha.append(String.format("║  %2d    ", andar));

            for (int i = 0; i < elevadores.length; i++) {
                Elevador e = elevadores[i];
                if (e.getAndarAtual() == andar) {
                    int dirInt = e.getDirecao();  // Corrigido para int
                    String dir;
                    if (dirInt == 1) dir = "↑";
                    else if (dirInt == -1) dir = "↓";
                    else dir = " ";  // parado ou sem direção definida

                    dir = substituirSeta(dir); // transforma ↑ em ^ e ↓ em v

                    String textoElevador = String.format("%s(%d)", dir, e.getNumPassageiros());
                    // Cada coluna elevador tem 8 caracteres incluindo borda e espaços
                    linha.append(String.format("║  %-5s ", textoElevador));
                } else {
                    linha.append("║        "); // 8 espaços para coluna vazia
                }
            }

            String chamadasEspera = gerarResumoFila(filas[andar].getArraySubir(), "^") +
                                   gerarResumoFila(filas[andar].getArrayDescer(), "v");
            // Limita tamanho do texto da coluna chamada para 40 caracteres
            if (chamadasEspera.length() > 40) {
                chamadasEspera = chamadasEspera.substring(0, 37) + "...";
            }
            linha.append("║ ");
            linha.append(String.format("%-40s", chamadasEspera.trim())); // 40 chars largura para chamadas
            linha.append(" ║");

            sb.append(linha.toString()).append("\n");
        }

        sb.append("╚════════╩" + repetir("════════╩", elevadores.length - 1) + "════════╩==========================================╝\n");
        
        return sb.toString();
    }

    // Método para substituir setas Unicode por ASCII simples
    private String substituirSeta(String direcao) {
        if (direcao == null) return " ";
        switch (direcao) {
            case "↑": return "^";
            case "↓": return "v";
            default: return " ";
        }
    }

   
    private String gerarResumoFila(Pessoa[] pessoas, String direcao) {
        if (pessoas == null || pessoas.length == 0) return "";

        int prioridade = 0;
        int comuns = 0;

        for (Pessoa p : pessoas) {
            if (p != null && (p.isIdoso() || p.isCadeirante())) {
                prioridade++;
            } else if (p != null) {
                comuns++;
            }
        }

        StringBuilder resultado = new StringBuilder();
        if (prioridade > 0) resultado.append(direcao).append("*").append(prioridade).append(" ");
        if (comuns > 0) resultado.append(direcao).append("(").append(comuns).append(") ");
        return resultado.toString();
    }

    private String repetir(String s, int vezes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vezes; i++) sb.append(s);
        return sb.toString();
    }
}
