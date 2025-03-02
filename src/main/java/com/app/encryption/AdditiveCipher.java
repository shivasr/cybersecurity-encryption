package com.app.encryption;


import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class AdditiveCipher {

    private final Map<String, Integer> mapOfAlphabetToNumber = new HashMap<>();
    private final Map<Integer, String> mapOfNumberToAlphabet = new HashMap<>();

    public AdditiveCipher() {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < alphabets.length(); i ++) {
            mapOfNumberToAlphabet.put(i, String.valueOf(alphabets.charAt(i)));
            mapOfAlphabetToNumber.put(String.valueOf(alphabets.charAt(i)), i);
        }
    }

    public String encryptPlainText(String plainText, int key, boolean debug) {

        int[] numbers = new int[plainText.length()];
        int[] cypherTextValues = new int[plainText.length()];
        final StringBuilder cypherTextBuffer = new StringBuilder();

        if (debug) {
            log.debug("\n\n");
            log.debug("Encrypting cypher Text ===> {}", plainText);
        }

        for (int i = 0; i < plainText.length(); i++) {
            String plainTextChar = String.valueOf(plainText.charAt(i));
            numbers[i] = mapOfAlphabetToNumber.get(plainTextChar);

            cypherTextValues[i] = Math.floorMod(numbers[i] + key, 26);

            String cypherTextChar = mapOfNumberToAlphabet.get(cypherTextValues[i]);

            cypherTextBuffer.append(cypherTextChar);

        }

        if (debug) {
            log.debug(Stream.of(plainText.split(""))
                    .map (String::new)
                    .collect(Collectors.joining("\t")));


            log.debug(Arrays.stream(numbers)
                    .mapToObj(number -> number + "")
                    .collect(Collectors.joining("\t")));


            log.debug(Arrays.stream(cypherTextValues)
                    .mapToObj(number -> number + "")
                    .collect(Collectors.joining("\t")));



            log.debug(String.join("\t", cypherTextBuffer.toString().split("")) + "\n");


            log.debug("Cypher Text ==> " + cypherTextBuffer);

            log.debug("\n\n");

        }

        return cypherTextBuffer.toString();
    }

    public String decryptCypherText(String cypherText, int key, boolean debug) {

        int[] numbers = new int[cypherText.length()];
        int[] plainTextValues = new int[cypherText.length()];
        final StringBuilder plainTextBuffer = new StringBuilder();

        if (debug) {
            log.debug("\n\n");
            log.debug("Decrypting cypher Text ===> " + cypherText);
        }

        for (int i = 0; i < cypherText.length(); i++) {
            String cypherTextChar = String.valueOf(cypherText.charAt(i));
            numbers[i] = mapOfAlphabetToNumber.get(cypherTextChar);

            plainTextValues[i] = Math.floorMod(numbers[i] - key, 26);

            String plainTextChar = mapOfNumberToAlphabet.get(plainTextValues[i]);

            plainTextBuffer.append(plainTextChar);

        }

       if (debug) {
           log.debug(Stream.of(cypherText.split(""))
                   .map (String::new)
                   .collect(Collectors.joining("\t")));

           log.debug(Arrays.stream(numbers)
                   .mapToObj(number -> number + "")
                   .collect(Collectors.joining("\t")));

           log.debug(Arrays.stream(plainTextValues)
                   .mapToObj(number -> number + "")
                   .collect(Collectors.joining("\t")));

           log.debug(String.join("\t", plainTextBuffer.toString().split("")) + "\n");
           log.debug("Plain Text ==> "+ plainTextBuffer);
           log.debug("\n\n");
           

       }

        return plainTextBuffer.toString();
    }


    public int bruteForceAttack(String cypherText, String sensibleText) {

        int key = -1;
        for (int i = 0; i < 26; i++) {

            String decrypted = decryptCypherText(cypherText, i, false);

            log.debug("key = {} \t--> {}", i, decrypted);

            if (decrypted.equals(sensibleText)) {
                log.debug("Brute force attack done at key: " + i);
                key = i;
                break;
            }


        }

        return key;
    }

    public static void main(String[] args) {
        AdditiveCipher additiveCipher = new AdditiveCipher();
        String cypherText = additiveCipher.encryptPlainText("SHIVAKUMAR", 10, true);

        additiveCipher.decryptCypherText(cypherText, 10, true);

       int bruteForce = additiveCipher.bruteForceAttack("CRSFKUEWKB", "SHIVAKUMAR");
        log.debug(String.valueOf(bruteForce));
    }
}
