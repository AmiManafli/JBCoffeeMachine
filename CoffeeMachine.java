package machine;
import java.nio.channels.SelectableChannel;
import java.util.Scanner;
public class CoffeeMachine {
    static int water = 400, milk = 540, coffeeBeans = 120, money = 550, disposableCups = 9;
    //water, milk, coffee beans, price
    static int[] espresso = new int[]{250, 0, 16, 4};
    static int[] latte = new int[]{350, 75, 20, 7};
    static int[] cappuccino = new int[]{200, 100, 12, 6};
    static boolean turnOff = false;
    static Scanner scanner = new Scanner(System.in);

    enum State {
        SELECT_ACTION, BUY_COFFEE, FILL_WATER, FILL_MILK, FILL_BEANS,
        FILL_CUPS, TAKE, EXIT
    }

    private static State state = State.SELECT_ACTION;

    public static void main(String[] args) {
        while (state != State.EXIT) {
            printString();
            String action = scanner.next();
            processCommands(action);
        }
    }

    public static void processCommands(String action) {
        switch(state) {
            case SELECT_ACTION:
                selectAction(action);
                break;
            case BUY_COFFEE:
                buyCoffee(action);
                break;
            case FILL_WATER:
                fillWater(action);
                break;
            case FILL_MILK:
                fillMilk(action);
                break;
            case FILL_BEANS:
                fillCoffeeBeans(action);
                break;
            case FILL_CUPS:
                fillCups(action);
                break;
            case TAKE:
                takeMoney();
                break;
        }

    }

    private static void selectAction(String action) {
        switch (action) {
            case "buy":
                state = State.BUY_COFFEE;
                break;
            case "fill":
                state = State.FILL_WATER;
                break;
            case "take":
                takeMoney();
                break;
            case "remaining":
                printResources();
                state = State.SELECT_ACTION;
                break;
            case "exit":
                state = State.EXIT;
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    public static void printResources() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(coffeeBeans + " of coffee beans");
        System.out.println(disposableCups + " of disposable cups");
        System.out.println(money + " of money");
        System.out.println();

        state = State.SELECT_ACTION;
    }

    public static void buyCoffee(String action) {
        switch (action) {
            case "1":
                makeCoffee(espresso);
                break;
            case "2":
                makeCoffee(latte);
                break;
            case "3":
                makeCoffee(cappuccino);
                break;
            case "back":
                state = State.SELECT_ACTION;
                break;
            case "remaining":
                printResources();
                break;
            default:
                System.out.println("Invalid coffee type.");
        }
    }

    public static void makeCoffee(int[] coffee) {
        int isEnoughResources = calculateResources(coffee);
        if (isEnoughResources == -1) {
            System.out.println("Sorry, not enough water!");
        } else if (isEnoughResources == -2) {
            System.out.println("Sorry, not enough milk!");
        } else if (isEnoughResources == -3) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (disposableCups == 0) {
            System.out.println("Sorry, not enough disposable cups!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= coffee[0];
            milk -= coffee[1];
            coffeeBeans -= coffee[2];
            money += coffee[3];
            disposableCups--;
        }
        state = State.SELECT_ACTION;
    }

    public static void fillWater(String action) {
        water += Integer.parseInt(action);
        state = State.FILL_MILK;
    }

    public static void fillMilk(String action) {
        milk += Integer.parseInt(action);
        state = State.FILL_BEANS;
    }

    public static void fillCoffeeBeans(String action) {
        coffeeBeans += Integer.parseInt(action);
        state = State.FILL_CUPS;
    }

    public static void fillCups(String action) {
        disposableCups += Integer.parseInt(action);
        state = State.SELECT_ACTION;
    }

    public static void takeMoney() {
        System.out.println("I gave you $" + money);
        money = 0;
        state = State.SELECT_ACTION;
    }

    public static void printString() {
        switch (state) {
            case SELECT_ACTION:
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                break;
            case BUY_COFFEE:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                break;
            case FILL_WATER:
                System.out.println("Write how many ml of water do you want to add:");
                break;
            case FILL_MILK:
                System.out.println("Write how many ml of milk do you want to add:");
                break;
            case FILL_BEANS:
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;
            case FILL_CUPS:
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
        }
    }

    public static int calculateResources(int[] coffee) {
        int cupsWater = water / coffee[0];
        int cupsCoffeeBeans = coffeeBeans / coffee[2];
        int cupsMilk = 1;
        if (coffee[1] != 0) {
            cupsMilk = milk / coffee[1];
        }

        if (cupsWater == 0) { return -1; }
        else if (cupsMilk == 0) { return -2; }
        else if (cupsCoffeeBeans == 0) { return -3; }
        else return 0;
    }
}
