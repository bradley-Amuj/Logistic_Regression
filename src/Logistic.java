import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logistic {


    private double learningRate;
    private int ITERATIONS;
    private double weights[];

    public Logistic(int weights,int ITERATIONS) {

        learningRate = 0.0001;
        this.weights = new double[weights];
        this.ITERATIONS = ITERATIONS;

    }

    private static double sigmoid(double x){ return 1/(1+(Math.exp(-x)));}

    public void train(List<Instance> instances){

        for(int n = 0;n<ITERATIONS;n++){

            double like = 0.0;

            for(int j= 0;j<instances.size();j++){

                int x[] = instances.get(j).x;
                int label = instances.get(j).label;
                double predicted = classify(x);

                for(int k = 0;k<weights.length;k++){

                    weights[k] = weights[k]+learningRate*(label-predicted)*x[k];
                }

            }
        }

    }




    private double classify(int x[])    {
        double totalGuessesBeforeSigmoid = .0;

        for(int i=0;i<weights.length;i++){
            totalGuessesBeforeSigmoid = weights[i]*x[i];
        }


        return sigmoid(totalGuessesBeforeSigmoid);
    }


    public static class Instance {
        public int label;
        public int[] x;


        public Instance(int label, int[] x) {
            this.label = label;
            this.x = x;
        }
    }
        public static List<Instance>readDataSet(String file) throws FileNotFoundException{
        List<Instance> dataset = new ArrayList<Instance>();

        Scanner scanner = null;


        try{

            scanner = new Scanner(new File(file));

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.startsWith("#")){

                    continue;
                }
                String columns[] = line.split("\\s+");

                //Skipping the first and last columns
                int i = 1;

                int data [] = new int[columns.length-2];

                for( i=1;i<columns.length-1;i++){
                    data[i-1] = Integer.parseInt(columns[i]);
                }
                int label  = Integer.parseInt(columns[i]);

                Instance instance = new Instance(label,data);

                dataset.add(instance);

            }
        } finally {
            if(scanner!=null){

                scanner.close();
            }
        }

        return dataset;
    }


    public static void main(String[] args) throws FileNotFoundException {

        List<Instance> instances = readDataSet("/Users/user/Downloads/LogisticRegression/src/data.txt");

        Logistic logisticModel = new Logistic(5,10000);

        logisticModel.train(instances);

        int x [] = {1,0,1,1,0};

        System.out.println("The model predicted "+ logisticModel.classify(x));

        System.out.println("This is a cow");

    }




}
