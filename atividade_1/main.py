#========================Integrantes do Grupo===============================
#Renan Parpinelli Scarpin        número USP 14712188
#Arthur Ernesto de carvalho      número USP 14559479
#Vinicius Gustierrez Neves       número USP 14749363
#===========================================================================

"""Observação: 
 Como a função checkPrimeAux não é um modelo de função que poderia ser utilizado para outros propósitos, 
 os testes nela utilizados não são passados como argumentos usando lambdas assim como seria no seguinte exemplo:
 * teste_maior: bool = lambda x,y: x>y
 * teste_primo: bool = lambda x,y: (x%y == 0)

 projetoFuncionalRec(a: int, b: int, maior_intervalo: int, intervalo_atual: int, contando: bool, teste_maior: bool, teste_primo: bool, checkRelation: cakkable) -> int
 checkPrimeAux(a: int, b: int, teste_primo: bool, teste_maior: bool) -> bool
 
 
 Preceitos funcionais utilizados no código:
- Utilização de funções de ordem superior (Funções como parâmetros de outras)
- Recursão para evitar loops
- Imutabilidade e transparência referencial -> Não há variáveis mutáveis e funções sempre retornam o mesmo valor para os mesmos argumentos
"""

#Verifica se a é divisível por b e recursivamente verifica se a é divisível por qualquer número maior que b e menor que a
def checkPrimeAux(a: int, b: int) -> bool:
    if(b * b > a): 
        return True
    elif(a % b == 0):
        return False
    else:
        return checkPrimeAux(a, b + 1)

#verifica se um valor a é primo
def checkPrime (a: int) -> bool:
    return checkPrimeAux(a, 2)

def projetoFuncionalRec(a: int, b: int, maior_intervalo: int, intervalo_atual: int, contando: bool, checkRelation: callable) -> int:
    if(a > b): #Indica que a já assumiu todos os valores entre a e b, logo tudo foi percorrido e o maior intervalo entre primos consecutivos foi encontrado
        return maior_intervalo
    if(checkRelation(a)): #Quando enconra um primo, o intervalo atual volta para 1 e o contando vira true
        if(intervalo_atual > maior_intervalo): #Se o intervalo atual é maior que o maior intervalo, o intervalo atual substitui o maior intervalo
            return projetoFuncionalRec(a + 1, b, intervalo_atual, 1, True, checkRelation)
        else:
            return projetoFuncionalRec(a + 1, b, maior_intervalo, 1, True, checkRelation)
    else: #a não é primo, logo mantém a contagem atual do intervalo
        if(contando):
            return projetoFuncionalRec(a + 1, b, maior_intervalo, intervalo_atual + 1, contando, checkRelation)
        else:
            return projetoFuncionalRec(a + 1, b, maior_intervalo, intervalo_atual, contando, checkRelation)

#Retorna o maior intevalo entre primos consecutivos entre a e b
def projetoFuncional(a: int, b: int, checkRelation: callable) -> int:
    return projetoFuncionalRec(a, b, 0, 0, False, checkRelation)

a: int = int(input())
b: int = int(input())
print(projetoFuncional(a, b, checkPrime));