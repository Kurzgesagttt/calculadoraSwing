package br.com.cod3r.calculadora.modelo;

import java.util.ArrayList;
import java.util.List;

public class MemoriaCalc {
	
	private enum TipoComando{
		ZERAR,NUMERO,DIV,MULT,SOMA,SUB,IGUAL,VIRGULA,TROCASIN
	}
	
	private static final MemoriaCalc instancia = new MemoriaCalc();
	private final List<CalcObservador> observadores = new ArrayList<>();
	
	
	private TipoComando ultimaOperacao = null;
	private boolean substituir = false;
	private String textAtual = "";
	private String textBuffer = "";
	
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
		return textAtual.isEmpty() ? "0" : textAtual;
		
	}
	
	public void processaComando(String texto) {
		
		TipoComando tipoComando = detectarTipoComando(texto);
		
		if(tipoComando == null) {
			return;
		}else if(tipoComando == TipoComando.ZERAR) {
			textAtual = "";
			textBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}else if(tipoComando == TipoComando.TROCASIN && textAtual.contains("-")) {
			textAtual = textAtual.substring(1);
			
		}else if(tipoComando == TipoComando.TROCASIN && !textAtual.contains("-")) {
			textAtual = "-" +textAtual;
			
		}else if(tipoComando == TipoComando.NUMERO  || tipoComando ==TipoComando.VIRGULA) {
			textAtual = substituir ? texto : textAtual + texto;
			substituir = false;
		}else {
			substituir = true;
			textAtual = obterResultadoOperacao();
			textBuffer = textAtual;
			
			ultimaOperacao = tipoComando;
		}
		
		
		observadores.forEach(o -> o.valorAlterado(getTextAtual()));	
	}

	private String obterResultadoOperacao() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textAtual;
		}
		
		
		double numeroBuffer = Double.parseDouble(textBuffer.replace(",", "."));
		double numeroAtual = Double.parseDouble(textAtual.replace(",", "."));
		
		double resultado = 0;
		
		if(ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		}else if(ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		}else if(ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		}else if(ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		}
		
		String resultadoString = Double.toString(resultado).replace(".", ",");
		boolean inteiro = resultadoString.endsWith(",0");
		return inteiro ? resultadoString.replace(",0","") : resultadoString;
	}

	private TipoComando detectarTipoComando(String texto) {
		if(textAtual.isEmpty() && texto == "0" ) {
			return null;
		}
		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		}catch(NumberFormatException e) {
			//quando nao for numero:
			if("AC".equals(texto)) {
				return TipoComando.ZERAR;
			}else if("/".equals(texto)) {
				return TipoComando.DIV;
			}else if("*".equals(texto)) {
				return TipoComando.MULT;
			}else if("+".equals(texto)) {
				return TipoComando.SOMA;
			}else if("-".equals(texto)) {
				return TipoComando.SUB;
			}else if("=".equals(texto)) {
				return TipoComando.IGUAL;
			}else if("+/-".equals(texto)) {
				return TipoComando.TROCASIN;
			}else if(",".equals(texto) && !textAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}
		}
		
		return null;
	}
	
	
}
