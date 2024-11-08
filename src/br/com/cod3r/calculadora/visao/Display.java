package br.com.cod3r.calculadora.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.cod3r.calculadora.modelo.CalcObservador;
import br.com.cod3r.calculadora.modelo.MemoriaCalc;

@SuppressWarnings("serial")
public class Display extends JPanel implements CalcObservador{
	
	private JLabel label;
	
	public Display() {
		MemoriaCalc.getInstancia().adcionarObservador(this);
		
		setBackground(new Color(46,49,50));
		label = new JLabel(MemoriaCalc.getInstancia().getTextAtual());
		label.setForeground(Color.WHITE);
		label.setFont(new Font("courier", Font.PLAIN,30));
		
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10,25));
		
		add(label);
	}
	
	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);
	}
}
