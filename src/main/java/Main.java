import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Calculator(initPersonsCount());
    }

    /**
     * Получает количество персон
     */
    private static int initPersonsCount() {
        Scanner scanner = new Scanner(System.in);
        int personsCount;

        System.out.println("На сколько человек нужно разделить счет?");
        while (true) {
            // Пытаемся прочитать число персон, если не удается выводим предупреждение и повторяем попытку
            if (scanner.hasNextInt()) {
                personsCount = scanner.nextInt();

                // Если получили количество персон более 2х останавливаем ввод,
                // в противном случае выводим предупреждение и повторяем попытку
                if (personsCount > 1) {
                    break;
                } else {
                    System.out.println("Число не может быть меньше 2, повторите ввод");
                    scanner.nextLine();
                }
            } else {
                System.out.println("Некорретные данные, повторите ввод");
                scanner.nextLine();
            }
        }

        return personsCount;
    }
}