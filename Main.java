import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

public class Main{

    static Random generator;

    static ArrayList<String> names = new ArrayList<String>();

    static String[] baseServices = {
                                        "food",
                                        "water",
                                        "fuel",
                                        "data center",
                                        "passes"
                                    };

    static int[][] enneagramCombos = {
                                        {1,4,7},
                                        {1,7,4},
                                        {2,4,8},
                                        {2,8,4},
                                        {3,6,9},
                                        {3,9,6},
                                        {4,1,2},
                                        {4,2,1},
                                        {5,7,8},
                                        {5,8,7},
                                        {6,9,3},
                                        {6,3,9},
                                        {7,1,5},
                                        {7,5,1},
                                        {8,2,5},
                                        {8,5,2},
                                        {9,3,6},
                                        {9,6,3},
                                    };

    static int[][] enneagramCompat = {
                                        {1,2,9},
                                        {2,4,8},
                                        {3,1,9},
                                        {4,2,9},
                                        {5,1},
                                        {6,8,9,2},
                                        {7,5,1},
                                        {8,2,9},
                                        {9,4,6}
                                    };

    static int[][] enneagramNonCompat = {
                                            {7},
                                            {},
                                            {},
                                            {8},
                                            {9},
                                            {},
                                            {},
                                            {4},
                                            {5}
                                        };


    static <T> T pickRandom(T[] array){
        int rnd = generator.nextInt(array.length);
        return array[rnd];
    }

    static <T> T pickRandomAL(ArrayList<T> array){
        int rnd = generator.nextInt(array.size());
        return array.get(rnd);
    }

    static class Place{
        public int est = 0;
        public String name = "example";
        public int value = 0;
        int startingNumber = 0;

        ArrayList<NPC> residents;
        ArrayList<String> services;

        String[] neighbors;
        NPC[] popular;
        NPC[] unpopular;
        

        public Place(String iName, int iStartingNumber){
            name = iName;
            startingNumber = iStartingNumber;

            residents = addRandomCharacters(startingNumber);
            getServices();
            //TODO
        }

        public Place(String iName){
            name = iName;
            startingNumber = generator.nextInt(100);
            residents = addRandomCharacters(startingNumber);
            //TODO
        }

        public Place(){
            name = (generator.nextFloat()<.5) ?  pickRandomAL(names) + "'s " + pickRandomAL(names) : pickRandomAL(names)+"ton";
            startingNumber = generator.nextInt(100);
            residents = addRandomCharacters(startingNumber);
            //TODO


        }

        public int increaseAge(){
            return est++;
        }

        public ArrayList<NPC> addRandomCharacters(int iStartingNumber){

            ArrayList<NPC> temp = new ArrayList<NPC>();


            for(int i =0; i<iStartingNumber; i++){
                temp.add(new NPC());
            }
            
            //TODO
            return temp;
        }

        public void getServices(){

            if(services.isEmpty()){
                int rnd = generator.nextInt(10);

                value = rnd*1000;
                for(int i =0; i< rnd; i++){
                    services.add(pickRandom(baseServices));
                }
                
            }
        }

    }
    
    interface interactable {
        void interact();
    }

    interface walkable{
        void walk();
    }

    static class NPC implements interactable, walkable{

        String name = "examplePerson";
        int age = 0;
        int[] enneagram = {1,4,7};
        int happiness = 100;
        int boredom = 50;
        int hunger = 0;



        String[] memories;
        String[] thoughts;
        String[] knowledge;
        NPC[] friends;
        NPC[] enemies;
        String icon = "";


        String home = "This Base";
        String[] travels = {"This Base"};

        public NPC(){
            name = pickRandomAL(names);
            enneagram = pickRandom(enneagramCombos);
            age = generator.nextInt(90) + 10; 
            //TODO
        }

        public void cleanup(){
            //TODO
        }

        @Override
        public void interact() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'interact'");
        }

        @Override
        public void walk() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'walk'");
        }
    
    }
    
    static class Character implements interactable, walkable{
        @Override
        public void interact(){
            throw new UnsupportedOperationException("Unimplemented method 'Interact'");
        }

        @Override
        public void walk() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'walk'");
        }
    }
    public static void main(String[] args) throws FileNotFoundException{
        generator = new Random();

        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("name.txt")));
            s.useLocale(Locale.US);
            s.useDelimiter(",\\s");

            while(s.hasNext()){
                names.add(s.next());
            }

        } finally {
            // TODO: handle exception
            s.close();
        }
        for(int i =0; i<5; i++){

            

            Place p = new Place();


            System.out.println(">>>>>>>>>>>>>>>>>" + p.name + " "+ p.startingNumber);


            for(NPC n: p.residents){

                System.out.println(n.name+" "+n.enneagram[0]);
            }
        }


    }
}
