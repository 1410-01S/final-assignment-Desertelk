import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    static Random generator;

    static ArrayList<String> names = new ArrayList<String>();
    static ArrayList<String> foods = new ArrayList<String>();

    static String[] baseServices = {
            "food",
            "water",
            "fuel",
            "data center",
            "passes"
    };

    static int[][] enneagramCombos = {
            { 1, 4, 7 },
            { 1, 7, 4 },
            { 2, 4, 8 },
            { 2, 8, 4 },
            { 3, 6, 9 },
            { 3, 9, 6 },
            { 4, 1, 2 },
            { 4, 2, 1 },
            { 5, 7, 8 },
            { 5, 8, 7 },
            { 6, 9, 3 },
            { 6, 3, 9 },
            { 7, 1, 5 },
            { 7, 5, 1 },
            { 8, 2, 5 },
            { 8, 5, 2 },
            { 9, 3, 6 },
            { 9, 6, 3 },
    };

    static int[][] enneagramCompat = {
            { 1, 2, 9 },
            { 2, 4, 8 },
            { 3, 1, 9 },
            { 4, 2, 9 },
            { 5, 1 },
            { 6, 8, 9, 2 },
            { 7, 5, 1 },
            { 8, 2, 9 },
            { 9, 4, 6 }
    };

    static int[][] enneagramNonCompat = {
            { 7 },
            {},
            {},
            { 8 },
            { 9 },
            {},
            {},
            { 4 },
            { 5 }
    };

    static <T> T pickRandom(T[] array) {
        int rnd = generator.nextInt(array.length);
        return array[rnd];
    }

    static <T> T pickRandomAL(ArrayList<T> array) {
        int rnd = generator.nextInt(array.size());
        return array.get(rnd);
    }

    static boolean includes(int[] array, int val) {
        for (int element : array) {
            if (element == val) {
                return true;
            }
        }

        return false;
    }

    static boolean spawnFood() {

        Scanner s = null;
        try {
                s = new Scanner(new BufferedReader(new FileReader("foods.txt")));
             
            s.useLocale(Locale.US);
            s.useDelimiter(",");

            while (s.hasNext()) {
                foods.add(s.next());
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // TODO: handle exception
            s.close();
        }

        if (!foods.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    static class Place {
        public int est = 0;
        public String name = "example";
        public int value = 0;
        int startingNumber = 0;

        ArrayList<NPC> residents;
        ArrayList<String> services;

        String[] neighbors;
        NPC[] popular;
        NPC[] unpopular;

        public Place(String iName, int iStartingNumber) {
            name = iName;
            startingNumber = iStartingNumber;

            residents = addRandomCharacters(startingNumber);
            getServices();
            // TODO
        }

        public Place(String iName) {
            name = iName;
            startingNumber = generator.nextInt(100);
            residents = addRandomCharacters(startingNumber);
            // TODO
        }

        public Place() {
            name = (generator.nextFloat() < .5) ? pickRandomAL(names) + "'s " + pickRandomAL(names)
                    : pickRandomAL(names) + "ton";
            startingNumber = generator.nextInt(100);
            residents = addRandomCharacters(startingNumber);
            // TODO

        }

        public int increaseAge() {
            return est++;
        }

        public ArrayList<NPC> addRandomCharacters(int iStartingNumber) {

            ArrayList<NPC> temp = new ArrayList<NPC>();

            for (int i = 0; i < iStartingNumber; i++) {
                temp.add(new NPC());
            }

            // TODO
            return temp;
        }

        public void getServices() {

            if (services.isEmpty()) {
                int rnd = generator.nextInt(10);

                value = rnd * 1000;
                for (int i = 0; i < rnd; i++) {
                    services.add(pickRandom(baseServices));
                }

            }
        }

        public void step() {
            for (NPC npc : this.residents) {
                npc.interact(pickRandomAL(this.residents));
            }
        }

    }

    static class NPC {

        String name = "examplePerson";
        int age = 0;
        int[] enneagram = { 1, 4, 7 };
        int happiness = 100;
        int boredom = 50;
        int hunger = 0; //When 100 is put as value of hunger, everyone dies of hunger.

        ArrayList<String> memories = new ArrayList<String>();
        ArrayList<String> enemies = new ArrayList<String>();
        ArrayList<String> friends = new ArrayList<String>();
        ArrayList<String> knowledge = new ArrayList<String>();
        ArrayList<String> thoughts = new ArrayList<String>();
        String icon = "";

        String home = "This Base";
        String[] travels = { "This Base" };

        public NPC() {
            name = pickRandomAL(names);
            enneagram = pickRandom(enneagramCombos);
            age = generator.nextInt(90) + 10;
            // TODO
        }

        public void eat() {
            boolean isHungry = true;
            int rand = (int) Math.random() * 9;

            if (this.hunger == 0) {
                isHungry = false;
            } else if (this.hunger > 0 && this.hunger < 100) {
                isHungry = true;
                System.out.println(this.name + " ate a(n) " + foods.get(0));
                this.hunger += 1;
            } else if (this.hunger == 100) {
                System.out.println(this.name + " has died of hunger");
            }
        }

        public void interact(NPC interactee) {
            int myType = this.enneagram[0];
            int yourType = interactee.enneagram[0];

            boolean firstTime = false;

            if (!(this.knowledge.contains(interactee.name) && interactee.knowledge.contains(this.name))) {
                firstTime = true;

                this.knowledge.add(interactee.name);
                interactee.knowledge.add(this.name);

                this.happiness += 2;
                interactee.happiness += 2;

                this.boredom -= 2;
                interactee.boredom -= 2;

            }

            if (includes(enneagramNonCompat[myType - 1], yourType)) {
                if (firstTime) {
                    this.enemies.add(interactee.name);
                }

                this.thoughts.add(this.name + " had a negative experience interacting with " + interactee.name);
                this.thoughts.add(interactee.name + " had a negative experience interacting with " + this.name);

                this.happiness -= 10;
                interactee.happiness -= 10;

                this.boredom -= 5;
                interactee.boredom -= 5;

                System.out.println(this.name + " had a negative experience interacting with " + interactee.name);
            } else if (includes(enneagramCompat[myType - 1], yourType)) {
                if (firstTime) {
                    this.friends.add(interactee.name);
                }

                this.thoughts.add(this.name + " had a positive experience interacting with " + interactee.name);
                this.thoughts.add(interactee.name + " had a positive experience interacting with " + this.name);

                this.happiness += 10;
                interactee.happiness += 10;

                this.boredom -= 5;
                interactee.boredom -= 5;

                System.out.println(this.name + " had a negative experience interacting with " + interactee.name);
            } else {
                this.boredom += 10;
                interactee.boredom += 10;

                this.thoughts.add(this.name + " didn't feel anything interacting with " + interactee.name);
                System.out.println(this.name + " didn't feel anything interacting with " + interactee.name);
            }
        }

        public class Character {
            public static void createCharacter (){
                Scanner userIn = new Scanner(System.in);

            System.out.println("What is your name?");
            String name = userIn.nextLine();
            int boredom = 0;
            int happiness = 100;

            System.out.println("Here is eveyone else in the worlds");
            }
        }

        public static void main(String[] args) throws FileNotFoundException {
            generator = new Random();

            Scanner s = null;

            try {
                s = new Scanner(new BufferedReader(new FileReader("name.txt")));
                s.useLocale(Locale.US);
                s.useDelimiter(",");

                while (s.hasNext()) {
                    names.add(s.next());
                }

            } finally {
                // TODO: handle exception
                s.close();
            }

            Place p = new Place(); 
            Character.createCharacter();
            p.step();
            p.step();
            p.step();
            p.step();
            p.step();
            p.step();
            p.step();

            System.out.println(p.residents.get(1).thoughts.toString());

            for (int i = 0; i < 5; i++) {
                Boolean alive = true;

                System.out.println(">>>>>>>>>>>>>>>>>" + p.name + " " + p.startingNumber);

                for (NPC n : p.residents) {

                    System.out.println(n.name + " " + n.enneagram[0]);
                    System.out.println(n.name + " is " + n.happiness + " happy ");
                    System.out.println(n.name + " is " + n.boredom + " bored ");
                    if (n.boredom >= 100) {
                        alive = false;
                        System.out.println(n.name + " is now dead ");
                    }

                    n.eat();
                }

            }

        }
    }
}
