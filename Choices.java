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
class Choices
{
    ArrayList<ArrayList<Integer>> choices;
    int maxdeadline;

    public int getMaxdeadline()
    {
        return maxdeadline;
    }
    
    
    Choices(ArrayList<Task> tasks)
    {
        int maxDeadline = 0;
        for(Task t: tasks)
        {
            if(t.getDeadline() > maxDeadline)
                maxDeadline = t.getDeadline();
        }
        
        choices = new ArrayList<>();
        for(int i = 0; i < maxDeadline; i++)
        {
            choices.add(getChoicesForThisTime(tasks));
        }
    }

    int getRandomChoice(int i)
    {
        Random r = new Random();
        return choices.get(i).get(r.nextInt(choices.get(i).size()));
    }

    private ArrayList<Integer> getChoicesForThisTime(ArrayList<Task> tasks)
    {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(-1);
        for(int i = 0; i < tasks.size(); i++)
        {
            if(tasks.get(i).isValidInThisTime(i))
            {
                temp.add(i);
            }
        }    
        return temp;
    }
    
}
