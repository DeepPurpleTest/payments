package com.example.payments.util;

import com.example.payments.entity.CardType;

import java.util.Random;
import java.util.stream.IntStream;

public class CardNumberBuilder {
    private static final Random random = new Random();
    private CardNumberBuilder(){}

    // 5457 0822 2186 3439
    // 10 4 10 7 0 8 4 2 4 1 16 6 6 4 6 9
    // 1 + 0 + 1 + 0 + 0 + 4 + 4 + 1 + 6 + 6 + 6 = 29
    // 4 + 7 + 8 + 2 + 1 + 6 + 4 + 9 = 41

    // 4624 7482 3324 9080
    // 8 6 4 4 14 4 16 2 6 3 4 4 18 0 16 0
    // 8 + 4 + 1 4 + 1 6 + 6 + 4 + 1 8 + 1 6 = 50
    // 6 + 4 + 4 + 2 + 3 + 4 + 0 + 0 = 23

    public static String generateCardNumber(CardType type) {
        StringBuilder builder = new StringBuilder();
        builder.append(type.getPrefix());
        int size = 16 - (type.getPrefix().length() + 1);
        IntStream.range(0, size).forEach(string -> builder.append(random.nextInt(10)));
        builder.append(getCheckSum(builder.toString()));

        return builder.toString();
    }

    private static int getCheckSum(String number) {
        String[] numbers = number.split("");
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Integer.parseInt(numbers[i]);
            if (i % 2 == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }
        int mod = sum % 10;

        return ((mod == 0)? 0 : 10 - mod);
    }
}
