public class Main {
    public static void main(String[] args) {
        Configuracao config = new Configuracao();
        ControladorElevadores controlador = new ControladorElevadores(config);
        PainelAscii painel = new PainelAscii(config, controlador);

        final int totalMinutos = 1440; 
        
        System.out.println("=== INÍCIO DA SIMULAÇÃO DO ELEVADOR ===");
        System.out.println("Simulando um dia inteiro (1440 minutos)");
        System.out.println("Pressione ENTER para iniciar...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignora exceção
        }

        // Cria arquivo de log
        try (java.io.PrintWriter log = new java.io.PrintWriter("simulacao_elevador.log")) {
            for (int minuto = 0; minuto < totalMinutos; minuto++) {
                StringBuilder ciclo = new StringBuilder();
                
                ciclo.append("═══════════════════════════════════════════════════════════════════════════════\n");
                

                int hora = (minuto / 60) % 24;
                int minutoHora = minuto % 60;
                String horarioFormatado = String.format("%02d:%02d", hora, minutoHora);
                boolean horarioPico = isHorarioPico(hora);
                
                ciclo.append("CICLO " + minuto + " | HORÁRIO: " + horarioFormatado + " | " + 
                             (horarioPico ? "HORÁRIO DE PICO" : "HORÁRIO NORMAL") + "\n\n");

               
                controlador.gerarPessoas();

                
                ciclo.append("=== STATUS DOS ELEVADORES ===\n");
                double energiaGastaCiclo = 0;
                for (Elevador e : controlador.getElevadores()) {
                    ciclo.append("Elevador " + e.getId() + 
                        " | Andar: " + e.getAndarAtual() + 
                        " | Direção: " + e.getDirecaoTexto() + 
                        " | Passageiros: " + e.getNumPassageiros() + "/" + e.getCapacidade() + 
                        " | Peso: " + e.getPesoAtual() + "/" + e.getPesoMaximo() + "\n");
                    energiaGastaCiclo += e.getEnergiaGastaCiclo();
                }
                ciclo.append("Energia gasta neste ciclo: " + String.format("%.2f", energiaGastaCiclo) + " unidades\n");
                ciclo.append("Energia total gasta: " + String.format("%.2f", controlador.getResumo().getEnergiaGastaTotal()) + " unidades\n\n");

                
                controlador.simularCiclo();

                ciclo.append("=== PAINEL ATUAL ===\n\n");
                ciclo.append(painel.getPainelAsString());
                ciclo.append("\n");

                ciclo.append("═══════════════════════════════════════════════════════════════════════════════\n\n");

                log.write(ciclo.toString());
                log.flush();

                System.out.println(ciclo.toString());
                System.out.flush();

                if (minuto % 100 == 0) {
                    System.out.println("Progresso: " + (minuto * 100 / totalMinutos) + "%");
                }
 }
        } catch (Exception e) {
            System.out.println("Erro ao salvar log: " + e.getMessage());
        }
        
        System.out.println("=== RESUMO FINAL DA SIMULAÇÃO ===");
        System.out.println();
        
        controlador.imprimirResumoFinal();
        
        System.out.println("\nA simulação completa foi salva no arquivo 'simulacao_elevador.log'");
        System.out.println("Você pode abrir este arquivo para ver todos os ciclos da simulação.");
    }

    private static boolean isHorarioPico(int hora) {
        return (hora >= 7 && hora <= 9) || (hora >= 17 && hora <= 19);
    }
}
