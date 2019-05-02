import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

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
		String linha;
		BufferedReader arqEntrada;
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
				tabela.put(municipio.getNomeComEstado(), municipio);
			} 
			arqEntrada.close();
			
			// apenas para testar, exibe o conteúdo da tabela hashing:
			//System.out.println(tabela);
			
		} 
		catch(IOException e) {
			System.out.println("erro no acesso ao arquivo");
		}
		
/*-------------------- ----------------------------------------------------------------*/
				
		
		 Divisas nomeEstado = null;
		 Divisas nomeDivisa = null;
		 BufferedReader arqEntrada2;
		 BufferedReader arqEntrada3;
		 String linha2;
		 String linha3;
		 HashMap<String, Divisas> tabelaD = new HashMap<String,Divisas>();
		
		 try {
				// abre o arquivo para leitura no modo UNICODE (o mesmo formato TXT gerado pelo Excel)
				arqEntrada2 = new BufferedReader(new InputStreamReader(new FileInputStream("municipios_divisas.txt"), "UNICODE"));
				arqEntrada3 = new BufferedReader(new InputStreamReader(new FileInputStream("divisas_concatenadas.txt"), "UNICODE"));
				
				// percorre o arquivo, linha a linha, armazenando-o na tabela hashing
				while(true) {
					linha2 = arqEntrada2.readLine();
					linha3 = arqEntrada3.readLine();
					if(linha2 == null) 
						break;
					if(linha3 == null) 
						break;
					nomeEstado = new Divisas(linha2);
					nomeDivisa = new Divisas (linha3);
					tabelaD.put(nomeEstado.getEstado(),nomeEstado);
					tabelaD.put(nomeDivisa.getDivisas(), nomeDivisa);
					
					
				} 
				arqEntrada2.close();
				arqEntrada3.close();
				
				// apenas para testar, exibe o conteúdo da tabela hashing:
				System.out.println(tabelaD);
				
			} 
			catch(IOException e) {
				System.out.println("erro no acesso ao arquivo");
			}		
		}
		
	
}
