package br.octave.ASTOctave.ast;

public class OctaveExpressaoBinaria extends OctaveExpressao{

	private ElementoOctave parte1;
	private ElementoOctave parte2;
	private String operador;
	
	public OctaveExpressaoBinaria(ElementoOctave parte1, ElementoOctave parte2, String operador){
		this.parte1 = parte1;
		this.parte2 = parte2;
		this.operador = operador;
	}
	
	public ElementoOctave getParte1(){
		return this.parte1;
	}

	public ElementoOctave getParte2(){
		return this.parte2;
	}
	
	public String getOperador(){
		return this.operador;
	}
}