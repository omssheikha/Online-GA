/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg525.ga1;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Omar
 */
public class Genetic
{

    private static final int POPULATION_SIZE = 10;
    private static final int NUMBER_OF_REPETITIONS = 10000;
    private static final double CHANCE_OF_MUTATION = 0.13;
    private static final int CONVERGENCE_LIMIT = 10;

    private Choices validChoices;
    private ArrayList<Task> tasks;
    private Chromosome[] population = new Chromosome[POPULATION_SIZE];
    private int totalExecutionTime = 0;
    
    public Genetic(ArrayList<Task> tasks)
    {
        this.tasks = tasks;
        validChoices = new Choices(tasks);
        for (Task t : tasks)
        {
            totalExecutionTime += t.getExecutionTime();
        }
    }
    
    public Chromosome getBestDegredationChromosome()
    {
        double max = 0;
        int maxpos = -1;
        for (int x = 0; x< population.length; x++)
        {
            if(population[x].getFitness() > max)
            {
                max= population[x].getFitness();
                maxpos = x;
            }        
        }
        return population[maxpos];
    }
    
    public void StartGA()
    {
        initializePopulation();
        int generation = 0;
        int convergence = 0;
        double prevAvg = 0;
        while (generation < NUMBER_OF_REPETITIONS && convergence < CONVERGENCE_LIMIT)
        {
            Breed();
            mutate();
            generation++;
            double avg = getAverageFitness();
            if(getPercentageDifference(avg, prevAvg) <= 5)
                convergence++;
            else
                convergence = 0;
            prevAvg = avg;
        }
    }
    
     private double getPercentageDifference(double v1, double v2)
    {
        return Math.abs((v1 - v2) / ((v1 + v2) / 2)) * 100;
    }

    private void initializePopulation()
    {
        for (int i = 0; i < population.length; i++)
        {
            population[i] = new Chromosome(validChoices);
            population[i].setFitness(calculateFitness(i));
        }
    }

    private double calculateFitness(int i)
    {
        double bestdgredation = 0;
        while(calcSubFitness(bestdgredation, i) < totalExecutionTime)
        {
            bestdgredation += 0.1;
        }
        return 1/bestdgredation;
    }
    
    private double getAverageFitness()
    {
        double acc = 0;
        for (Chromosome c: population)
        {
            acc += c.getFitness();
        }
        return acc/population.length;
    }
    
    private double calcSubFitness(double degredation, int i)
    {
          double[] frequency = new double[tasks.size()];
        for (int j = 0; j < population[i].getChromosome().length; j++)
        {
            int x = population[i].getGene(j);
            if (x != -1)
            {
                if (frequency[x] != tasks.get(x).getExecutionTime())
                {
                    frequency[x] += 1 * degredation;
                }
            }
        }
        int total = 0;
        for (int j = 0; j < frequency.length; j++)
        {
            total += (int) frequency[j];
        }

        return total;
    }

    private void Breed()
    {
        int[] parents = selectParents();
        mate(parents);
    }

    private int[] selectParents()
    {
        int[] parents = new int[2];
        int[] chosen = new int[4];
        Random r = new Random();

        for (int j = 0; j < 4; j++)
        {
            chosen[j] = r.nextInt(population.length);
        }

        if (population[chosen[0]].getFitness() > population[chosen[1]].getFitness())
        {
            parents[0] = chosen[0];
        } else
        {
            parents[0] = chosen[1];
        }

        if (population[chosen[2]].getFitness() > population[chosen[3]].getFitness())
        {
            parents[1] = chosen[2];
        } else
        {
            parents[1] = chosen[3];
        }

        return parents;
    }

    private void mate(int[] parents)
    {
        Random r = new Random();
        int temp = r.nextInt(validChoices.getMaxdeadline());
        Chromosome child1 = new Chromosome(population[parents[0]], population[parents[1]], temp);
        Chromosome child2 = new Chromosome(population[parents[1]], population[parents[0]], temp);

        int minpos = getminimum(-1);
        int minpos2 = getminimum(minpos);

        population[minpos] = child1;
        population[minpos2] = child2;
        population[minpos].setFitness(calculateFitness(minpos));
        population[minpos2].setFitness(calculateFitness(minpos2));
    }

    private int getminimum(int i)
    {
        int minimumpos = -1;
        double minimum = Integer.MAX_VALUE;
        for (int x = 0; x < population.length; x++)
        {
            if (x != i && population[x].getFitness() < minimum)
            {
                minimumpos = x;
                minimum = population[x].getFitness();
            }
        }
        return minimumpos;
    }

    private void mutate()
    {
        Random r = new Random();
        double t = r.nextDouble();

        if (t < CHANCE_OF_MUTATION)
        {
            int m = getChromosomeToMutate();
            population[m].mutate(validChoices);
            population[m].setFitness(calculateFitness(m));
        }

    }

    private int getChromosomeToMutate()
    {
        int chosen = -1;
        int accumlate = 0;
        for (Chromosome population1 : population)
        {
            accumlate += population1.getFitness();
        }

        Random r = new Random();
        int rand = r.nextInt(accumlate);

        int cum_fitness = 0;
        for (int i = 0; i < population.length; i++)
        {
            cum_fitness += population[i].getFitness();
            if (cum_fitness > rand)
            {
                chosen = i;
                break;
            }
        }
        return chosen;
    }

}
