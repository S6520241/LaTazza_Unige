package application.model.utenti;

import java.util.Comparator;

import application.utils.Euro;

public class Persona extends Cliente{
	
	private Euro debito;
	
    public static final Comparator<Persona> SortListPersona = (Persona p1, Persona p2) -> p1.getNome().compareToIgnoreCase(p2.getNome());
	
    
	public Persona(String nome) {
		super(nome);
		debito=new Euro(0);
	}
	
	public Persona(String nome, Euro debito) {
		super(nome);
		this.debito=debito;
	}
	
	
	
	public String getNome() {
		return super.getNome();
	}
	
	
	public Euro getDebito() {
		return debito;
	}
	
	
	public boolean aumentaDebito(Euro euro) {
		if(euro.getValore() < 0)
			return false;
		debito.somma(euro);	
		return true;
	}

	
	public boolean diminuisciDebito(Euro euro) {
		if(euro.getValore()<0 || euro.getValore()>debito.getValore())
			return false;
		debito.sottrai(euro);
		return true;
	}
	
	
	@Override
	public String toString() {
		return super.getNome();
	}
		
}