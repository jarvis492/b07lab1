class Polynomial {
    double[] coefficients;

    Polynomial() {
        coefficients = new double[]{0};
    }

    Polynomial(double[] coefficients) {
        this.coefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
        }
    }

    Polynomial add(Polynomial poly2) {

        double[] newCoefficients;

        if(coefficients.length > poly2.coefficients.length) {
            newCoefficients = new double[coefficients.length];
            System.arraycopy(coefficients, 0, newCoefficients, 0, coefficients.length);

            for (int i = 0; i < poly2.coefficients.length; i++) {
                newCoefficients[i] += poly2.coefficients[i];
            }
        }
        else {
            newCoefficients = new double[poly2.coefficients.length];
            System.arraycopy(poly2.coefficients, 0, newCoefficients, 0, poly2.coefficients.length);
            
            for (int i = 0; i < coefficients.length; i++) {
                newCoefficients[i] += coefficients[i];
            }
        }

        Polynomial newPoly = new Polynomial(newCoefficients);
        return newPoly;
    }

    double evaluate(double input) {
        double output = 0;

        for (int i = 0; i < coefficients.length; i++) {
            double variable = 1;
            for (int j = 0; j < i; j++) {
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
