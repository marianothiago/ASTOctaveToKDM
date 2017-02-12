package br.octave.astOctaveToKDM.octaveKDM;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.octave.ASTOctave.ast.Block;
import br.octave.ASTOctave.ast.ElementoOctave;
import br.octave.ASTOctave.ast.OctaveAnonymousFunction;
import br.octave.ASTOctave.ast.OctaveArray;
import br.octave.ASTOctave.ast.OctaveCaseSwitch;
import br.octave.ASTOctave.ast.OctaveChamadaFuncao;
import br.octave.ASTOctave.ast.OctaveClassDef;
import br.octave.ASTOctave.ast.OctaveClear;
import br.octave.ASTOctave.ast.OctaveConstante;
import br.octave.ASTOctave.ast.OctaveDo;
import br.octave.ASTOctave.ast.OctaveElseIf;
import br.octave.ASTOctave.ast.OctaveError;
import br.octave.ASTOctave.ast.OctaveErrorLineParse;
import br.octave.ASTOctave.ast.OctaveEvents;
import br.octave.ASTOctave.ast.OctaveExpressao;
import br.octave.ASTOctave.ast.OctaveExpressaoAtribuicao;
import br.octave.ASTOctave.ast.OctaveExpressaoAutoDecremento;
import br.octave.ASTOctave.ast.OctaveExpressaoAutoIncremento;
import br.octave.ASTOctave.ast.OctaveExpressaoBinaria;
import br.octave.ASTOctave.ast.OctaveExpressaoLooping;
import br.octave.ASTOctave.ast.OctaveExpressaoTranspose;
import br.octave.ASTOctave.ast.OctaveExpressaoUnaria;
import br.octave.ASTOctave.ast.OctaveFor;
import br.octave.ASTOctave.ast.OctaveFunction;
import br.octave.ASTOctave.ast.OctaveGlobal;
import br.octave.ASTOctave.ast.OctaveHandleFunction;
import br.octave.ASTOctave.ast.OctaveIdentificador;
import br.octave.ASTOctave.ast.OctaveIf;
import br.octave.ASTOctave.ast.OctaveImport;
import br.octave.ASTOctave.ast.OctaveMatriz;
import br.octave.ASTOctave.ast.OctaveOperacaoComparacao;
import br.octave.ASTOctave.ast.OctaveOperacaoLogica;
import br.octave.ASTOctave.ast.OctaveParFor;
import br.octave.ASTOctave.ast.OctavePersistent;
import br.octave.ASTOctave.ast.OctaveProperties;
import br.octave.ASTOctave.ast.OctaveString;
import br.octave.ASTOctave.ast.OctaveStructure;
import br.octave.ASTOctave.ast.OctaveSwitch;
import br.octave.ASTOctave.ast.OctaveVariavel;
import br.octave.ASTOctave.ast.OctaveVariavelStructure;
import br.octave.ASTOctave.ast.OctaveWarning;
import br.octave.ASTOctave.ast.OctaveWhile;
import br.octave.structureKDM.CompilationUnit;
import br.octave.structureKDM.KDMSegment;
import br.octave.structureKDM.ParameterUnit;
import br.octave.structureKDM.Signature;
import br.octave.structureKDM.codeElement.AbstractCodeElement;
import br.octave.structureKDM.codeElement.ActionElement;
import br.octave.structureKDM.codeElement.BlockUnit;
import br.octave.structureKDM.codeElement.CallableUnit;
import br.octave.structureKDM.codeElement.StorableUnit;
import br.octave.structureKDM.inventoryElement.Directory;
import br.octave.structureKDM.inventoryElement.SourceFile;
import br.octave.structureKDM.model.CodeModel;
import br.octave.structureKDM.model.InventoryModel;

public class ParseOctaveASTToKDM {

	private List<String> listFilesError;
	private ArvoreOctave ast;

	private KDMSegment segment;
	private InventoryModel inventory;
	private CodeModel code;
	private CompilationUnit compilation;
	private List<String> filesName;
	private int qtdParsedFiles = 0;

	public OctaveElementsKDM loadProject(File directory, String nameProject) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IOException{
		String messageError = "", messageParsedFiles = "";
		if (nameProject.trim().equals("")) {
			segment = new KDMSegment(directory.getName());
		} else {
			segment = new KDMSegment(nameProject);
		}
		this.inventory = new InventoryModel("source references");
		this.code = new CodeModel(nameProject);
		this.segment.addSegment(code);
		this.segment.addSegment(inventory);
		loadFile(directory);
		if (this.qtdParsedFiles > 0) {
			messageParsedFiles = this.qtdParsedFiles + " Octave files were parsed.";
		}
		if (this.filesName != null) {
			messageError = "Can't was possible to parse " + this.filesName.size() + " Octave files:\n\n";
			Iterator<String> itr = this.filesName.iterator();
			while (itr.hasNext()) {
				messageError = messageError + itr.next() + "\n";
			}
		}
		return new OctaveElementsKDM(this.segment, messageParsedFiles + "\n\n" + messageError);
	}

	private void loadFile(File directory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
			SecurityException, ClassNotFoundException, IOException {
		Directory dir = new Directory(directory.getPath());
		this.inventory.addInventoryElement(dir);
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				loadFile(file);
			} else {
				if (file.getName() != null) {
					if (file.getName().length() >= 3) {
						if (file.getName().substring(file.getName().length() - 2, file.getName().length())
								.equals(".m")) {

							ParseOctaveToAST oct = new ParseOctaveToAST();
							try {
								this.ast = oct.generateAST(file.toString());
//								if (this.ast.getListError().size() > 0) {
//									System.out.println(
//											file.toString() + " ---------- Qtd: " + this.ast.getListError().size());
//								}
								this.qtdParsedFiles++;
								SourceFile source = new SourceFile(file.getName(), file.getPath());
								this.compilation = new CompilationUnit(file.getName());
								sweepAST(ast.getPai(), this.compilation);
								this.inventory.addInventoryElement(source);
								this.code.addModule(this.compilation);
							} catch (Exception e) {
								if (this.listFilesError == null)
									this.listFilesError = new ArrayList<String>();
								this.listFilesError.add(file.getAbsolutePath());
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	private void sweepAST(No astNode, AbstractCodeElement codeElement)
			throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
			SecurityException, ClassNotFoundException {
	
		AbstractCodeElement element = parseToken(astNode.getElemento(), codeElement);
		List<No> filhos = astNode.getFilhos();
		if ((filhos != null) && (filhos.size() > 0)) {
			for (int x = 0; x < filhos.size(); x++) {
				No filho = (No) filhos.get(x);
				sweepAST(filho, element);
			}
		}
		
	}
	
	private AbstractCodeElement parseToken(ElementoOctave elementoAST, AbstractCodeElement parentCodeElement) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IOException {
		AbstractCodeElement codeElement = null;
		if(elementoAST != null){
			String classe = elementoAST.getClass().getSimpleName();
	
			NodeOctave z = NodeOctave.valueOf(classe);
			System.out.println(classe);
			switch (z) {
			case OctaveConstante: {
				OctaveConstante token = (OctaveConstante) elementoAST;
				codeElement = new ActionElement("constant", token.getValue());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveExpressaoAtribuicao: {
				OctaveExpressaoAtribuicao token = (OctaveExpressaoAtribuicao) elementoAST;
				codeElement = new ActionElement("assignment","assignment");
				AbstractCodeElement storable = new StorableUnit("local");
				parseToken(token.getVar(),storable);
				codeElement.addCodeElement(storable);
				parseToken(token.getValorAtribuido(),codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveFunction: {
				OctaveFunction token = (OctaveFunction)elementoAST;
				
				Signature signature = new Signature();
				ParameterUnit parameter = null;
				
				int x = 0;
				if(token.getRetorno() != null){
					parameter = new ParameterUnit(token.getRetorno(),"return",x);
					signature.addParameter(parameter);
				}
				if(token.getParametros() != null){
					for(x=0; x<token.getParametros().size();x++){
						ElementoOctave param = (ElementoOctave)token.getParametros().get(x);
						if(param instanceof OctaveIdentificador){
							parameter = new ParameterUnit(((OctaveIdentificador)param).getNome(),"parameter",x+1);
						}else{
							if(param instanceof OctaveHandleFunction){
								parameter = new ParameterUnit("@"+((OctaveHandleFunction)param).getIdentificador().getNome(),"parameter",x+1);
							}
						}
						signature.addParameter(parameter);
					}
				}
				codeElement = new CallableUnit(token.getIdentificador().getNome(), signature);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveVariavel: {
				OctaveVariavel variable = (OctaveVariavel)elementoAST;
				codeElement = new ActionElement("variable declaration", variable.getIdentificador().getNome());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveExpressaoAutoDecremento: {
				OctaveExpressaoAutoDecremento token = (OctaveExpressaoAutoDecremento)elementoAST;
				codeElement = new ActionElement("auto decrement expression", token.getOctaveVariavel().getIdentificador().getNome());
				parentCodeElement.addCodeElement(codeElement);
			}
			    break;
			case OctaveExpressaoAutoIncremento: {
				OctaveExpressaoAutoIncremento token = (OctaveExpressaoAutoIncremento)elementoAST;
				codeElement = new ActionElement("auto increment expression", token.getOctaveVariavel().getIdentificador().getNome());
				parentCodeElement.addCodeElement(codeElement);
			}
			    break;
			case OctaveExpressaoLooping: {
				OctaveExpressaoLooping token = (OctaveExpressaoLooping)elementoAST;
				codeElement = new ActionElement("looping expression", "looping expression");
				parseToken(token.getApartirDe(), codeElement);
				parseToken(token.getAte(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
			    break;
			case OctaveExpressaoTranspose: {
				OctaveExpressaoTranspose token = (OctaveExpressaoTranspose)elementoAST;
				codeElement = new ActionElement("transpose expression", token.getOperacao());
				parseToken(token.getIdentificador(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
			    break;
			case OctaveExpressaoBinaria: {
				OctaveExpressaoBinaria binaryExpression = (OctaveExpressaoBinaria)elementoAST;
				if (binaryExpression.getOperador().equals("+") || binaryExpression.getOperador().equals(".+")){
					codeElement = new ActionElement("aritimetic operator", "plus");
				}
				if (binaryExpression.getOperador().equals("-") || binaryExpression.getOperador().equals(".-")){
					codeElement = new ActionElement("aritimetic operator", "minus");
				}
				if (binaryExpression.getOperador().equals("*") || binaryExpression.getOperador().equals(".*")){
					codeElement = new ActionElement("aritimetic operator", "multiply");
				}
				if (binaryExpression.getOperador().equals("/") || binaryExpression.getOperador().equals("./")){
					codeElement = new ActionElement("aritimetic operator", "slice");
				}
				if (binaryExpression.getOperador().equals("\\") || binaryExpression.getOperador().equals(".\\")){
					codeElement = new ActionElement("aritimetic operator", "slice");
				}
				if (binaryExpression.getOperador().equals("^") || binaryExpression.getOperador().equals("**")
					||	binaryExpression.getOperador().equals(".^") || binaryExpression.getOperador().equals(".**")){
					codeElement = new ActionElement("aritimetic operator", "power");
				}
				parentCodeElement.addCodeElement(codeElement);
				parseToken(binaryExpression.getParte1(), codeElement);
				parseToken(binaryExpression.getParte2(), codeElement);
			}
				break;
			case OctaveOperacaoComparacao: {
				OctaveOperacaoComparacao comparison = (OctaveOperacaoComparacao)elementoAST;
				if (comparison.getOperador().equals(">")){
					codeElement = new ActionElement("comparison operator", "greater");
				}
				if (comparison.getOperador().equals(">=")){
					codeElement = new ActionElement("comparison operator", "greater equals");
				}
				if (comparison.getOperador().equals("<")){
					codeElement = new ActionElement("comparison operator", "less");
				}
				if (comparison.getOperador().equals("<=")){
					codeElement = new ActionElement("comparison operator", "less equals");
				}
				if (comparison.getOperador().equals("==")){
					codeElement = new ActionElement("comparison operator", "equals");
				}
				if (comparison.getOperador().equals("!=")){
					codeElement = new ActionElement("comparison operator", "not equals");
				}
				if (comparison.getOperador().equals("~=")){
					codeElement = new ActionElement("comparison operator", "not equals");
				}
				parentCodeElement.addCodeElement(codeElement);
				parseToken(comparison.getPrimeiroOperando(), codeElement);
				parseToken(comparison.getSegundoOperando(), codeElement);
			}
				break;
			case OctaveOperacaoLogica: {
				OctaveOperacaoLogica logical = (OctaveOperacaoLogica)elementoAST;
				if (logical.getOperador().equals("&")){
					codeElement = new ActionElement("logical operator", "and");
				}
				if (logical.getOperador().equals("&&")){
					codeElement = new ActionElement("logical operator", "and");
				}
				if (logical.getOperador().equals("|")){
					codeElement = new ActionElement("logical operator", "or");
				}
				if (logical.getOperador().equals("||")){
					codeElement = new ActionElement("logical operator", "or");
				}
				if (logical.getOperador().equals("~")){
					codeElement = new ActionElement("logical operator", "not");
				}
				if (logical.getOperador().equals("!")){
					codeElement = new ActionElement("logical operator", "not");
				}
				if (logical.getOperador().equals("~~")){
					codeElement = new ActionElement("logical operator", "not");
				}
				parentCodeElement.addCodeElement(codeElement);
				parseToken(logical.getPrimeiroOperando(), codeElement);
				parseToken(logical.getSegundoOperando(), codeElement);
			}
				break;
			case OctaveFor: {
				OctaveFor token = (OctaveFor)elementoAST;
				codeElement = new ActionElement("for","for");
				parseToken(token.getVariavel(), codeElement);
				parseToken(token.getOctaveExpressao(), codeElement);
				AbstractCodeElement codeElementBlock = new BlockUnit("block for");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveParFor: {
				OctaveParFor token = (OctaveParFor)elementoAST;
				codeElement = new ActionElement("parfor","parfor");
				parseToken(token.getVariavel(), codeElement);
				parseToken(token.getOctaveExpressao(), codeElement);
				AbstractCodeElement codeElementBlock = new BlockUnit("block for");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;	
			case OctaveGlobal: {
				OctaveGlobal token = (OctaveGlobal)elementoAST;
				codeElement = new ActionElement("global variable", "global variable");
				parseToken(token.getOctaveExpressao(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveChamadaFuncao: {
				OctaveChamadaFuncao token = (OctaveChamadaFuncao)elementoAST;
				codeElement = new ActionElement("call",token.getIdentificador().getNome());
				AbstractCodeElement parameters = new ActionElement("parameters", "parameters");
				codeElement.addCodeElement(parameters);
				if(token.getParametros() != null){
					for(int x = 0; x < token.getParametros().size(); x++){
						parseToken(token.getParametros().get(x), parameters);
					}
				}
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveMatriz: {
				OctaveMatriz matriz = (OctaveMatriz)elementoAST;
				
				if(matriz.getIdentificador() != null)
				    codeElement = new ActionElement("matriz expression",matriz.getIdentificador().getNome());
				else
					codeElement = new ActionElement("matriz expression", "matriz expression");
				
				AbstractCodeElement matrixValue = new ActionElement("matriz value", matriz.getValor());
				codeElement.addCodeElement(matrixValue);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveArray: {
				OctaveArray array = (OctaveArray)elementoAST;
				
				if(array.getIdentificador() != null)
				    codeElement = new ActionElement("matriz expression",array.getIdentificador().getNome());
				else
					codeElement = new ActionElement("matriz expression", "matriz expression");
				
				AbstractCodeElement matrixValue = new ActionElement("matriz value", array.getValor());
				codeElement.addCodeElement(matrixValue);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveStructure: {
				OctaveStructure structure = (OctaveStructure)elementoAST;
				codeElement = new ActionElement("structure definition", structure.getValor());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveVariavelStructure: {
				OctaveVariavelStructure structure = (OctaveVariavelStructure)elementoAST;
				codeElement = new ActionElement("structure variable accessing", structure.getValor());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctavePersistent: {
				OctavePersistent token = (OctavePersistent)elementoAST;
				codeElement = new ActionElement("persistent variable", "persistent variable");
				parseToken(token.getOctaveExpressao(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveExpressao: {
				if (!(elementoAST instanceof OctaveExpressaoAtribuicao) &&
					!(elementoAST instanceof OctaveExpressaoAutoDecremento) &&
					!(elementoAST instanceof OctaveExpressaoAutoIncremento) &&
					!(elementoAST instanceof OctaveExpressaoBinaria) &&
					!(elementoAST instanceof OctaveExpressaoLooping) &&
					!(elementoAST instanceof OctaveExpressaoTranspose) &&
					!(elementoAST instanceof OctaveExpressaoUnaria) &&
					!(elementoAST instanceof OctaveOperacaoComparacao) &&
					!(elementoAST instanceof OctaveOperacaoLogica)){
					parseToken(((OctaveExpressao)elementoAST).getExpressao(), parentCodeElement);
				}
			}
				break;
			case OctaveIf: {
				OctaveIf token = (OctaveIf)elementoAST;
				codeElement = new ActionElement("if", "if");
				parseToken(token.getOctaveExpressao(), codeElement);
				AbstractCodeElement codeElementBlock = new BlockUnit("block if");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveElseIf: {
				OctaveElseIf token = (OctaveElseIf)elementoAST;
				codeElement = new ActionElement("elseif", "elseif");
				parseToken(token.getOctaveExpressao(), codeElement);
				AbstractCodeElement codeElementBlock = new BlockUnit("block elseif");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveElse: {
				codeElement = new ActionElement("else", "else");
				AbstractCodeElement codeElementBlock = new BlockUnit("block else");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveWhile: {
				OctaveWhile token = (OctaveWhile)elementoAST;
				codeElement = new ActionElement("while", "while");
				parseToken(token.getOctaveExpressao(), codeElement);
				AbstractCodeElement codeElementBlock = new BlockUnit("block while");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveDo: {
				OctaveDo token = (OctaveDo)elementoAST;
				codeElement = new ActionElement("do", "do");
				parseToken(token.getOctaveExpressao(), codeElement);
				AbstractCodeElement codeElementBlock = new BlockUnit("block while");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveTry: {
				codeElement = new ActionElement("try", "try");
				AbstractCodeElement codeElementBlock = new BlockUnit("block try");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveCatch: {
				codeElement = new ActionElement("catch", "catch");
				AbstractCodeElement codeElementBlock = new BlockUnit("block catch");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveUnwindProtect: {
				codeElement = new ActionElement("unwindprotect", "unwindprotect");
				AbstractCodeElement codeElementBlock = new BlockUnit("block unwindprotect");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;	
			case OctaveUnwindProtectCleanup: {
				codeElement = new ActionElement("unwindprotectcleanup", "unwindprotectcleanup");
				AbstractCodeElement codeElementBlock = new BlockUnit("block unwindprotectcleanup");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;	
			case OctaveSwitch: {
				OctaveSwitch token = (OctaveSwitch)elementoAST;
				codeElement = new ActionElement("switch", "switch");
				parseToken(token.getOctaveExpressao(), codeElement);
				
				AbstractCodeElement codeElementBlock = new BlockUnit("block switch");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveCaseSwitch: {
				OctaveCaseSwitch token = (OctaveCaseSwitch)elementoAST;
				codeElement = new ActionElement("case switch", "case switch");
				parseToken(token.getOctaveExpressao(), codeElement);
				
				AbstractCodeElement codeElementBlock = new BlockUnit("block case switch");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveOtherwiseSwitch: {
				codeElement = new ActionElement("otherwise switch", "otherwise switch");
				
				AbstractCodeElement codeElementBlock = new BlockUnit("block otherwise switch");
				codeElement.addCodeElement(codeElementBlock);
				parentCodeElement.addCodeElement(codeElement);
				codeElement = codeElementBlock;
			}
				break;
			case OctaveExpressaoUnaria: {
				OctaveExpressaoUnaria token = (OctaveExpressaoUnaria)elementoAST;
				codeElement = new ActionElement("unary expression", token.getOperadorUnario());
				parseToken(token.getElemento(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveBreak: {
				codeElement = new ActionElement("break", "break");
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveContinue: {
				codeElement = new ActionElement("continue", "continue");
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveReturn: {
				codeElement = new ActionElement("return", "return");
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case Block: {
				Block token = (Block)elementoAST;
				codeElement = new BlockUnit(token.getNomeBloco());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveString: {
				OctaveString text = (OctaveString)elementoAST;
				codeElement = new ActionElement("text", text.getValor());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
//			case OctaveIdentificador: {
//				OctaveIdentificador identifier = (OctaveIdentificador)elementoAST;
//				codeElement = new ActionElement("identifier", identifier.getNome());
//				parentCodeElement.addCodeElement(codeElement);
//			}
//				break;
			case OctaveHandleFunction: {
				OctaveHandleFunction token = (OctaveHandleFunction)elementoAST;
				codeElement = new ActionElement("function handle", token.getIdentificador().getNome());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveAnonymousFunction: {
				OctaveAnonymousFunction token = (OctaveAnonymousFunction)elementoAST;
				codeElement = new ActionElement("function anonymous handle", "function anonymous handle");
				AbstractCodeElement expression = new ActionElement("expression anonymous function", "expression anonymous function");
				parseToken(token.getExpressao(), expression);
				codeElement.addCodeElement(expression);
				AbstractCodeElement parameters = new ActionElement("parameters", "parameters");
				codeElement.addCodeElement(parameters);
				if(token.getParametros() != null){
					for(int x = 0; x < token.getParametros().size(); x++){
						parseToken(token.getParametros().get(x), parameters);
					}
				}
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveClassDef: {
				OctaveClassDef token = (OctaveClassDef)elementoAST;
				codeElement = new ActionElement("OctaveClassDef", "OctaveClassDef");
				parseToken(token.getExpression(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveClear: {
				OctaveClear token = (OctaveClear)elementoAST;
				codeElement = new ActionElement("OctaveClear", token.getOctaveExpressao());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveError: {
				OctaveError token = (OctaveError)elementoAST;
				codeElement = new ActionElement("OctaveError", "OctaveError");
				parseToken(token.getOctaveExpressao(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveWarning: {
				OctaveWarning token = (OctaveWarning)elementoAST;
				codeElement = new ActionElement("OctaveWarning", "OctaveWarning");
				parseToken(token.getOctaveExpressao(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveErrorLineParse: {
				OctaveErrorLineParse token = (OctaveErrorLineParse)elementoAST;
				codeElement = new ActionElement("OctaveErrorLineParse", "OctaveErrorLineParse");
				AbstractCodeElement childCodeElement = new ActionElement("linecontent", token.getValue());
	            codeElement.addCodeElement(childCodeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveEvents: {
				OctaveEvents token = (OctaveEvents)elementoAST;
				codeElement = new ActionElement("OctaveEvents", "OctaveEvents");
				parseToken(token.getExpression(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveProperties: {
				OctaveProperties token = (OctaveProperties)elementoAST;
				codeElement = new ActionElement("OctaveProperties", "OctaveProperties");
				parseToken(token.getExpression(), codeElement);
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveImport: {
				OctaveImport token = (OctaveImport)elementoAST;
				codeElement = new ActionElement("OctaveImport", token.getValor());
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			case OctaveMethods: {
				codeElement = new ActionElement("OctaveMethods", "OctaveMethods");
				parentCodeElement.addCodeElement(codeElement);
			}
				break;
			default: {
				System.out.println(elementoAST.getClass().getSimpleName());
			}
				break;
			}
		}
		return codeElement;
	}
}
