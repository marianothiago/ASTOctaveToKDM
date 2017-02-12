package br.octave.ASTOctave.ast;

public class OctaveClear extends ElementoOctave{

	private String value;
	
	public OctaveClear(String value){
		this.value = value;
	}
	public String getOctaveExpressao(){
		return this.value;
	}
}