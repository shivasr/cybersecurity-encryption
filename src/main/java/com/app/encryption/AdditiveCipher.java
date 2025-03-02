package com.app.encryption;

import java.util.HashMap;
import java.util.Map;

public class AdditiveCipher {

    private Map<String, Integer> mapOfAlphabetToNumber = new HashMap<>();
    private Map<Integer, String> mapOfNumberToAlphabet = new HashMap<>();

    public AdditiveCipher() {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < alphabets.length(); i ++) {
            mapOfNumberToAlphabet.put(i, String.valueOf(alphabets.charAt(i)));
            mapOfAlphabetToNumber.put(String.valueOf(alphabets.charAt(i)), i);
        }
    }

    public String encryptPlainText(String plainText, int key) {

        int[] numbers = new int[plainText.length()];
        int[] cypherTextValues = new int[plainText.length()];
        final StringBuilder cypherTextBuffer = new StringBuilder();

        System.out.println("=================================================");
        System.out.println("Encryptin cypher Text ===> " + plainText);

        for (int i = 0; i < plainText.length(); i++) {
            String plainTextChar = String.valueOf(plainText.charAt(i));
            numbers[i] = mapOfAlphabetToNumber.get(plainTextChar);

            cypherTextValues[i] = Math.floorMod(numbers[i] + key, 26);

            String cypherTextChar = mapOfNumberToAlphabet.get(cypherTextValues[i]);

            cypherTextBuffer.append(cypherTextChar);

        }

        for (int i = 0; i < plainText.length(); i++) {
            System.out.print(plainText.charAt(i) + "\t");
        }

        System.out.println();
        for (int i = 0; i < plainText.length(); i++) {
            System.out.print(numbers[i] + "\t");
        }

        System.out.println();
        for (int i = 0; i < plainText.length(); i++) {
            System.out.print(cypherTextValues[i] + "\t");
        }

        System.out.println();

        for (int i = 0; i < plainText.length(); i++) {
            System.out.print(cypherTextBuffer.charAt(i) + "\t");
        }
        System.out.println();
        System.out.println("=================================================");
        System.out.println("\n");

        System.out.println("Cypher Text ==> "+ cypherTextBuffer);
        return cypherTextBuffer.toString();
    }

    public String decryptCypherText(String cypherText, int key) {

        int[] numbers = new int[cypherText.length()];
        int[] plainTextValues = new int[cypherText.length()];
        final StringBuilder plainTextBuffer = new StringBuilder();

        // System.out.println("=================================================");
        // System.out.println("Decrypting cypher Text ===> " + cypherText);

        for (int i = 0; i < cypherText.length(); i++) {
            String cypherTextChar = String.valueOf(cypherText.charAt(i));
            numbers[i] = mapOfAlphabetToNumber.get(cypherTextChar);

            plainTextValues[i] = Math.floorMod(numbers[i] - key, 26);

            String plainTextChar = mapOfNumberToAlphabet.get(plainTextValues[i]);

            plainTextBuffer.append(plainTextChar);

        }

        /*for (int i = 0; i < cypherText.length(); i++) {
            System.out.print(cypherText.charAt(i) + "\t");
        }

        System.out.println();
        for (int i = 0; i < cypherText.length(); i++) {
            System.out.print(numbers[i] + "\t");
        }

        System.out.println();
        for (int i = 0; i < cypherText.length(); i++) {
            System.out.print(plainTextValues[i] + "\t");
        }

        System.out.println();

        for (int i = 0; i < cypherText.length(); i++) {
            System.out.print(plainTextBuffer.charAt(i) + "\t");
        }
        System.out.println();
        System.out.println("=================================================");
        System.out.println("\n");
        System.out.println("Plain Text ==> "+ plainTextBuffer);*/
        return plainTextBuffer.toString();
    }


    public int bruteForceAttack(String cypherText, String sensibleText) {

        int key = -1;
        for (int i = 0; i < 26; i++) {

            String decrypted = decryptCypherText(cypherText, i);

            System.out.print("key = " + i + "\t\t");

            for (int j = 0; j < decrypted.length(); j++) {
                System.out.print(decrypted.charAt(j) + "\t");
            }
            System.out.println();
            if (decrypted.equals(sensibleText)) {
                System.out.println("Brute force attack done at key: " + i);
                key = i;
                break;
            }


        }

        return key;
    }

    public static void main(String[] args) {
        AdditiveCipher additiveCipher = new AdditiveCipher();
        String cypherText = additiveCipher.encryptPlainText("SHIVAKUMAR", 10);

        additiveCipher.decryptCypherText("CRSFKUEWKB", 10);

        additiveCipher.bruteForceAttack("CRSFKUEWKB", "SHIVAKUMAR");

    }
}
