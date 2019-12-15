/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg525.ga1;

/**
 *
 * @author Omar
 */
public class Task
{
    private int startTime;
    private int deadline;
    private int executionTime;
    private boolean isCritical;

    public Task(int startTime, int deadline, int executionTime, boolean isCritical)
    {
        this.startTime = startTime;
        this.deadline = deadline;
        this.executionTime = executionTime;
        this.isCritical = isCritical;
    }
        
    public int getStartTime()
    {
        return startTime;
    }

    public void setStartTime(int startTime)
    {
        this.startTime = startTime;
    }

    public int getDeadline()
    {
        return deadline;
    }

    public void setDeadline(int deadline)
    {
        this.deadline = deadline;
    }

    public int getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(int executionTime)
    {
        this.executionTime = executionTime;
    }

    public boolean isIsCritical()
    {
        return isCritical;
    }

    public void setIsCritical(boolean isCritical)
    {
        this.isCritical = isCritical;
    }

    public boolean isValidInThisTime(int time)
    {
        return time >= startTime && time <= startTime+executionTime;
    }

}
