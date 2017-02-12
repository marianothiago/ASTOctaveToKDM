package br.octave.astOctaveToKDM.octaveKDM;

import br.octave.ASTOctave.ast.ElementoOctave;

public class Token extends ElementoOctave{

	private String valor;
	private String tipo;
	private int precedencia;
	
	public Token(String valor, String tipo, int precedencia){
		this.valor = valor;
		this.tipo = tipo;
		this.precedencia = precedencia;
	}
	
	public String getValor(){
		return this.valor;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public int getPrecedencia(){
		return this.precedencia;
	}
}