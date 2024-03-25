package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number <= 1) throw new IllegalNumberException();
        if (number == 2) return true;
        if (number % 2 == 0) return false;
        for (int i = 3; i * i <= number; ++i) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        int res = 0, len = len(number);
        for (int i = 0; i < len; ++i) {
            res += number % 10;
            number = number / 10;
        }
        return res;
    }

    private int len(int number) {
        int numbersCount = 0;
        while (number != 0) {
            ++numbersCount;
            number /= 10;
        }
        return numbersCount;
    }

}

