import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String primer = scan.nextLine();
        if (primer.matches("\\d+\\s*[+\\-*/]\\s*\\d+")) {
            Arab(primer);
        } else if (primer.matches("M{0,5}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})\\s*[+\\-*/]\\s*M{0,5}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})")) {
            Rim(primer);
        }
        else if(primer.matches("M{0,5}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})(\\d{0,2})\\s*[+\\-*/]\\s*M{0,5}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})(\\d{0,2})")) {
            throw new IllegalArgumentException("throws Exception //т.к. используются одновременно разные системы счисления");
        } else if (primer.matches("[IVXLCDM\\d+]+\\s*[+\\-*/]\\s*[IVXLCDM\\d+]+\\s*[+\\-*/]\\s*[IVXLCDM\\d+]+")) {
            throw new RuntimeException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else {
            throw new RuntimeException("throws Exception //т.к. строка не является математической операцией");
        }
    }

    public static void Arab(String primerArab) {
        String sad = null;
        int sum = 0;
        String primer = primerArab;
        String[] parts = primer.split("[\\+\\-\\*\\/]");
        Pattern pattern = Pattern.compile("[+\\-*/]");
        Matcher matcher = pattern.matcher(primer);
        if (matcher.find()) {
            sad = matcher.group();
        }
        try {
            int a = Integer.parseInt(parts[0].trim());
            int b = Integer.parseInt(parts[1].trim());
            if (a > 0 && a <= 10 && b > 0 && b <= 10) {
                switch (sad) {
                    case "+":
                        sum = a + b;
                        break;
                    case "-":
                        sum = a - b;
                        break;
                    case "*":
                        sum = a * b;
                        break;
                    case "/":
                        sum = a / b;
                }
                System.out.println(sum);
            }
            else {
                throw new RuntimeException("Одно или оба числа вне допустимого диапазона [1, 10]");
            }
        } catch (Exception e) {
            System.err.println("throws Exception: " + e.getMessage());
        }

    }

    public static void Rim(String primerRim) {
        String sad = null;
        int sum = 0;
        String primer = primerRim;
        String[] parts = primer.split("[+\\-*/]");
        Pattern pattern = Pattern.compile("[+\\-*/]");
        Matcher matcher = pattern.matcher(primer);

        if (matcher.find()) {
            sad = matcher.group();
        }

        try {
            int a = rimToArab(parts[0].trim());
            int b = rimToArab(parts[1].trim());

            if (a > 0 && a <= 10 && b > 0 && b <= 10) {
                switch (sad) {
                    case "+":
                        sum = a + b;
                        break;
                    case "-":
                        sum = a - b;
                        break;
                    case "*":
                        sum = a * b;
                        break;
                    case "/":
                        sum = a / b;
                        break;
                    default:
                        throw new IllegalArgumentException("Неверный оператор: " + sad);
                }

                if (sum > 0) {
                    System.out.println(arabToRim(sum));
                } else {
                    throw new RuntimeException("//т.к. в римской системе нет отрицательных чисел и чисел меньше I");
                }
            } else {
                throw new IllegalArgumentException("Одно или оба числа вне допустимого диапазона [I, X]");
            }
        } catch (Exception e) {
            System.err.println("throws Exception: " + e.getMessage());
        }

    }

    public static int rimToArab(String rim) {
        Map<Character, Integer> keyMap = new HashMap<>();
        keyMap.put('I', 1);
        keyMap.put('V', 5);
        keyMap.put('X', 10);
        keyMap.put('L', 50);
        keyMap.put('C', 100);
        keyMap.put('D', 500);
        keyMap.put('M', 1000);
        int result = 0;
        int pred = 0;
        for (int i = rim.length() - 1; i >= 0; i--) {
            if (keyMap.get(rim.charAt(i)) != null) {
                int currentValue = keyMap.get(rim.charAt(i));
                if (currentValue < pred) {
                    result -= currentValue;
                } else {
                    result += currentValue;
                }
                pred = currentValue;
            }
        }
        return result;
    }

    public static String arabToRim(int result) {
        TreeMap<Integer, String> keyMap = new TreeMap<>();
        keyMap.put(1000, "M");
        keyMap.put(900, "CM");
        keyMap.put(500, "D");
        keyMap.put(400, "CD");
        keyMap.put(100, "C");
        keyMap.put(90, "XC");
        keyMap.put(50, "L");
        keyMap.put(40, "XL");
        keyMap.put(10, "X");
        keyMap.put(9, "IX");
        keyMap.put(5, "V");
        keyMap.put(4, "IV");
        keyMap.put(1, "I");
        String rim = "";
        int arabKey;
        do {
            arabKey = keyMap.floorKey(result);
            rim += keyMap.get(arabKey);
            result -= arabKey;

        } while (result != 0);
        return rim;
    }
}