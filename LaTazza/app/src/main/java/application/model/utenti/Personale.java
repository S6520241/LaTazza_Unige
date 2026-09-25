package application.model.utenti;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import application.utils.Euro;

public class Personale {

	private LinkedHashSet<Persona> personale;
	private LinkedHashSet<PagamentoDebito> pagamentiDebito;
	
	public Personale() {
		personale = new LinkedHashSet<Persona>();
		pagamentiDebito = new LinkedHashSet<PagamentoDebito>();
	}
	
	
	public LinkedHashSet<Persona> getPersonale() {
		return personale;
	}

	public LinkedHashSet<PagamentoDebito> getPagamentiDebito() {
		return pagamentiDebito;
	}


	public void load(String path) {
		try (BufferedReader br = new BufferedReader( 
				new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")))) {

		      String sCurrentLine;

		      while ((sCurrentLine = br.readLine()) != null) {
		    	  if(sCurrentLine.equals("PERSONALE")){
		    		  while ((sCurrentLine = br.readLine()) != null) {
		    			  if(sCurrentLine.equals(""))
		    				  break;
		    			  String[] persona = sCurrentLine.split(" ");
		    			  personale.add(new Persona(persona[0], new Euro(0,Integer.valueOf(persona[1]))));
		    		  }
		    	  }if(sCurrentLine.equals("PAGAMENTO")){
		    		  while ((sCurrentLine = br.readLine()) != null) {
		    			  if(sCurrentLine.equals(""))
		    				  return;
		    			  String[] persona = sCurrentLine.split(" ");
		    			  pagamentiDebito.add(new PagamentoDebito(new Persona(persona[1]),new Euro(0,Integer.valueOf(persona[2])),new Date(Long.parseLong(persona[0]))));
		    		  }
		    	  }
		      }
		    } catch (IOException e) {
		    }
	}


	public boolean addPersona (String p) {	
		return personale.add(new Persona(p));	
	}
	
	
	public boolean removePersona (Persona p) {	
		if(!p.getDebito().ugualeA(new Euro(0)))	return false;
		return personale.remove(p);
	}
	
	
	public Set<Persona> getIndebitati(){
		LinkedHashSet<Persona> personaleConDebiti = new LinkedHashSet<Persona>();
		for(Persona pers : personale) {
			if(!pers.getDebito().ugualeA(new Euro(0))) {
				personaleConDebiti.add(pers);
			}
		}
		return personaleConDebiti;
	}
	
	
	public boolean diminuisciDebito(Persona pers, Euro ammontare) {
		if(!pers.diminuisciDebito(ammontare))
			return false;
		return pagamentiDebito.add(new PagamentoDebito(pers, ammontare));
	}
	
	
	public String print() {
		String personaleString="PERSONALE\n";
		for(Persona pers : personale)
			personaleString+=pers.toString()+" "+pers.getDebito().getValore()+'\n';
		String pagamentiDebitiString="PAGAMENTO\n";
		for(PagamentoDebito pagDeb : pagamentiDebito) 
			pagamentiDebitiString+=pagDeb.getEpoch()+" "+pagDeb.getPersona()+' '+pagDeb.getAmmontare().getValore()+'\n';
		return personaleString+='\n'+pagamentiDebitiString+'\n';
	}

}