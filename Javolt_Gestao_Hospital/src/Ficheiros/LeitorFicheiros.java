package Ficheiros;

import Entidades.Medico;
import Entidades.Especialidade;
import Entidades.Sintoma;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorFicheiros {
    private String separador;

    public LeitorFicheiros(String separador) {
        this.separador = separador;
    }

    private int contarLinhas(String caminho) {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            while (br.readLine() != null) total++;
        } catch (IOException e) {
            System.out.println(" Erro ao contar linhas: " + e.getMessage());
        }
        return total;
    }

    public Especialidade[] lerEspecialidades(String caminho) {
        int total = contarLinhas(caminho);
        Especialidade[] lista = new Especialidade[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(separador);
                if (partes.length == 2) {
                    lista[i++] = new Especialidade(partes[0], partes[1]);
                }
            }
        } catch (IOException e) {
            System.out.println(" Erro ao ler especialidades: " + e.getMessage());
        }
        return lista;
    }

    public Medico[] lerMedicos(String caminho, Especialidade[] especialidadesConhecidas) {
        int total = contarLinhas(caminho);
        Medico[] lista = new Medico[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(separador);
                if (partes.length >= 5) {
                    String nomeMedico = partes[0].trim();
                    String codigoEsp = partes[1].trim();
                    String nomeEsp = codigoEsp;

                    if (especialidadesConhecidas != null) {
                        for (Especialidade e : especialidadesConhecidas) {
                            if (e != null && e.getCodigo().equalsIgnoreCase(codigoEsp)) {
                                nomeEsp = e.getNome();
                                break;
                            }
                        }
                    }

                    lista[i++] = new Medico(
                            nomeMedico,
                            nomeEsp,
                            Integer.parseInt(partes[2].trim()),
                            Integer.parseInt(partes[3].trim()),
                            Double.parseDouble(partes[4].trim().replace(",", "."))
                    );
                }
            }
        } catch (IOException e) {
            System.out.println(" Erro ao ler médicos: " + e.getMessage());
        }
        return lista;
    }

    public Sintoma[] lerSintomas(String caminho, Especialidade[] especialidadesSistema) {
        int total = contarLinhas(caminho);
        Sintoma[] lista = new Sintoma[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(separador);

                if (partes.length >= 2) {
                    String nome = partes[0].trim();
                    String urgencia = partes[1].trim();
                    Especialidade especialidade = null;

                    if (partes.length >= 3 && especialidadesSistema != null) {
                        String codEsp = partes[2].trim();
                        for (Especialidade e : especialidadesSistema) {
                            if (e != null && e.getCodigo().equalsIgnoreCase(codEsp)) {
                                especialidade = e;
                                break;
                            }
                        }
                    }

                    lista[i++] = new Sintoma(nome, urgencia, especialidade);
                }
            }
        } catch (IOException e) {
            System.out.println(" Erro ao ler sintomas: " + e.getMessage());
        }
        return lista;
    }
}