main :: IO ()
main = do
    line1 <- getLine
    line2 <- getLine
    let a = read line1
    let b = read line2
    let d = primeDist a b
    print d

primeDist :: Integer -> Integer -> Integer
primeDist a b = relationDistRec a b 0 0 False checkPrime

relationDistRec :: Integer -> Integer -> Integer -> Integer -> Bool -> (Integer -> Bool) -> Integer
relationDistRec a b maior_intervalo intervalo_atual contando checkRelation
    | a > b = maior_intervalo
    | checkRelation a = if intervalo_atual > maior_intervalo
                        then relationDistRec (a+1) b intervalo_atual 1 True checkRelation
                        else relationDistRec (a+1) b maior_intervalo 1 True checkRelation

    | otherwise =       if contando
                        then relationDistRec (a+1) b maior_intervalo (intervalo_atual+1) contando checkRelation
                        else relationDistRec (a+1) b maior_intervalo intervalo_atual   contando checkRelation

checkPrime :: Integer -> Bool
checkPrime n = checkPrimeRec n 2

checkPrimeRec :: Integer -> Integer -> Bool
checkPrimeRec n root
    | root*root > n = True
    | n `rem` root == 0 = False
    | otherwise = checkPrimeRec n (root+1)