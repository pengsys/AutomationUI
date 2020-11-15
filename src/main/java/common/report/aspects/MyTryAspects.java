package common.report.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/11/15 16:53
 */
@Slf4j
@Aspect
public class MyTryAspects {

    @Pointcut("@annotation(common.report.mytry)")
    public void withMyTryAnnotation() {
        //pointcut body, should be empty
    }

    @Pointcut("execution（）**（..）")
    public void anyMethod() {
        //pointcut body, should be empty
    }

    @Pointcut("anyMethod() && withMyTryAnnotation()")
    public Object test(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        for(int i=0; i<3; i++) {
            try{
                result = point.proceed();
                return result;
            }catch (Throwable throwable) {
                if(i==2) {
                    throw throwable;
                }
            }
        }
        return result;
    }

}
