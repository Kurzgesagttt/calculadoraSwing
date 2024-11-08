package br.com.cod3r.calculadora.modelo;

import java.util.ArrayList;
import java.util.List;

public class MemoriaCalc {
	
	private static final MemoriaCalc instancia = new MemoriaCalc();
	private final List<CalcObservador> observadores = new ArrayList<>();
	
	
	private String text = "";
	
	private MemoriaCalc() {
		// TODO Auto-generated constructor stub
		
	}
	
	public void adcionarObservador(CalcObservador observador) {
		observadores.add(observador);
	}
	
	public static MemoriaCalc getInstancia() {
		return instancia;
		
	}

	public String getTextAtual() {
		return text.isEmpty() ? "0" : text;
		
	}
	
	public void processaComando(String valor) {
		
		if("AC".equals(valor)) {
			text = "";
		}else {
			text += valor;
		}
		
		observadores.forEach(o -> o.valorAlterado(getTextAtual()));	
	}
}
