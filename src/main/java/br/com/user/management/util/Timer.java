package br.com.user.management.util;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class Timer {
    private long init;
    private long end;

    public void start(){
        init = System.currentTimeMillis();
    }

    public void stop(){
        end = System.currentTimeMillis();
    }

    public long totalInMillis(){
        return end - init;
    }
}
