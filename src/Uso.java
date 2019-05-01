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
			System.out.println(tabela);
			
		} 
		catch(IOException e) {
			System.out.println("erro no acesso ao arquivo");
		}		
	}
}
