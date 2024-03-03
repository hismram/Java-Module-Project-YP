import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
    final static String END_COMMAND = "Завершить";
    // Количество человек
    int personsCount;
    // Общая стоимость
    float total;
    // Сумма на человека
    float byPerson;
    // Список товаров
    ArrayList<Good> goods = new ArrayList<>();

    Calculator(int personsCount) {
        this.personsCount = personsCount;
        addGoods();
        printResult();
    }

    /**
     * Запрашивает у пользователя список товаров
     */
    public void addGoods() {
        // Сканер
        Scanner scanner = new Scanner(System.in);

        // Добавление товаров продалжается пока не получим команду на завершение
        while (true) {
            // Если есть добавленные продукты спрашиваем нужно ли добавить еще один
            if (!goods.isEmpty()) {
                System.out.println("Добавить еще один товар?");
                System.out.println("Для завершение формирования списка товаров введите 'Завершить'");
                if (scanner.nextLine().equalsIgnoreCase(Calculator.END_COMMAND)) {
                    break;
                } else {
                    System.out.println("Введите назвние и стоимость товара");
                }
            } else {
                System.out.println("Введите назвние и стоимость товара");
            }
            // Формат строки для добавления
            System.out.println("[Название] [Стоимость]");
            // Считываем товар
            readGood();
        }
    }

    /**
     * Выводит результат
     */
    public void printResult() {
        System.out.println("Добавленные товары:");
        for (Good good : goods) {
            // Выводим данные товара
            System.out.printf("%s %.2f %s%n", good.name, good.price, Formatter.format(good.price));
        }

        System.out.println("-------------------");
        // Выводим общую стоимость
        System.out.printf("Итого: %.2f %s%n", total, Formatter.format(total));
        // Выводим стоимость на человека
        System.out.printf("На человека: %.2f %s%n", byPerson, Formatter.format(byPerson));
    }

    private void readGood() {
        // Сканер
        Scanner scanner = new Scanner(System.in);
        // Цена товара
        float price;
        // Название товара
        String name;

        while (true) {
            String input = scanner.nextLine();
            // Пытаемся распарсить полученную строку, в случае ошибки выводим предупреждение о некорректном формате
            try {
                // Начинаем с последнего блока, это цена
                price = Float.parseFloat(input.substring(input.lastIndexOf(' ')));

                if (price <= 0) {
                    System.out.println("Стоимость товара должна быть больше нуля, повторите ввод");
                    continue;
                }
                // Все что до последнего пробела это название: "Томаты в собственном соку", "Каре ягненка"...
                name = input.substring(0, input.lastIndexOf(' '));
                addGood(name, price);
                System.out.println("Товар успешно добавлен");
                break;
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                // Получили менее 2 элементов или последний элемент это не стоимость
                System.out.println("Некорректный формат ввода, повторите ввод");
            }
        }
    }

    /**
     * Добавляет товар в список и обновляет суммы
     *
     * @param name Название продукта
     * @param price Стоимость продукта
     */
    private void addGood(String name, float price) {
        goods.add(new Good(name, price));
        total += price;
        byPerson = total / personsCount;
    }
}

class Good {
    // Название продукта
    String name;
    // Стоимость продукта
    float price;

    Good(String name, float price) {
        this.name = name;
        this.price = price;
    }
}

class Formatter {
    // Возможные варианты
    final static String[] cases = new String[]{"рубль", "рубля", "рублей"};

    /**
     * Возвращает форматированную строку
     * @param value дробная сумма
     */
    static String format(float value) {
        String result;
        // Окончание числа до 99
        int num = toInt(value) % 100;

        // Если окончание от 5 до 20 будет "рублей"
        if (num >= 5 && num <= 20) {
            result = cases[2];
        } else {
            // Окончание числа до 9
            num = num % 10;

            // От 1 до 4 - рубля
            if (num > 1 && num < 5) {
                result = cases[1];
                // 0 и от 5 до 9 - рублей
            } else if (num == 0 || num >= 5) {
                result = cases[2];
            } else {// остается только 1 - рубль
                result = cases[0];
            }
        }

        return result;
    }

    /**
     * Преобразует сумму к инту
     * @param value дробная сумма
     */
    static int toInt(float value) {
        return (int) value;
    }
}
