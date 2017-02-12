package br.octave.ASTOctave.ast;

public class OctaveExpressao extends ElementoOctave{
	
	private ElementoOctave elemento;
	
	public void setElemento(ElementoOctave elemento){
		this.elemento = elemento;
	}
	public ElementoOctave getExpressao(){
		return this.elemento;
	}
}