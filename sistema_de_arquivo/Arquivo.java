import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Arquivo {
    String nome;
    int tamanho;
    List<Integer> blocosAlocados;

    public Arquivo(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.blocosAlocados = new ArrayList<>();
    }
}

class Diretorio {
    String nome;
    Map<String, Arquivo> arquivos;

    public Diretorio(String nome) {
        this.nome = nome;
        this.arquivos = new HashMap<>();
    }
}

class SimuladorSistemaArquivos {
    int tamanhoBloco;
    int tamanhoMemoria;
    Diretorio raiz;
    List<Boolean> blocosMemoria;

    public SimuladorSistemaArquivos(int tamanhoBloco, int tamanhoMemoria) {
        this.tamanhoBloco = tamanhoBloco;
        this.tamanhoMemoria = tamanhoMemoria;
        this.raiz = new Diretorio("raiz");
        this.blocosMemoria = new ArrayList<>();
        for (int i = 0; i < tamanhoMemoria / tamanhoBloco; i++) {
            blocosMemoria.add(false);
        }
    }

    public void criarArquivo(String nomeDiretorio, String nomeArquivo, int tamanho) {
        Diretorio diretorio = encontrarDiretorio(raiz, nomeDiretorio);
        if (diretorio != null && !diretorio.arquivos.containsKey(nomeArquivo) && tamanho <= tamanhoMemoria) {
            Arquivo arquivo = new Arquivo(nomeArquivo, tamanho);
            alocarMemoria(arquivo);
            diretorio.arquivos.put(nomeArquivo, arquivo);
            System.out.println("Arquivo '" + nomeArquivo + "' criado.");
        } else {
            System.out.println("Falha na criação do arquivo.");
        }
    }

    public void excluirArquivo(String nomeDiretorio, String nomeArquivo) {
        Diretorio diretorio = encontrarDiretorio(raiz, nomeDiretorio);
        if (diretorio != null && diretorio.arquivos.containsKey(nomeArquivo)) {
            Arquivo arquivo = diretorio.arquivos.get(nomeArquivo);
            desalocarMemoria(arquivo);
            diretorio.arquivos.remove(nomeArquivo);
            System.out.println("Arquivo '" + nomeArquivo + "' excluído.");
        } else {
            System.out.println("Falha na exclusão do arquivo.");
        }
    }

    public void listarArquivos(String nomeDiretorio) {
        Diretorio diretorio = encontrarDiretorio(raiz, nomeDiretorio);
        if (diretorio != null) {
            System.out.println("Arquivos no diretório '" + nomeDiretorio + "':");
            for (String nomeArquivo : diretorio.arquivos.keySet()) {
                System.out.println("- " + nomeArquivo);
            }
        } else {
            System.out.println("Diretório não encontrado.");
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

    private void alocarMemoria(Arquivo arquivo) {
        int blocosNecessarios = (int) Math.ceil((double) arquivo.tamanho / tamanhoBloco);
        for (int i = 0; i < blocosMemoria.size(); i++) {
            if (blocosMemoria.get(i)) {
                continue;
            }
            boolean podeAlocar = true;
            for (int j = i; j < i + blocosNecessarios && j < blocosMemoria.size(); j++) {
                if (blocosMemoria.get(j)) {
                    podeAlocar = false;
                    break;
                }
            }
            if (podeAlocar) {
                for (int j = i; j < i + blocosNecessarios && j < blocosMemoria.size(); j++) {
                    blocosMemoria.set(j, true);
                    arquivo.blocosAlocados.add(j);
                }
                System.out.println("Blocos de memória alocados para '" + arquivo.nome + "'");
                break;
            }
        }
    }

    private void desalocarMemoria(Arquivo arquivo) {
        for (int bloco : arquivo.blocosAlocados) {
            blocosMemoria.set(bloco, false);
        }
        System.out.println("Blocos de memória desalocados de '" + arquivo.nome + "'");
    }

    public boolean verificarFragmentacao() {
        int blocosAlocadosConsecutivos = 0;
        boolean fragmentacaoExterna = false;

        for (boolean blocoAlocado : blocosMemoria) {
            if (blocoAlocado) {
                blocosAlocadosConsecutivos++;
            } else {
                blocosAlocadosConsecutivos = 0;
            }

            if (blocosAlocadosConsecutivos < tamanhoBloco) {
                fragmentacaoExterna = true;
            }

            if (blocosAlocadosConsecutivos == tamanhoBloco) {
                blocosAlocadosConsecutivos = 0;
            }
        }

        boolean fragmentacaoInterna = false;
        for (Arquivo arquivo : raiz.arquivos.values()) {
            int blocosNecessarios = (int) Math.ceil((double) arquivo.tamanho / tamanhoBloco);
            int blocosAlocados = arquivo.blocosAlocados.size();
            if (blocosNecessarios != blocosAlocados) {
                fragmentacaoInterna = true;
                break;
            }
        }

        System.out.println("Fragmentação interna: " + (fragmentacaoInterna ? "Sim" : "Não"));
        System.out.println("Fragmentação externa: " + (fragmentacaoExterna ? "Sim" : "Não"));

        return fragmentacaoInterna || fragmentacaoExterna;
    }
}