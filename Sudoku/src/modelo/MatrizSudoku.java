package modelo;

import java.util.ArrayList;

public class MatrizSudoku {

	private char[][] tableroInicial = new char[9][9];
	private boolean esCorrecto;
	private ArrayList<char[][]> matricesInternas = new ArrayList<char[][]>();
	private ArrayList<char[]> filas = new ArrayList<char[]>();
	private ArrayList<char[]> columnas = new ArrayList<char[]>();

	public MatrizSudoku(char[][] tableroInicial) {
		super();
		this.tableroInicial = tableroInicial;
		obtenerMatricesInternas();
		sacarEnArreglo(0);
		sacarEnArreglo(1);
		evaluarPosibilidadesSubMatriz(matricesInternas.get(0), 0);
	}

	public void llenarTablero(){
		
		for (int i = 0; i < tableroInicial.length; i++) {
			for (int j = 0; j < tableroInicial.length; j++) {
				
			}
		}
	}

	public char[] evaluarPosibilidadesSubMatriz(char[][] matrizInterna, int indiceMatrizInterna){
		char[] arreglo = new char[9];
		int indice = 0;

		for (int i = 0; i < matrizInterna.length; i++) {
			for (int j = 0; j < matrizInterna.length; j++) {
				if(matrizInterna[i][j]!=' '){
					arreglo[indice] = 'x';
				}else{
					arreglo[indice] = ' ';
				}
				indice++;
			}
		}

		return arreglo;
	}

	public char [] valoresActuales(char[] o){
		char[] arr = new char[9];
		for (int i = 0; i < o.length; i++) {
			if(o[i] == ' '){
				arr[i]=' ';
				System.out.println("este es el num "+arr[i]+"este el indice"+i);
			}else{
				arr[i]='x';
			}
			System.out.println(arr[i]);
		}
		return arr;
	}

	public char[] validar_casilla(char[]fila, char[] col, char[] interna){
		char [] retorno= new char[9];
		for (int i = 0; i < 9;  i++) {
			if(fila[i]!='x' || col[i]!='x'|| interna[i]!='x'){
				retorno[i]='x';
			}else {
				boolean probar=estaCompleto();
				if(probar==true){
					System.out.println("el sudoku ya esta completo");
				}else{
					boolean otro=estaCorrecto();
					if(otro==false){
						System.out.println("tenemos problemas ");
					} 
				}	 
			}
		}
		return retorno ;	 
	}

	public boolean estaCompleto(){

		for (int i = 0; i < tableroInicial.length; i++) {
			for (int j = 0; j < tableroInicial[i].length; j++) {
				if(tableroInicial[i][j] == '0' || tableroInicial[i][j] == ' '){
					return false;
				}
			}
		}

		return true;
	}	

	public boolean estaCorrecto(){
		esCorrecto =  true;

		if(tieneRepetidosCoF(0) || tieneRepetidosCoF(1) || tieneRepetidosEnSub_Matrices()) esCorrecto = false;

		return esCorrecto;
	}

	public boolean tieneRepetidosCoF(int op){//0 para filas, 1 para columnas

		if(op == 0){
			char[] temp = new char[9];
			for (int i = 0; i < filas.size(); i++) {
				temp = filas.get(i);
				boolean tiene = tieneRepetidos(temp);
				if(tiene == true){
					System.out.println("Valor del boolean: "+tiene);
					return true;
				}
			}
		}else{
			char[] temp = new char[9];
			for (int i = 0; i < columnas.size(); i++) {
				temp = columnas.get(i);
				boolean tiene = tieneRepetidos(temp);
				if(tiene){
					System.out.println("Valor del boolean: "+tiene);
					return true;
				}
			}
		}


		return false;
	}

	public boolean tieneRepetidosEnSub_Matrices(){

		char[][] temp = new char[3][3];
		for (int i = 0; i < matricesInternas.size(); i++) {
			temp = matricesInternas.get(i);
			boolean tiene = tieneRepetidosMatrizIterna(temp);
			if(tiene){
				System.out.println("Valor del boolean: "+tiene);
				return true;
			}
		}		
		return false;
	}

	/*
	 * Este metodo sirve para sacar en arreglos cada una de las filas o columnas (depende de
	 * la opcion dada) y se almacenan en las listas que le corresponde.
	 */
	public void sacarEnArreglo(int opcion){ //0 por filas, 1 por columnas

		if(opcion == 0){//por filas

			for (int i = 0; i < tableroInicial.length; i++) {
				char[] arreglo = new char[9];
				for (int j = 0; j < tableroInicial.length; j++) {
					arreglo[j] = tableroInicial[i][j];
				}
				filas.add(arreglo);
			}
		}else{
			for (int i = 0; i < tableroInicial.length; i++) {
				char[] arreglo = new char[9];

				for (int j = 0; j < tableroInicial.length; j++) {
					char var = tableroInicial[j][i];
					arreglo[j] = var;
				}
				columnas.add(arreglo);
			}
		}
	}

	/*
	 * Este m�todo sirve para revisar si un arreglo tiene o no valores repetidos dentro
	 * de este.
	 */
	public boolean tieneRepetidos(char[] arreglo){

		for(int i=0;i<arreglo.length-1;i++){
			for(int j=i+1;j<arreglo.length;j++){
				if(arreglo[i]!=' '){
					if(arreglo[i]==arreglo[j]){
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * Este m�todo valida que una matriz interna (la matriz de 3x3) no tenga valores repetidos
	 */
	public boolean tieneRepetidosMatrizIterna(char[][] matrizInterna){
		char[] arreglo = new char[9];
		int indice = 0;
		for (int i = 0; i < matrizInterna.length; i++) {
			for (int j = 0; j < matrizInterna.length; j++) {
				arreglo[indice] = matrizInterna[i][j];
				indice++;
			}
		}

		boolean tieneRepetidos = tieneRepetidos(arreglo);

		return tieneRepetidos;
	}

	/*
	 * Con este metodo se hallan las sub-matrices que conforman el sudoku y se almacenan en un
	 * ArrayList llamado matricesInternas
	 */
	public void obtenerMatricesInternas(){

		int cIni = 0;
		int fIni = 0;
		int cantMatrices = 0;

		do {
			char[][] matriz = new char[3][3];

			switch (cantMatrices) {
			case 0:
				cIni = 0;
				fIni = 0;
				break;

			case 1:
				cIni = 0;
				fIni = 3;
				break;

			case 2:
				cIni = 0;
				fIni = 6;
				break;

			case 3:
				cIni = 3;
				fIni = 0;
				break;

			case 4:
				cIni = 3;
				fIni = 3;
				break;

			case 5:
				cIni = 3;
				fIni = 6;
				break;

			case 6:
				cIni = 6;
				fIni = 0;
				break;

			case 7:
				cIni = 6;
				fIni = 3;
				break;

			case 8:
				cIni = 6;
				fIni = 6;
				break;

			default:
				break;
			}

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i+fIni<9 && j+cIni<9){
						matriz[i][j] =  tableroInicial[i+fIni][j+cIni];
					}
				}
			}

			matricesInternas.add(matriz);

			cantMatrices = cantMatrices+1;

		} while (cantMatrices < 9);
	}

	public void imprimirTablero(){
		String matriz = "";
		for (int i = 0; i < tableroInicial.length; i++) {
			for (int j = 0; j < tableroInicial.length; j++) {
				if(j==0) matriz += "| ";
				if(j != 8){
					if(j==2||j==5){
						matriz += tableroInicial[i][j]+" || ";
					}else{
						matriz += tableroInicial[i][j]+" | ";
					}
				}else{
					if(i==2||i==5){
						//matriz += "| 1 | 2 | 3 || 4 | 5 | 6 || 7 | 8 | 9 |"
						matriz += tableroInicial[i][j]+" |\n";
						matriz += "---------------------------------------\n";
					}else{
						matriz += tableroInicial[i][j]+" |\n";
					}
				}
			}
		}
		System.out.println(matriz);
	}

	public void imprimirFilasOColumnas(int op){ //0 para filas, 1 para columnas
		if(op == 0){
			System.out.println("Contenido por filas");
			char[] temp = new char[9];
			for (int i = 0; i < filas.size(); i++) {
				temp = filas.get(i);
				System.out.println("Fila numero: "+i);
				for (int j = 0; j < temp.length; j++) {
					System.out.print(temp[j]+" ");
				}
				System.out.println("\n");
			}
		}else{
			System.out.println("Contenido por columnas");
			char[] temp = new char[9];
			for (int i = 0; i < columnas.size(); i++) {
				temp = columnas.get(i);
				System.out.println("Columna numero: "+i);
				for (int j = 0; j < temp.length; j++) {
					System.out.print(temp[j]+" ");
				}
				System.out.println("\n");
			}
		}
	}

}