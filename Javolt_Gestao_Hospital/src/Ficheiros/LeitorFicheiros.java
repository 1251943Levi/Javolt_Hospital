package Ficheiros;

import Entidades.Medico;
import Entidades.Especialidade;
import Entidades.Sintoma;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorFicheiros {

    private String separador;

    // ================= CONSTRUTOR =================
    public LeitorFicheiros(String separador) {
        this.separador = separador;
    }

    // ================= CONTAR LINHAS =================
    private int contarLinhas(String caminho) {
        int total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            while (br.readLine() != null) {
                total++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao contar linhas: " + e.getMessage());
        }

        return total;
    }

    // ================= ESPECIALIDADES =================
    public Especialidade[] lerEspecialidades(String caminho) {

        int total = contarLinhas(caminho);
        Especialidade[] lista = new Especialidade[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {

                String[] partes = linha.split(separador);

                if (partes.length == 2) {   // validação básica
                    String codigo = partes[0];
                    String nome = partes[1];

                    lista[i] = new Especialidade(codigo, nome);
                    i++;
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler especialidades: " + e.getMessage());
        }

        return lista;
    }

    // ================= MÉDICOS =================
    public Medico[] lerMedicos(String caminho) {

        int total = contarLinhas(caminho);
        Medico[] lista = new Medico[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {

                String[] partes = linha.split(separador);

                if (partes.length == 5) {

                    String nome = partes[0];
                    String codEspecialidade = partes[1];
                    int horaEntrada = Integer.parseInt(partes[2]);
                    int horaSaida = Integer.parseInt(partes[3]);
                    double valorHora = Double.parseDouble(partes[4]);

                    lista[i] = new Medico(
                            nome,
                            codEspecialidade,
                            horaEntrada,
                            horaSaida,
                            valorHora
                    );
                    i++;
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler médicos: " + e.getMessage());
        }

        return lista;
    }

    // ================= SINTOMAS =================
    public Sintoma[] lerSintomas(String caminho, Especialidade[] especialidadesSistema) {

        int total = contarLinhas(caminho);
        Sintoma[] lista = new Sintoma[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {

                String[] partes = linha.split(separador);

                if (partes.length >= 2) {

                    String nome = partes[0];
                    String urgencia = partes[1];

                    Especialidade especialidade = null;

                    // Se existir código de especialidade
                    if (partes.length == 3) {
                        String codEsp = partes[2];

                        // Procurar especialidade no sistema
                        for (Especialidade e : especialidadesSistema) {
                            if (e.getCodigo().equalsIgnoreCase(codEsp)) {
                                especialidade = e;
                                break;
                            }
                        }
                    }

                    lista[i] = new Sintoma(nome, urgencia, especialidade);
                    i++;
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler sintomas: " + e.getMessage());
        }

        return lista;
    }}

