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
    let pinos = map read (words line1) :: [Int]
    let frames = contagem pinos
    let pontos = pontuacao pinos
    putStrLn $ frames ++ show pontos

--Função que retorna a string da contagem de pontos formatado conforme as specificações
contagem :: [Int] -> String
contagem [x1,x2,x3] -- Ultimas 3 jogadas
    | x1 == 10      = "X " ++ contagem [x2,x3]
    | x1 + x2 == 10 = showl x1 ++ " / " ++ showl x3 ++ " | "
contagem [x1,x2] -- Ultimas 2 jogadas
    | x1 + x2 == 10 = showl x1 ++ " / | " --Caso seja um Spare
    | otherwise     = showl x1 ++ " " ++ showl x2 ++ " | "--Caso não seja um spare
contagem (10:xs) = "X _ | " ++ contagem xs
contagem (x1:x2:xs)
    | x1 + x2 == 10 = showl x1 ++ " / | " ++ contagem xs
    | otherwise     = showl x1 ++ " " ++ showl x2 ++ " | " ++ contagem xs
contagem _ = ""

--Função que calcula a pontuação da partida de boliche
pontuacao :: [Int] -> Int
pontuacao [x1,x2,x3] = x1 + x2 + x3 --O ultimo frame pode ter três jogadas
pontuacao [x1,x2] = x1 + x2 --Caso o ultimo frame tenha apenas 2 jogadas
pontuacao (x1:x2:x3:xs)
    | x1 == 10       = x1 + x2 + x3 + pontuacao (x2:x3:xs) --strike
    | x1 + x2 == 10  = x1 + x2 + x3 + pontuacao (x3:xs) --spare
    | otherwise      = x1 + x2 + pontuacao (x3:xs)

--Mistura de show com bowl, mostra os números como apareciam na tabela de pontos do Boliche
showl :: Int -> String
showl 10 = "X"
showl x = show x