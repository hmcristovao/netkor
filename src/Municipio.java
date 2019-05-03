public class Municipio {
	private String nomeComEstado;
	private String estado;
	private String nome;
	private String regiao;
	private int populacao;
	private String porte;
	private boolean capital;
	private int fator1;
	private int fator2;

	
	public Municipio(String linha) {
		
		String linhaSemAspas = linha.substring(1, linha.length()-1);
		String trechoLinha[] = linhaSemAspas.split(",");
		this.nomeComEstado 	= trechoLinha[0];
		this.estado 		= trechoLinha[1];
		this.nome 			= trechoLinha[2];
		this.regiao 		= trechoLinha[3];
		this.populacao 		= Integer.parseInt(trechoLinha[4]);
		this.porte 			= trechoLinha[5];
		this.capital		= trechoLinha[6].equals("Capital") ? true : false;
		this.fator1 		= Integer.parseInt(trechoLinha[7]);
		this.fator2 		= Integer.parseInt(trechoLinha[8]);
	}
	public String getNomeComEstado() {
		return this.nomeComEstado;
	}
	public String getEstado() {
		return this.estado;
	}
	public String getNome() {
		return this.nome;
	}
	public String getRegiao() {
		return this.regiao;
	}
	public int getPopulacao() {
		return this.populacao;
	}
	public String getPorte() {
		return this.porte;
	}
	public boolean getCapital() {
		return this.capital;
	}
	public int getFator1() {
		return this.fator1;
	}
	public int getFator2() {
		return this.fator2;
	}
	public String toString() {
		return this.getNomeComEstado()+","+this.getEstado()+","+this.getNome()+","+this.getRegiao()
				+","+this.getPopulacao()+","+this.getPorte()+","+this.getCapital()+","+this.getFator1()
				+","+this.getFator2();
	}		
}
