import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

// Tarefas:	

// Henrique
// ler o TXT principal e carregá-lo em uma tabela hashing

// Keicila
// criar uma tabela hashing com as divisas

// Klaus
// percorrer a tabela e gerar os vertices, particões e vetores

// Luiz
// percorrer as tabelas e gerar os arcs

public class Uso {

	public static void main(String[] args) {
		String linha, linha_divisas;
		BufferedReader arqEntrada, arqDivisas;
		
		BufferedWriter arquivoSaidaProjeto;
		BufferedWriter arquivoSaidaRede;
		BufferedWriter arquivoSaidaParticao1;
		BufferedWriter arquivoSaidaParticao2;
		BufferedWriter arquivoSaidaParticao3;
		BufferedWriter arquivoSaidaParticao4;
		BufferedWriter arquivoSaidaVector1;
		BufferedWriter arquivoSaidaVector2;
		BufferedWriter arquivoSaidaVector3;
		
		String arquivoSaidaP = "build/projeto.paj";
		String arquivoSaidaR = "build/vertices.net";
		String arquivoSaidaPart1 = "build/estados.clu";
		String arquivoSaidaPart2 = "build/regioes.clu";
		String arquivoSaidaPart3 = "build/portes.clu";
		String arquivoSaidaPart4 = "build/capitais.clu";
		String arquivoSaidaV1 = "build/fator1.vec";
		String arquivoSaidaV2 = "build/fator2.vec";
		String arquivoSaidaV3 = "build/populacao.vec";
		
		
		/*
		 * Cria o Array com as divisas existentes
		 * */
		ArrayList<String> divisas_array = new ArrayList<String>();
		
		try
		{
			arqDivisas = new BufferedReader(new InputStreamReader(new FileInputStream("divisas_concatenadas.txt"), "UNICODE"));
			while(true)
			{
				linha_divisas = arqDivisas.readLine();
				if(linha_divisas == null)
					break;
				System.out.println(linha_divisas);
				divisas_array.add(linha_divisas);
			}
			arqDivisas.close();
		}
		catch(IOException e) {
			System.err.println("Erro no acesso ao arquivo divisas_concatenadas.txt !");
		}
		
		Municipio municipio = null;
		HashMap<String, Municipio> tabela = new HashMap<String, Municipio>();
		try {
			// abre o arquivo para leitura no modo UNICODE (o mesmo formato TXT gerado pelo Excel)
			arqEntrada = new BufferedReader(new InputStreamReader(new FileInputStream("municipios_divisas.txt"), "UNICODE"));
			// percorre o arquivo, linha a linha, armazenando-o na tabela hashing
			
			while(true) {
				linha = arqEntrada.readLine();
				if(linha == null) 
					break;
				municipio = new Municipio(linha);
				System.out.println(municipio);
				tabela.put(municipio.getNomeComEstado(), municipio);
			} 
			arqEntrada.close();
			
/*--------------------------KLAUS - Gerar Vértices, Partições e Vetores----------------------------------------------------*/			

			//Como serão gerados os arquivos .net, .clu e .vet, então aproveitei
			//para gerar um .paj (um projeto pajek) contendo todos os arquivos
			arquivoSaidaProjeto = new BufferedWriter(new FileWriter(arquivoSaidaP));
			arquivoSaidaRede = new BufferedWriter(new FileWriter(arquivoSaidaR));
			arquivoSaidaParticao1 = new BufferedWriter(new FileWriter(arquivoSaidaPart1));
			arquivoSaidaParticao2 = new BufferedWriter(new FileWriter(arquivoSaidaPart2));
			arquivoSaidaParticao3 = new BufferedWriter(new FileWriter(arquivoSaidaPart3));
			arquivoSaidaParticao4 = new BufferedWriter(new FileWriter(arquivoSaidaPart4));
			arquivoSaidaVector1 = new BufferedWriter(new FileWriter(arquivoSaidaV1));
			arquivoSaidaVector2 = new BufferedWriter(new FileWriter(arquivoSaidaV2));
			arquivoSaidaVector3 = new BufferedWriter(new FileWriter(arquivoSaidaV3));
			
			Municipio municipioSaida;
			int i = 1;
			
			//Gerando .paj e o .net
			arquivoSaidaProjeto.append("*Network "+ arquivoSaidaR.substring(6));
			arquivoSaidaProjeto.append("\n*Vertices "+ tabela.size());
			
			arquivoSaidaRede.append("*Vertices "+ tabela.size());
			
			for (Entry<String, Municipio> entry : tabela.entrySet())
			{    
			    String nome = entry.getKey();
			    arquivoSaidaProjeto.append("\n" + "    " + i + " \"" + nome.substring(3) + "\" ");
			    arquivoSaidaRede.append("\n" + "    " + i++ + " \"" + nome.substring(3) + "\" ");
			}
			
			
			i = 1;
			int j = 1;
			
			
			
			
			/*
			 * Criacao dos arcos//edges
			 * 
			 * Obs: Codigo para gerar arcos esta comentado, logo apos o de edges.
			 * Acredito que so o de Edges seja suficiente, ja que dois arcos A->B e B->A, 
			 * e o mesmo que um edge A-B, assim fica menos linhas no arquivo .paj.
			 * */		
			
			/*
			 * Criacao dos edges
			 * 
			 * */
			arquivoSaidaProjeto.append("\n\n*Edges ");
			for (Entry<String, Municipio> entry_i : tabela.entrySet())
			{    
				for (Entry<String, Municipio> entry_j : tabela.entrySet())
				{   
					
				  // Se a cidade A ja foi ligada em B, entao B não necessita ligar com A novamente, redundancia.
				  if(j > i)
				  {
					  // se forem do mesmo estados, entao cria edge com valor 2
					  if(entry_i.getValue().getEstado().equals(entry_j.getValue().getEstado()) && 
							  !(entry_i.getKey().equals(entry_j.getKey())) )
					  {
						  arquivoSaidaProjeto.append("\n"+i+" "+j+" 2");
					  }
					  // se forem de estados vizinhos, entao cria edge com valor 1
					  else if(divisas_array.contains(entry_i.getValue().getEstado().concat(entry_j.getValue().getEstado())))
					  {
						  arquivoSaidaProjeto.append("\n"+i+" "+j+" 1");
					  }  
				  }
				  j++; 
				}
				
				i++;
				j = 1;
			}
			
			
			
			/*
			 *  Criacao das arestas
			 * */
			
/*			
 * 			arquivoSaidaProjeto.append("\n\n*Arcs ");
 * 
 * 			for (Entry<String, Municipio> entry_i : tabela.entrySet())
			{    
				for (Entry<String, Municipio> entry_j : tabela.entrySet())
				{   
					
				  // se forem do mesmo estados, entao cria arco com valor 2
				  if(entry_i.getValue().getEstado().equals(entry_j.getValue().getEstado()) && 
						  !(entry_i.getKey().equals(entry_j.getKey())) )
				  {
					  arquivoSaidaProjeto.append("\n"+i+" "+j+" 2");
				  }
				  // se forem de estados vizinhos, entao cria arco com valor 1
				  else if(divisas_array.contains(entry_i.getValue().getEstado().concat(entry_j.getValue().getEstado())))
				  {
					  arquivoSaidaProjeto.append("\n"+i+" "+j+" 1");
				  }
				  j++; 
				}
				
				i++;
				j = 1;
			}*/
			
			//Gerando particao dos estados
			arquivoSaidaProjeto.append("\n\n*Partition "+ arquivoSaidaPart1.substring(6));
			arquivoSaidaProjeto.append("\n*Vertices "+ tabela.size());
			
			arquivoSaidaParticao1.append("*Vertices "+ tabela.size());
			
			for (Entry<String, Municipio> entry : tabela.entrySet())
			{  
				municipioSaida = entry.getValue();  
			    String nome = entry.getKey();
			    if(municipioSaida.getEstado().contains("RO")) 
			    {
			    	arquivoSaidaProjeto.append("\n 1");
			    	arquivoSaidaParticao1.append("\n 1");
			    }
			    else if(municipioSaida.getEstado().contains("AC"))
			    {
			    	arquivoSaidaProjeto.append("\n 2");
			    	arquivoSaidaParticao1.append("\n 2");
			    }
			    else if(municipioSaida.getEstado().contains("AM"))
			    {
			    	arquivoSaidaProjeto.append("\n 3");
			    	arquivoSaidaParticao1.append("\n 3");
			    }
			    else if(municipioSaida.getEstado().contains("RR"))
			    {
			    	arquivoSaidaProjeto.append("\n 4");
			    	arquivoSaidaParticao1.append("\n 4");
			    }
			    else if(municipioSaida.getEstado().contains("PA"))
			    {
			    	arquivoSaidaProjeto.append("\n 5");
			    	arquivoSaidaParticao1.append("\n 5");
			    }
			    else if(municipioSaida.getEstado().contains("AP"))
			    {
			    	arquivoSaidaProjeto.append("\n 6");
			    	arquivoSaidaParticao1.append("\n 6");
			    }
			    else if(municipioSaida.getEstado().contains("TO"))
			    {
			    	arquivoSaidaProjeto.append("\n 7");
			    	arquivoSaidaParticao1.append("\n 7");
			    }
			    else if(municipioSaida.getEstado().contains("MA"))
			    {
			    	arquivoSaidaProjeto.append("\n 8");
			    	arquivoSaidaParticao1.append("\n 8");
			    }
			    else if(municipioSaida.getEstado().contains("PI"))
			    {
			    	arquivoSaidaProjeto.append("\n 9");
			    	arquivoSaidaParticao1.append("\n 9");
			    }
			    else if(municipioSaida.getEstado().contains("CE"))
			    {
			    	arquivoSaidaProjeto.append("\n 10");
			    	arquivoSaidaParticao1.append("\n 10");
			    }
			    else if(municipioSaida.getEstado().contains("RN"))
			    {
			    	arquivoSaidaProjeto.append("\n 11");
			    	arquivoSaidaParticao1.append("\n 11");
			    }
			    else if(municipioSaida.getEstado().contains("PB"))
			    {
			    	arquivoSaidaProjeto.append("\n 12");
			    	arquivoSaidaParticao1.append("\n 12");
			    }
			    else if(municipioSaida.getEstado().contains("PE"))
			    {
			    	arquivoSaidaProjeto.append("\n 13");
			    	arquivoSaidaParticao1.append("\n 13");
			    }
			    else if(municipioSaida.getEstado().contains("AL"))
			    {
			    	arquivoSaidaProjeto.append("\n 14");
			    	arquivoSaidaParticao1.append("\n 14");
			    }
			    else if(municipioSaida.getEstado().contains("SE"))
			    {
			    	arquivoSaidaProjeto.append("\n 15");
			    	arquivoSaidaParticao1.append("\n 15");
			    }
			    else if(municipioSaida.getEstado().contains("BA"))
			    {
			    	arquivoSaidaProjeto.append("\n 16");
			    	arquivoSaidaParticao1.append("\n 16");
			    }
			    else if(municipioSaida.getEstado().contains("MG"))
			    {
			    	arquivoSaidaProjeto.append("\n 17");
			    	arquivoSaidaParticao1.append("\n 17");
			    }
			    else if(municipioSaida.getEstado().contains("ES"))
			    {
			    	arquivoSaidaProjeto.append("\n 18");
			    	arquivoSaidaParticao1.append("\n 18");
			    }
			    else if(municipioSaida.getEstado().contains("RJ"))
			    {
			    	arquivoSaidaProjeto.append("\n 19");
			    	arquivoSaidaParticao1.append("\n 19");
			    }
			    else if(municipioSaida.getEstado().contains("SP"))
			    {
			    	arquivoSaidaProjeto.append("\n 20");
			    	arquivoSaidaParticao1.append("\n 20");
			    }
			    else if(municipioSaida.getEstado().contains("PR"))
			    {
			    	arquivoSaidaProjeto.append("\n 21");
			    	arquivoSaidaParticao1.append("\n 21");
			    }
			    else if(municipioSaida.getEstado().contains("SC"))
			    {
			    	arquivoSaidaProjeto.append("\n 22");
			    	arquivoSaidaParticao1.append("\n 22");
			    }
			    else if(municipioSaida.getEstado().contains("RS"))
			    {
			    	arquivoSaidaProjeto.append("\n 23");
			    	arquivoSaidaParticao1.append("\n 23");
			    }
			    else if(municipioSaida.getEstado().contains("MS"))
			    {
			    	arquivoSaidaProjeto.append("\n 24");
			    	arquivoSaidaParticao1.append("\n 24");
			    }
			    else if(municipioSaida.getEstado().contains("MT"))
			    {
			    	arquivoSaidaProjeto.append("\n 25");
			    	arquivoSaidaParticao1.append("\n 25");
			    }
			    else if(municipioSaida.getEstado().contains("GO"))
			    {
			    	arquivoSaidaProjeto.append("\n 26");
			    	arquivoSaidaParticao1.append("\n 26");
			    }
			    else {
			    	arquivoSaidaProjeto.append("\n 27");
			    	arquivoSaidaParticao1.append("\n 27");
			    }
			}
			
			//Gerando particao das regiões
			arquivoSaidaProjeto.append("\n\n*Partition "+ arquivoSaidaPart2.substring(6));
			arquivoSaidaProjeto.append("\n*Vertices "+ tabela.size());
			
			arquivoSaidaParticao2.append("*Vertices "+ tabela.size());
			
			for (Entry<String, Municipio> entry : tabela.entrySet())
			{  
				municipioSaida = entry.getValue();  
			    if(municipioSaida.getRegiao().contains("Região Norte")) 
			    {
			    	arquivoSaidaProjeto.append("\n 100");
			    	arquivoSaidaParticao2.append("\n 100");
			    }
			    else if(municipioSaida.getRegiao().contains("Região Nordeste"))
			    {
			    	arquivoSaidaProjeto.append("\n 200");
			    	arquivoSaidaParticao2.append("\n 200");
			    }
			    else if(municipioSaida.getRegiao().contains("Região Suldeste"))
			    {
			    	arquivoSaidaProjeto.append("\n 300");
			    	arquivoSaidaParticao2.append("\n 300");
			    }
			    else if(municipioSaida.getRegiao().contains("Região Sul"))
			    {
			    	arquivoSaidaProjeto.append("\n 400");
			    	arquivoSaidaParticao2.append("\n 400");
			    } 
			    else if(municipioSaida.getRegiao().contains("Região Centro-Oeste"))
			    {
			    	arquivoSaidaProjeto.append("\n 500");
			    	arquivoSaidaParticao2.append("\n 500");
			    }
			    else if(municipioSaida.getRegiao().contains("Região Norte"))
			    {
			    	arquivoSaidaProjeto.append("\n 600");
			    	arquivoSaidaParticao2.append("\n 600");
			    }
			    else 
			    {
			    	arquivoSaidaProjeto.append("\n 9999999");
			    	arquivoSaidaParticao1.append("\n 9999999");
			    }
			}
			
			//Gerando particao dos portes
			arquivoSaidaProjeto.append("\n\n*Partition "+ arquivoSaidaPart3.substring(6));
			arquivoSaidaProjeto.append("\n*Vertices "+ tabela.size());
			
			arquivoSaidaParticao3.append("*Vertices "+ tabela.size());
			
			for (Entry<String, Municipio> entry : tabela.entrySet())
			{  
				municipioSaida = entry.getValue();  
			    if(municipioSaida.getPorte().contains("Pequeno I")) 
			    {
			    	arquivoSaidaProjeto.append("\n 1");
			    	arquivoSaidaParticao3.append("\n 1");
			    }
			    else if(municipioSaida.getPorte().contains("Pequeno II"))
			    {
			    	arquivoSaidaProjeto.append("\n 2");
			    	arquivoSaidaParticao3.append("\n 2");
			    }
			    else if(municipioSaida.getPorte().contains("Médio"))
			    {
			    	arquivoSaidaProjeto.append("\n 3");
			    	arquivoSaidaParticao3.append("\n 3");
			    }
			    else if(municipioSaida.getPorte().contains("Grande"))
			    {
			    	arquivoSaidaProjeto.append("\n 4");
			    	arquivoSaidaParticao3.append("\n 4");
			    }
			    else
			    {
			    	arquivoSaidaProjeto.append("\n 999");
			    	arquivoSaidaParticao3.append("\n 999");
			    }
			}
			
			//Gerando particao de capital ou não
			arquivoSaidaProjeto.append("\n\n*Partition "+ arquivoSaidaPart4.substring(6));
			arquivoSaidaProjeto.append("\n*Vertices "+ tabela.size());
			
			arquivoSaidaParticao4.append("*Vertices "+ tabela.size());
			
			for (Entry<String, Municipio> entry : tabela.entrySet())
			{  
				municipioSaida = entry.getValue();  
			    if(municipioSaida.getCapital()) 
			    {
			    	arquivoSaidaProjeto.append("\n 2");
			    	arquivoSaidaParticao4.append("\n 2");
			    }
			    else 
			    {
			    	arquivoSaidaProjeto.append("\n 1");
			    	arquivoSaidaParticao4.append("\n 1");
			    }
			}
			
			//Escrevendo o vetor 1 - Fator 1
			arquivoSaidaProjeto.append("\n\n*Vector "+ arquivoSaidaV1.substring(6));
			arquivoSaidaProjeto.append("\n*Vertices "+ tabela.size());
			
			arquivoSaidaVector1.append("*Vertices "+ tabela.size());
			for (Entry<String, Municipio> entry : tabela.entrySet())
			{  
				municipioSaida = entry.getValue();  
			    arquivoSaidaProjeto.append("\n" + municipioSaida.getFator1());
			    arquivoSaidaVector1.append("\n" + municipioSaida.getFator1());
			    
			}
			
			//Escrevendo o vetor 2 - Fator 2
			arquivoSaidaProjeto.append("\n\n*Vector "+ arquivoSaidaV2.substring(6));
			arquivoSaidaProjeto.append("\n*Vertices "+ tabela.size());
			
			arquivoSaidaVector2.append("*Vertices "+ tabela.size());
			for (Entry<String, Municipio> entry : tabela.entrySet())
			{  
				municipioSaida = entry.getValue();  
			    arquivoSaidaProjeto.append("\n" + municipioSaida.getFator2());
			    arquivoSaidaVector2.append("\n" + municipioSaida.getFator2());
			    
			}
			
			//Escrevendo o vetor 3 - população
			arquivoSaidaProjeto.append("\n\n*Vector "+ arquivoSaidaV3.substring(6));
			arquivoSaidaProjeto.append("\n*Vertices "+ tabela.size());
			
			arquivoSaidaVector3.append("*Vertices "+ tabela.size());
			for (Entry<String, Municipio> entry : tabela.entrySet())
			{  
				municipioSaida = entry.getValue();  
			    arquivoSaidaProjeto.append("\n" + municipioSaida.getPopulacao());
			    arquivoSaidaVector3.append("\n" + municipioSaida.getPopulacao());
			    
			}
			
			arquivoSaidaProjeto.close();
			arquivoSaidaRede.close();
			arquivoSaidaParticao1.close();
			arquivoSaidaParticao2.close();
			arquivoSaidaParticao3.close();
			arquivoSaidaParticao4.close();
			arquivoSaidaVector1.close();
			arquivoSaidaVector2.close();
			arquivoSaidaVector3.close();
			
		} 
		catch(IOException e) {
			System.out.println("erro no acesso ao arquivo");
		}
		
/*-------------------- ----------------------------------------------------------------*/
		String linha2;
		BufferedReader arqEntrada2;
		Divisas divisas = null;
		HashMap<String, Divisas> tabelaD = new HashMap<String, Divisas>();
		try {
			// abre o arquivo para leitura no modo UNICODE (o mesmo formato TXT gerado pelo Excel)
			arqEntrada2 = new BufferedReader(new InputStreamReader(new FileInputStream("divisas_concatenadas.txt"), "UNICODE"));
			// percorre o arquivo, linha a linha, armazenando-o na tabela hashing
			while(true) {
				linha2 = arqEntrada2.readLine();
				if(linha2 == null) break;
					
				divisas = new Divisas(linha2);
				tabelaD.put(divisas.getDivisas(),divisas);
			} 
			arqEntrada2.close();
			
			// apenas para testar, exibe o conteúdo da tabela hashing:
			System.out.println(tabelaD);
			
		} 
		catch(IOException e) {
			System.out.println("erro no acesso ao arquivo");
		}
		
	}
		
	
}
