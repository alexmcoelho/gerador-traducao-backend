import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConverterTranslate {
	
	private List<String> listaFrontEnd = new ArrayList<>();
	private List<String> listaResulFrontEnd = new ArrayList<>();
	
	//e vírgula também
	public String tirarAspasDuplo(String texto) {
		texto = texto.replace("\"", "");
		texto = texto.replace(",", "");
		return texto; 
	}
	
	public String acrescetaEnum(String texto) {
		String[] array = texto.split(Pattern.quote ("."));
		StringBuilder builder = new StringBuilder();
		builder.append(array[0])
		.append(".")
		.append(array[1])
		.append(".enum_")
		.append(array[2]);
		return builder.toString();
	}
	
	public String trocarUnderlinePorLetraMaiuscula(String texto) {
		String[] array = texto.split(Pattern.quote ("_type_"));
		String[] arrayAux = array[0].split(Pattern.quote ("_"));
		StringBuilder builder = new StringBuilder();
		builder.append(arrayAux[0])
		.append("_")
		.append(arrayAux[1])
		.append("_");
		for (int i = 2; i < arrayAux.length; i++) {
			builder.append(arrayAux[i].substring(0, 1).toUpperCase())
			.append(arrayAux[i].substring(1, arrayAux[i].length()));
		}
		builder.append("Type_");
		builder.append(array[1].toUpperCase());
		
		return builder.toString();
	}
	
	public ConverterTranslate() {

        // abre um arquivo e cria um file
        File arquivoTxt = new File("front-end.txt");
        
        String[] valoresEntreVirgulas = null;

        String vaiRecebendo = "";

        // cria um scanner para ler o arquivo
        Scanner leitor = null;

        //String que será salvo no converter.txt
        StringBuilder builder = new StringBuilder();

        //escreve no arquivo
        FileWriter arquivo;

        try {
            leitor = new Scanner(arquivoTxt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // percorre todo o arquivo
        while (leitor.hasNext()) {
            // recebe cada linha do arquivo
        	valoresEntreVirgulas = leitor.nextLine().split(":");
            listaFrontEnd.add(valoresEntreVirgulas[0]);
            listaResulFrontEnd.add(tirarAspasDuplo(valoresEntreVirgulas[1]));
        }
        
        for (int i = 0; i < listaFrontEnd.size(); i++) {
        	vaiRecebendo = "";
    		vaiRecebendo = tirarAspasDuplo(listaFrontEnd.get(i));
    		vaiRecebendo = acrescetaEnum(vaiRecebendo);
    		vaiRecebendo = trocarUnderlinePorLetraMaiuscula(vaiRecebendo);
    		builder.append(vaiRecebendo)
    		.append("=")
    		.append(listaResulFrontEnd.get(i))
    		.append("\r\n");
		}
        
        try {
            arquivo = new FileWriter(new File("back-end.txt"));
            arquivo.write(builder.toString());
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConverterTranslate converterTranslate = new ConverterTranslate();
		String vaiRecebendo = "";
		vaiRecebendo = converterTranslate.tirarAspasDuplo("\"hcm.general_register.movimentation_reason_type_hire\": \"0 - Admissão\",");
		vaiRecebendo = converterTranslate.acrescetaEnum(vaiRecebendo);
		vaiRecebendo = converterTranslate.trocarUnderlinePorLetraMaiuscula(vaiRecebendo);
		System.out.println(vaiRecebendo);
	}
	
	

}
