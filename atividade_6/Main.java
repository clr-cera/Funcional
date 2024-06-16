/* 
    ========================Integrantes do Grupo===============================
    Renan Parpinelli Scarpin        número USP 14712188
    Arthur Ernesto de carvalho      número USP 14559479
    Vinicius Gustierrez Neves       número USP 14749363
    ===========================================================================
    */

    import java.util.*;
    import java.util.stream.*;
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
        public String getpais() {
            return pais;
        }
    
        public int getmortes() {
            return mortes;
        }

        public int get_recuperados(){
            return recuperados;
        }

        public int getconfirmed() {
            return confirmed;
        }

        public int getcasos_ativos() {
            return casos_ativos;
        }

        
        //Método para dividir a linha do arquivo CSV e retornar um objeto DADOSCOVID
        public static DADOSCOVID split_dados(String linha_CSV) {
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

        //Função recursiva para imprimir a lista de países
        public static void imprimeListaRecursivo(List<String> lista, int i) {
            if (i < lista.size()) {
                System.out.println(lista.get(i));
                imprimeListaRecursivo(lista, i + 1);
            }
            return;
        }

        public static void main(String[] args) throws IOException {

            //Ler os valores de entrada (4 inteiros)
            Scanner s = new Scanner(System.in);
    
            int n1 = s.nextInt(); //Limite de casos confirmados
            int n2 = s.nextInt(); // Quantidade de países com maiores casos ativos
            int n3 = s.nextInt(); // Quantidade de países com menos mortes cnnfrimadas dentre os n2 países com maiores casos ativos
            int n4 = s.nextInt(); // Quantidade de países com maiores casos confirmados

            s.close();
            
            //Uso de streams para ler o arquivo e slitar os campos
            List<DADOSCOVID> paises = Files.lines(Paths.get("dados.csv")) //Cria uma lista de países utilizando o arquivo CSV e a classe auxiliar DADOSCOVID
                .map(DADOSCOVID::split_dados) //Aplica o split dos dados em cada pais
                .collect(Collectors.toList());
    
            //Soma de "casos_ativos" de todos os países em que "confirmed" >= n1
            int sumcasos_ativos = paises.stream()
                .filter(c -> c.getconfirmed() >= n1) //Aplica um filtro em que só os paises da lista que satisfazem a condição são considerados
                .mapToInt(DADOSCOVID::getcasos_ativos) //Pega o número de casos ativos de cada país
                .sum(); //Soma o número de casos ativos
            System.out.println(sumcasos_ativos);
    
            //Soma de "mortes" dos n3 países com menores valores de "confirmed" entre os n2 países com maiores valores de "casos_ativos"
            int sum_mortes = paises.stream()
                .sorted(Comparator.comparingInt(DADOSCOVID::getcasos_ativos).reversed())//Ordena a lista de países de acordo com o número de casos ativos
                .limit(n2)//Só passam os n2 primeiros 
                .sorted(Comparator.comparingInt(DADOSCOVID::getconfirmed)) //Ordena agora por número de casos confirmados
                .limit(n3) //Só passam os n3 primeiros
                .mapToInt(DADOSCOVID::getmortes)
                .sum(); //Soma o número de mortes
            System.out.println(sum_mortes);
    
            //Os n4 países com maiores valores de "Confirmed", nomes em ordem alfabética
            List<String> top_paises_confirmados = paises.stream()
                .sorted(Comparator.comparingInt(DADOSCOVID::getconfirmed).reversed()) //Ordena a lista de países de acordo com o número de casos confirmados
                .limit(n4) //Só passam os n4 primeiros
                .map(DADOSCOVID::getpais) //Pega o nome de cada país
                .sorted() //Ordena os nomes em ordem alfabética
                .collect(Collectors.toList()); //Formo uma lista com os paises
            imprimeListaRecursivo(top_paises_confirmados, 0); //Como não podemos utilizar loops, utilizamos uma função recursiva para imprimir a lista ordenada
        }
    }
    