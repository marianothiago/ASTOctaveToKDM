package br.octave.ASTOctave.ast;

public class OctaveExpressaoUnaria extends OctaveExpressao{

	private ElementoOctave elemento;
	private String operadorUnario;
	
	public OctaveExpressaoUnaria(String operadorUnario){
		this.operadorUnario = operadorUnario;
	}

	public ElementoOctave getElemento(){
		return this.elemento;
	}
	public void setElemento(ElementoOctave elemento){
		this.elemento = elemento;
	}
	public String getOperadorUnario(){
		return this.operadorUnario;
	}
}