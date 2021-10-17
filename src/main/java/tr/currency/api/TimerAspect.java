package tr.currency.api;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimerAspect {

    @Around("execution(* *(..)) && within(tr.currency.api..*) && !within(TimerAspect)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            System.out.println(joinPoint + " -> " + (System.nanoTime() - startTime) / 1000000 + " ms");
        }
    }
}
