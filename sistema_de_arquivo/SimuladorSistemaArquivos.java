import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Arquivo {
    String nome;
    int tamanho;

    public Arquivo(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }
}

class Diretorio extends Arquivo {
    Map<String, Arquivo> arquivos;

    public Diretorio(String nome) {
        super(nome, 0);
        arquivos = new HashMap<>();
    }
}

public class SimuladorSistemaArquivos {
    private int tamanhoBloco;
    private List<Boolean> blocosMemoria;
    private Diretorio raiz;

    public SimuladorSistemaArquivos(int tamanhoBloco, int tamanhoMemoria) {
        this.tamanhoBloco = tamanhoBloco;
        int numBlocos = tamanhoMemoria / tamanhoBloco;
        blocosMemoria = new ArrayList<>(numBlocos);
        for (int i = 0; i < numBlocos; i++) {
            blocosMemoria.add(false);
        }
        raiz = new Diretorio("raiz");
    }

    private boolean alocarBlocos(int numBlocos) {
        int blocosAlocados = 0;
        for (int i = 0; i < blocosMemoria.size(); i++) {
            if (!blocosMemoria.get(i)) {
                blocosMemoria.set(i, true);
                blocosAlocados++;
                if (blocosAlocados == numBlocos) {
                    return true;
                }
            }
        }
        return false;
    }

    private void liberarBlocos(int numBlocos) {
        int blocosLiberados = 0;
        for (int i = 0; i < blocosMemoria.size(); i++) {
            if (blocosMemoria.get(i)) {
                blocosMemoria.set(i, false);
                blocosLiberados++;
                if (blocosLiberados == numBlocos) {
                    return;
                }
            }
        }
    }

    private Diretorio encontrarDiretorio(Diretorio diretorioAtual, String nomeDiretorio) {
        if (diretorioAtual.nome.equals(nomeDiretorio)) {
            return diretorioAtual;
        }
        for (Arquivo arquivo : diretorioAtual.arquivos.values()) {
            if (arquivo instanceof Diretorio) {
                Diretorio subDiretorio = (Diretorio) arquivo;
                Diretorio diretorioEncontrado = encontrarDiretorio(subDiretorio, nomeDiretorio);
                if (diretorioEncontrado != null) {
                    return diretorioEncontrado;
                }
            }
        }
        return null;
    }

    public void criarArquivo(String nomeDiretorio, String nomeArquivo, int tamanhoArquivo) {
        Diretorio diretorio = encontrarDiretorio(raiz, nomeDiretorio);
        if (diretorio != null) {
            int numBlocos = (int) Math.ceil((double) tamanhoArquivo / tamanhoBloco);
            if (alocarBlocos(numBlocos)) {
                Arquivo arquivo = new Arquivo(nomeArquivo, tamanhoArquivo);
                diretorio.arquivos.put(nomeArquivo, arquivo);
                diretorio.tamanho += tamanhoArquivo;
                System.out.println("Arquivo '" + nomeArquivo + "' criado.");
            } else {
                System.out.println("Espaço insuficiente para criar o arquivo.");
            }
        } else {
            System.out.println("Diretório não encontrado.");
        }
    }

    public void excluirArquivo(String nomeDiretorio, String nomeArquivo) {
        Diretorio diretorio = encontrarDiretorio(raiz, nomeDiretorio);
        if (diretorio != null && diretorio.arquivos.containsKey(nomeArquivo)) {
            Arquivo arquivo = diretorio.arquivos.get(nomeArquivo);
            int numBlocos = (int) Math.ceil((double) arquivo.tamanho / tamanhoBloco);
            liberarBlocos(numBlocos);
            diretorio.arquivos.remove(nomeArquivo);
            diretorio.tamanho -= arquivo.tamanho;
            System.out.println("Arquivo '" + nomeArquivo + "' excluído.");
        } else {
            System.out.println("Arquivo não encontrado.");
        }
    }

    public void listarArquivos(String nomeDiretorio) {
        Diretorio diretorio = encontrarDiretorio(raiz, nomeDiretorio);
        if (diretorio != null) {
            System.out.println("Arquivos no diretório '" + nomeDiretorio + "':");
            for (Arquivo arquivo : diretorio.arquivos.values()) {
                System.out.println("- " + arquivo.nome + " (" + arquivo.tamanho + " bytes)");
            }
        } else {
            System.out.println("Diretório não encontrado.");
        }
    }

    public void verificarFragmentacao() {
        boolean fragmentacaoInterna = false;
        boolean fragmentacaoExterna = false;
    
        int blocosLivreConsecutivos = 0;
        boolean blocoOcupado = blocosMemoria.get(0);
    
        for (int i = 0; i < blocosMemoria.size(); i++) {
            boolean atualBlocoOcupado = blocosMemoria.get(i);
    
            if (atualBlocoOcupado != blocoOcupado) {
                blocosLivreConsecutivos = 0;
                blocoOcupado = atualBlocoOcupado;
            }
    
            if (!atualBlocoOcupado) {
                blocosLivreConsecutivos++;
    
                if (blocosLivreConsecutivos >= 2) {
                    fragmentacaoExterna = true;
                    break;
                }
            } else {
                blocosLivreConsecutivos = 0;
            }
        }
    
        if (!fragmentacaoExterna) {
            for (Arquivo arquivo : raiz.arquivos.values()) {
                int blocosNecessarios = (int) Math.ceil((double) arquivo.tamanho / tamanhoBloco);
                int blocosAlocados = 0;
                for (int i = 0; i < blocosMemoria.size(); i++) {
                    if (!blocosMemoria.get(i)) {
                        blocosAlocados++;
                        if (blocosAlocados == blocosNecessarios) {
                            break;
                        }
                    } else {
                        blocosAlocados = 0;
                    }
                }
                if (blocosNecessarios != blocosAlocados) {
                    fragmentacaoInterna = true;
                    break;
                }
            }
        }
    
        System.out.println("Fragmentação interna: " + (fragmentacaoInterna ? "Sim" : "Não"));
        System.out.println("Fragmentação externa: " + (fragmentacaoExterna ? "Sim" : "Não"));
    }
    
    
}