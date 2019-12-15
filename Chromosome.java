/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg525.ga1;

import java.util.Random;

/**
 *
 * @author Omar
 */
public class Chromosome
{
    private int[] chromosome;
    private Choices choices;
    private double fitness;
    
    public Chromosome(Choices validchoices)
    {
        chromosome = new int[validchoices.getMaxdeadline()];
        choices = validchoices;
        RandmoizeChromosome();
    }
    
    public int[] getChromosome()
    {
        return chromosome;
    }
    
    public int getGene(int i)
    {
        return chromosome[i];
    }

    public double getFitness()
    {
        return fitness;
    }

    public void setFitness(double fitness)
    {
        this.fitness = fitness;
    }
    
    private void RandmoizeChromosome()
    {
        for(int i = 0; i < chromosome.length; i++)
        {
            chromosome[i] = choices.getRandomChoice(i);
        }
    }
    
    public Chromosome(Chromosome mother, Chromosome father, int point)
    {
        chromosome = new int[mother.getChromosome().length];
        System.arraycopy(mother.getChromosome(), 0, chromosome, 0, point);
        System.arraycopy(father.getChromosome(), point, chromosome, point, chromosome.length - point);
    }

    void mutate(Choices validChoices)
    {
        Random r = new Random();
        int c = r.nextInt(chromosome.length);
        chromosome[c] = choices.getRandomChoice(c);
    }

    
}
