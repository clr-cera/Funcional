/* 
    ========================Integrantes do Grupo===============================
    Renan Parpinelli Scarpin        número USP 14712188
    Arthur Ernesto de carvalho      número USP 14559479
    Vinicius Gustierrez Neves       número USP 14749363
    ===========================================================================
*/

import java.util.*;
import java.nio.file.*;
import java.io.IOException;

//Classe que representa os dados de um país no arquivo CSV
class DADOSCOVID {

    //Variáveis para armazenar esse dados
    private final String pais;
    private final int confirmed;
    private final int mortes;
    private final int recuperados; //Acabamos por não utilizar pois não é exigido pelo trabalho
    private final int casos_ativos;
    
    //Construtor da classe 
    public DADOSCOVID(String pais, int confirmed, int mortes, int recuperados, int casos_ativos) {
        this.pais = pais;
        this.confirmed = confirmed;
        this.mortes = mortes; 
        this.casos_ativos = casos_ativos;
        this.recuperados = recuperados;
    }
    
    //Métodos para retornar os valores das variáveis da classe auxiliar
    public String getPais() {
        return pais;
    }

    public int getMortes() {
        return mortes;
    }

    public int getRecuperados(){
        return recuperados;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getCasosAtivos() {
        return casos_ativos;
    }

    
    //Método para dividir a linha do arquivo CSV e retornar um objeto DADOSCOVID
    public static DADOSCOVID splitDados(String linha_CSV) {
        String[] campos = linha_CSV.split(","); //Dividir a linha do arquivo CSV

        //Jogar os dados na estrutura de forma correta
        DADOSCOVID struct = new DADOSCOVID(
            campos[0].trim(), //Nome
            Integer.parseInt(campos[1].trim()), //Confirmed
            Integer.parseInt(campos[2].trim()), //Mortes
            Integer.parseInt(campos[3].trim()), //Recuperados
            Integer.parseInt(campos[4].trim()) //Casos ativos
        );
        return struct;
    }
}

//Classe principal
public class Main {
    public static void main(String[] args) throws IOException {

        //Ler os valores de entrada (4 inteiros)
        Scanner s = new Scanner(System.in);

        int n1 = s.nextInt(); //Limite de casos confirmados
        int n2 = s.nextInt(); // Quantidade de países com maiores casos ativos
        int n3 = s.nextInt(); // Quantidade de países com menos mortes cnnfrimadas dentre os n2 países com maiores casos ativos
        int n4 = s.nextInt(); // Quantidade de países com maiores casos confirmados

        s.close();
        
        //Uso de loop para ler o arquivo e dividir os campos
        LinkedList<DADOSCOVID> paises = new LinkedList<>();
        List<String> lines = Files.readAllLines(Paths.get("dados.csv"));
        for (String line : lines) {
            paises.add(DADOSCOVID.splitDados(line));
        }

        //Soma de "casos_ativos" de todos os países em que "confirmed" >= n1
        int sumCasosAtivos = 0;
        for (DADOSCOVID pais : paises){
            if (pais.getConfirmed() >= n1) { 
                sumCasosAtivos += pais.getCasosAtivos(); 
            }
        }
        System.out.println(sumCasosAtivos);
        
        //Soma de "mortes" dos n3 países com menores valores de "confirmed" entre os n2 países com maiores valores de "casos_ativos"
        LinkedList<DADOSCOVID> copia0 = new LinkedList<>(paises); //Como a lista será alterada, é criado uma cópia para que a lista original se mantenha e possa ser utilizada novamente depois.
        copia0.sort(Comparator.comparingInt(DADOSCOVID::getCasosAtivos)); //Ordena pelo número de casos ativos
        for(;copia0.size() > n2; copia0.removeFirst()); //Remove os menores até sobrar apenas os n2 maiores
        copia0.sort(Comparator.comparingInt(DADOSCOVID::getConfirmed)); //Ordena pelo número de confirmados
        for(;copia0.size() > n3; copia0.removeLast()); //Remove os maiores até sobrar apenas os n3 menores
        int sum_mortes = 0; //Inicializa a soma como 0
        for(DADOSCOVID pais : copia0){sum_mortes += pais.getMortes();}//Soma as mortes dos países restantes
        System.out.println(sum_mortes); //Imprime a soma

        //Os n4 países com maiores valores de "Confirmed", nomes em ordem alfabética
        LinkedList<DADOSCOVID> copia1 = new LinkedList<>(paises); //Como a lista será alterada, é criado uma cópia para que a lista original se mantenha e possa ser utilizada novamente depois.
        copia1.sort(Comparator.comparingInt(DADOSCOVID::getConfirmed)); //Ordena pelo número de confirmados
        for(;copia1.size() > n4; copia1.removeFirst()); //Remove os menores até sobrar apenas n4
        copia1.sort(Comparator.comparing(DADOSCOVID::getPais)); //Ordena por ordem alfabética
        for(DADOSCOVID pais : copia1){System.out.println(pais.getPais());} //Imprime a lista restante
    }
}
