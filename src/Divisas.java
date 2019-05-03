public class Divisas {
	
	
	public String getDivisas() {
		return divisas;
	}
	
	
	private String divisas;
	

	public Divisas(String linha2) {
		String linhaSemAspas = linha2;
		String trechoLinha[] = linhaSemAspas.split("\\\\r?\\\\n");
		
		this.divisas	= trechoLinha[0];
			
	}
}
