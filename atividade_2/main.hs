{-
========================Integrantes do Grupo===============================
Renan Parpinelli Scarpin        número USP 14712188
Arthur Ernesto de carvalho      número USP 14559479
Vinicius Gustierrez Neves       número USP 14749363
===========================================================================
-}

main :: IO ()
main = do
    line1 <- getLine
    line2 <- getLine
    let a = read line1
    let b = read line2
    let d = primeDist a b
    print d

--Função com menos parametros para facilitar o uso, serve apenas para chamar a função com mais parametros e com os parametros iniciais corretos
primeDist :: Integer -> Integer -> Integer
primeDist a b = relationDistRec a b 0 0 False checkPrime

--Função que realmente resolve o problema
relationDistRec :: Integer -> Integer -> Integer -> Integer -> Bool -> (Integer -> Bool) -> Integer
relationDistRec a b maior_intervalo intervalo_atual contando checkRelation
    | a > b = maior_intervalo --Condição de parada da recursão
    --apenas se a for primo
    | checkRelation a = if intervalo_atual > maior_intervalo
                        then relationDistRec (a+1) b intervalo_atual 1 True checkRelation --maior intervalo <- intervalo atual & contando <- true
                        else relationDistRec (a+1) b maior_intervalo 1 True checkRelation --maior intervalo permanece & contando <-  true
    --apenas se a não for primo
    | otherwise =       if contando
                        then relationDistRec (a+1) b maior_intervalo (intervalo_atual+1) contando checkRelation --segue contando o intervalo
                        else relationDistRec (a+1) b maior_intervalo intervalo_atual   contando checkRelation --segue não contando o intervalo

--Função que verifica se um número é primo utilizando uma função auxiliar recursiva
checkPrime :: Integer -> Bool
checkPrime n = checkPrimeRec n 2

--Função auxiliar recursiva para verificar se um número é primo
checkPrimeRec :: Integer -> Integer -> Bool
checkPrimeRec n root
    | root*root > n = True
    | n `rem` root == 0 = False
    | otherwise = checkPrimeRec n (root+1)
