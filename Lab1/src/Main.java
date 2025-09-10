import java.util.Scanner;

public class Main {
    interface Strategy {
        int move();
    }

    static class RideStrategy implements Strategy {
        public int move() {
            System.out.println("Твой герой теперь скачет на коне");
            return 15;
        }
    }

    static class WalkStrategy implements Strategy {
        public int move() {
            System.out.println("Твой герой теперь идет пешком");
            return 5;
        }
    }

    static class FlyStrategy implements Strategy {
        public int move() {
            System.out.println("Твой герой теперь летит");
            return 40;
        }
    }

    static class Hero {
        private Strategy strategy;

        public Hero() {}

        public void setStrategy(Strategy strategy) {
            this.strategy = strategy;
        }

        public int move() {
            return strategy.move();
        }
    }

    public static void main(String[] args) {
        Hero hero = new Hero();

        Scanner scanner = new Scanner(System.in);

        int moveSpeed = 0, choice = 5, distance = 100;
        boolean loop = true;
        System.out.println("До пункта назначения " + distance + " километров");
        System.out.println("Выберите метод передвижения: \n 1. Пешком \n 2. На коне \n 3. Лететь " +
        "\n 4. Выполнить передвижение \n Любое другое - Выйти\n");

        while (loop && distance > 0) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1: hero.setStrategy(new WalkStrategy());
                        moveSpeed = hero.move();
                    break;
                    case 2: hero.setStrategy(new RideStrategy());
                        moveSpeed = hero.move();
                    break;
                    case 3: hero.setStrategy(new FlyStrategy());
                        moveSpeed = hero.move();
                    break;
                    case 4: distance -= Math.min(moveSpeed, distance);
                        System.out.println("До пункта назначения " + distance + " километров");
                    break;
                    default: loop = false;
                    break;
                }
            } else {
                System.out.println("Введена не цифра!\n");
                scanner.nextLine();
                continue;
            }
        }
        if (distance <= 0) {
            System.out.println("Вы в пункте назначения!\n");
        }
    }
}