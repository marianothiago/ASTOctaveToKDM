package br.octave.ASTOctave.ast;

public class Block extends ElementoOctave{

	private String nomeBloco = "";
	
	public Block(String nomeBloco){
		this.nomeBloco = nomeBloco;
	}
	
	public String getNomeBloco(){
		return this.nomeBloco;
	}
}