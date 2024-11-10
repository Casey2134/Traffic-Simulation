abstract class RandomDistribution {
    abstract double sample();
}

class Exponential extends RandomDistribution {
    private final double lambda;

    Exponential(double lambda) {
        this.lambda = lambda;
    }

    @Override
    double sample() {
        return -Math.log(1 - Math.random()) / lambda;
    }
}

class Normal extends RandomDistribution {
    private final double mean;
    private final double stdDev;

    Normal(double mean, double stdDev) {
        this.mean = mean;
        this.stdDev = stdDev;
    }

    @Override
    double sample() {
        // Generate thirty uniform(0,1) random numbers, summing them as we go;
        double sum = 0.0;
        for (int i = 0; i < 30; i++) {
            sum += Math.random();
        }

        // Form the expression above, subtracting off the n/2 and then dividing by the expression shown.
        double number = (sum - 15) / Math.sqrt(30.0 / 12.0);

        // Multiply by sigma and add mu.
        return number * stdDev + mean;
    }
}