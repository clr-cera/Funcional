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
    let a = map read (words line1) :: [Int]
    let b = countIntervalo 0 a
    let maior = getMax b
    print maior


-- Função que conta os intervalos crescentes e retorna um vetor com seus valores
countIntervalo :: (Ord a, Num b) => b -> [a] -> [b]
countIntervalo count [] = [count]
countIntervalo count [x] = [count + 1]
countIntervalo count (x1:x2:xs)
    | x1 < x2   = countIntervalo (count + 1) (x2:xs)
    | otherwise = (count + 1):countIntervalo 0 (x2:xs)

-- Função que escolhe o maior elemento de um vetor
getMax :: (Ord a, Num a) => [a] -> a
getMax [] = 0
getMax [x] = x
getMax (x1:xs) 
    | x1 > x2 = x1
    | otherwise = x2
    where x2 = getMax xs
