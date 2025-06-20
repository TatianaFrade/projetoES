package pt.ipleiria.estg.ei.dei.esoft;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe adaptadora para facilitar a migração entre o modelo antigo de Compra
 * e o novo modelo que usa listas de Bilhetes.
 */
public class CompraAdapter {
    
    /**
     * Converte as compras do formato antigo para o novo formato.
     * 
     * @param comprasAntigas Lista de compras no formato antigo
     * @param todasSessoes Lista de todas as sessões do sistema (para restaurar referências)
     * @return Lista de compras convertida para o formato novo
     */
    public static List<Compra> converterComprasAntigas(List<Compra> comprasAntigas, List<Sessao> todasSessoes) {
        if (comprasAntigas == null || comprasAntigas.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Compra> comprasNovas = new ArrayList<>();
        
        for (Compra compraAntiga : comprasAntigas) {
            // Verificar se é uma compra antiga (sem bilhetes)
            // Compras antigas têm idSessao e idLugar como campos diretos
            
            try {
                // Para compras apenas de itens do bar
                if (compraAntiga.getIdSessao() == null) {
                    // Criar uma nova compra somente com itens
                    Compra compraNova = new Compra(
                            new ArrayList<>(),                      // Lista vazia de bilhetes
                            compraAntiga.getItensBar(),             // Itens do bar
                            compraAntiga.getMetodoPagamento(),      // Método de pagamento
                            compraAntiga.getIdUsuario()             // ID do usuário
                    );
                    
                    comprasNovas.add(compraNova);
                    continue;
                }
                
                // Para compras com bilhetes
                String idSessao = compraAntiga.getIdSessao();
                String idLugar = compraAntiga.getIdLugar();
                
                if (idSessao != null && idLugar != null) {
                    // Encontrar a sessão e o lugar correspondentes
                    for (Sessao sessao : todasSessoes) {
                        if (sessao.getId().equals(idSessao)) {
                            // Encontrar o lugar na sala
                            for (Lugar lugar : sessao.getSala().getLugares()) {
                                if (lugar.getIdentificacao().equals(idLugar)) {
                                    // Criar bilhete com a sessão e lugar
                                    List<Bilhete> bilhetes = new ArrayList<>();
                                    bilhetes.add(new Bilhete(sessao, lugar));
                                    
                                    // Criar nova compra
                                    Compra compraNova = new Compra(
                                            bilhetes,                           // Bilhetes
                                            compraAntiga.getItensBar(),          // Itens do bar
                                            compraAntiga.getMetodoPagamento(),   // Método de pagamento
                                            compraAntiga.getIdUsuario()          // ID do usuário
                                    );
                                    
                                    comprasNovas.add(compraNova);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao converter compra: " + e.getMessage());
                // Se der erro na conversão, manter a compra original
                comprasNovas.add(compraAntiga);
            }
        }
        
        return comprasNovas;
    }
}
