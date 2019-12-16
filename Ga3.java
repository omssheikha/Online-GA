/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg525.ga1;

import java.util.ArrayList;

/**
 *
 * @author Omar
 */
public class Ga3
{

    static ArrayList<Task> tasks;
    static ArrayList<Task> temptask;
    static ArrayList<Task> augmentedTasks;
    static int[] finshedChromosome;
    static int[] finished;

    public static void main(String[] args)
    {
        fillArrylist();
        sortTasks();
        int maxDeadline = getMaxDeadline();
        finshedChromosome = new int[maxDeadline];
        finished = new int[tasks.size()];
        int taskCounter = 0;
        int currentTime = 0;

        while (currentTime < maxDeadline)
        {
            augmentedTasks.clear();
            while (true)
            {
                if (tasks.get(taskCounter).getStartTime() == taskCounter)
                {
                    temptask.add(tasks.get(taskCounter));
                } else
                {
                    break;
                }
                taskCounter++;
            }
            for (int i = 0; i < temptask.size(); i++)
            {
                Task temp = temptask.get(i);
                if (temp.getStartTime() < currentTime)
                {
                    temp.setStartTime(0);
                } else
                {
                    temp.setStartTime(temp.getStartTime() - currentTime);
                }

                if (finished[i] > temp.getExecutionTime())
                {
                    temp.setExecutionTime(0);
                } else
                {
                    temp.setExecutionTime(temp.getExecutionTime() - finished[i]);
                }
            }
            Genetic g = new Genetic(augmentedTasks);
            g.StartGA();
            Chromosome temp = g.getBestDegredationChromosome();
            int temp2 = 0;
            
            while(true)
            {
                if(currentTime == tasks.get(taskCounter+1).getStartTime())
                    break;
                finshedChromosome[temp2+currentTime] = temp.getGene(temp2);
            }
            int[] finshedTemp = new int[finished.length];
            for (int i = 0; i < currentTime; i++)
            {
                if(finshedChromosome[i] == -1)
                    continue;
                if(finshedTemp[finshedChromosome[i]] == tasks.get(i).getExecutionTime())
                    continue;
                finshedTemp[finshedChromosome[i]]++;
            }
        }
        System.out.println("the best degredation is " + calculateFitness());
    }

    private static void fillArrylist()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void sortTasks()
    {
        int n = tasks.size();

        // One by one move boundary of unsorted subarray 
        for (int i = 0; i < n - 1; i++)
        {
            // Find the minimum element in unsorted array 
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
            {
                if (tasks.get(j).getStartTime() < tasks.get(min_idx).getStartTime())
                {
                    min_idx = j;
                }
            }

            // Swap the found minimum element with the first 
            // element 
            Task temp = tasks.get(min_idx);
            tasks.set(min_idx, tasks.get(i));
            tasks.set(i, temp);
        }
    }

    private static int getMaxDeadline()
    {
        int maxDeadline = 0;
        for (Task t : tasks)
        {
            if (t.getDeadline() > maxDeadline)
            {
                maxDeadline = t.getDeadline();
            }
        }
        return maxDeadline;
    }
    
    private static double calculateFitness()
    {
        double totalExecutionTime = 0;
        for(Task t: tasks)
        {
            totalExecutionTime+= t.getExecutionTime();
        }
        double bestdgredation = 0;
        while(calcSubFitness(bestdgredation) < totalExecutionTime)
        {
            bestdgredation += 0.1;
        }
        return 1/bestdgredation;
    }

    private static double calcSubFitness(double degredation)
    {
          double[] frequency = new double[tasks.size()];
        for (int j = 0; j < finshedChromosome.length; j++)
        {
            int x = finshedChromosome[j];
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
}
