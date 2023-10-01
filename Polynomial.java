import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.io.PrintStream;
import java.text.DecimalFormat;

class Polynomial {
    double[] coefficients;
    int[] exponents;

    Polynomial() {
        coefficients = new double[]{0};
        exponents = new int[]{0};
    }

    Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
        }

        this.exponents = new int[exponents.length];
        for (int i = 0; i < exponents.length; i++) {
            this.exponents[i] = exponents[i];
        }

    }

    Polynomial(File file) throws Exception {
        coefficients = new double[0];
        exponents = new int[0];

        BufferedReader b = new BufferedReader(new FileReader(file));        
        String data = b.readLine();
        String[] splitData = data.split("(\\+)|((?=\\-))");
        System.out.println(Arrays.toString(splitData));

        for (int i = 0; i < splitData.length; i++) {

            String term = splitData[i];
            int varStartInd = splitData[i].indexOf("x");
            int exponentStartInd = varStartInd+1;

            if (varStartInd == -1) {
                coefficients = appendArray(coefficients, Double.parseDouble(term));
                exponents = appendArray(exponents, 0);
            }
            else if (exponentStartInd == term.length()) {
                coefficients = appendArray(coefficients, Double.parseDouble(term.substring(0, varStartInd)));
                exponents = appendArray(exponents, 1);
            }
            else {
                coefficients = appendArray(coefficients, Double.parseDouble(term.substring(0, varStartInd)));
                exponents = appendArray(exponents, Integer.parseInt(term.substring(exponentStartInd)));
            }
        }
        b.close();
        System.out.println(Arrays.toString(coefficients));
        System.out.println(Arrays.toString(exponents));

    }


    int findIndex(int[] arr, int toFind) {
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == toFind) {
                return i;
            }
        }
        return -1;
    }   

    int[] appendArray(int[] arr, int newElement) {
        int[] newArr = new int[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        newArr[newArr.length-1] = newElement;

        return newArr;
    }   

    double[] appendArray(double[] arr, double newElement) {
        double[] newArr = new double[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        newArr[newArr.length-1] = newElement;

        return newArr;
    }   

    void saveToFile(String fileName) throws Exception{
        PrintStream ps = new PrintStream(fileName + ".txt");

        String polyString = "";

        for (int i = 0; i < coefficients.length; i++) {

            if (i != 0 && coefficients[i] > 0) {
                polyString += "+";
            }

            String coeffString = Double.toString(coefficients[i]);
            int ind = coeffString.length() - 1;
            while (coeffString.charAt(ind) == '0' || coeffString.charAt(ind) == '.') {
                ind--;
            }
            polyString += coeffString.substring(0, ind+1);
            if (exponents[i] != 0) {
                polyString += "x";
                if (exponents[i] > 1) {
                    polyString += Integer.toString(exponents[i]);
                }
            }
        }

        ps.println(polyString);
        ps.close();
    }

    Polynomial add(Polynomial poly2) {

        double[] newCoefficients = new double[coefficients.length];
        System.arraycopy(coefficients, 0, newCoefficients, 0, coefficients.length);

        int[] newExponents = new int[exponents.length];
        System.arraycopy(exponents, 0, newExponents, 0, exponents.length);

        for (int i = 0; i < poly2.exponents.length; i++) {

            //check if exponent exists in our newExponent array. If it does, we can just add the coefficients, 
            //if not, we add a new extry to our arrays
            int ind = findIndex(newExponents, poly2.exponents[i]);
            if (ind != -1) {
                newCoefficients[ind] += poly2.coefficients[i];
            }
            else {
                newCoefficients = appendArray(newCoefficients, poly2.coefficients[i]);
                newExponents = appendArray(newExponents, poly2.exponents[i]);

            }
        }

        Polynomial newPoly = new Polynomial(newCoefficients, newExponents);
        return newPoly;
    }

    Polynomial multiply(Polynomial poly2) {

        Polynomial newPoly = new Polynomial();

        for (int i = 0; i < poly2.coefficients.length; i++) {
            
            double[] tempCoef = new double[coefficients.length];
            int[] tempExp = new int[exponents.length];

            for (int j = 0; j < coefficients.length; j++) {
                tempCoef[j] = coefficients[j] * poly2.coefficients[i];
                tempExp[j] = exponents[j] + poly2.exponents[i];
            }
            newPoly = newPoly.add(new Polynomial(tempCoef, tempExp));
        }
        
        return newPoly;
    }
    double evaluate(double input) {
        double output = 0;

        for (int i = 0; i < exponents.length; i++) {
            double variable = 1;
            for (int j = 0; j < exponents[i]; j++) {
                variable *= input;
            }
            output += coefficients[i] * variable;
        }
        return output;
    }
    
    boolean hasRoot(double potentialRoot) {

        if (evaluate(potentialRoot) == 0) {
            return true;
        }

        return false;
    }
}
