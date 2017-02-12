package br.octave.ASTOctave.ast;

public class OctaveErrorLineParse extends ElementoOctave{

	private String name;
	private String value;
	
	public OctaveErrorLineParse(String name, String value){
		this.name = name;
		this.value = value;
	}
	public String getName(){
		return this.name;
	}
	public String getValue(){
		return this.value;
	}
}