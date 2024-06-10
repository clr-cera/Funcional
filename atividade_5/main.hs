import Data.List (sortBy)
{-
========================Integrantes do Grupo===============================
Renan Parpinelli Scarpin        número USP 14712188
Arthur Ernesto de carvalho      número USP 14559479
Vinicius Gustierrez Neves       número USP 14749363
===========================================================================
-}

-- Registro que correponde a uma entrada do csv
data Register = Register {
    country :: [Char],
    confirmed :: Int,
    deaths :: Int,
    recovery :: Int,
    active :: Int
}
    deriving (Read,  Show, Eq, Ord)

-- Função que transforma um conjunto de strings em um registro
contentToRegister :: [[Char]] -> Register
contentToRegister content =
    Register {
        country = head content,
        confirmed = read $ head $ tail content,
        deaths = read $ head $ tail $ tail content,
        recovery = read $ head $ tail $ tail $ tail content,
        active = read $ head $ tail $ tail $ tail $ tail content
    }

main :: IO ()
main = do

-- Lê os inputs
    input <- getContents 
    let intList :: [Int] = map  read  (words input)

    let n1 = head intList
    let n2 = head $ tail intList
    let n3 = head $ tail $ tail intList
    let n4 = head $ tail $ tail $ tail intList

--Lê e processa o arquivo csv
    everything <- readFile "dados.csv"
    let first = lines everything
    let parsed = map (split (== ',')) first
    let registers = map contentToRegister parsed

-- Calcula o item 1)
    let a :: Int = sum $ 
            map active $
            filter ((>=n1).confirmed) registers

-- Calcula o item 2)
    let b :: Int = sum $
            map deaths $
            take n3 $
            sortBy (\x y -> compare (confirmed x) (confirmed y)) $
            take n2 $
            sortBy (\x y -> compare (active y) (active x)) registers

-- Calcula o item 3)
    let c :: [[Char]] =  map country $
            sortBy (\x y -> compare (country x) (country y)) $
            take n4 $
            sortBy (\x y -> compare (confirmed y) (confirmed x)) registers

    
    print a
    print b
    mapM_ putStrLn c



-- Função que divide uma string em múltiplas levando em consideração uma função (Char-> Bool), no qual o char é o separador
split :: (Char -> Bool) -> String -> [String]
split separator string =  case dropWhile separator string of
                      "" -> []
                      str -> word : split separator rest
                            where (word, rest) = break separator str