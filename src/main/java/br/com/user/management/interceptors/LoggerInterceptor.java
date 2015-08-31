package br.com.user.management.interceptors;

import br.com.user.management.exceptions.UserManagementException;
import br.com.user.management.util.Timer;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Interceptor
@Log
public class LoggerInterceptor {

    @AroundInvoke
    public Object log(InvocationContext invocationContext) throws Exception {
        Map<String, Object> mapLog = new HashMap<>();
        final Method method = invocationContext.getMethod();
        Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());

        mapLog.put("method", method.getName());
        mapLog.put("class", beanClass(invocationContext.getTarget()));

        Map<String, String> parameters = new HashMap<>();

        for(int i = 0; i< invocationContext.getParameters().length; i++){
            final String paramName = method.getParameters()[i].getName();
            final Object paramValue = invocationContext.getParameters()[i];
            parameters.put(paramName, paramValue == null ? null : paramValue.toString());
        }

        mapLog.put("parameters", parameters.toString());
        mapLog.put("when", "before");

        logger.info(mapLog.toString());

        Timer timer = new Timer();
        timer.start();

        try {
            final Object result = invocationContext.proceed();
            timer.stop();

            mapLog.put("result", result == null ? null : result.toString());
            mapLog.put("when", "after");
            mapLog.put("ms", timer.totalInMillis());

            logger.info(mapLog.toString());

            return result;
        } catch (UserManagementException e) {
            timer.stop();

            mapLog.put("exception", e.getClass().getSimpleName());
            mapLog.put("exceptionMessage", e.getMessage());

            if(e.getCause() != null){
                mapLog.put("cause", e.getCause().getClass().getSimpleName());
                mapLog.put("cause", e.getCause().getMessage());
            }

            mapLog.put("when", "onException");
            mapLog.put("ms", timer.totalInMillis());

            logger.warn(mapLog.toString(), e);

            throw e;
        } catch (Exception e) {
            timer.stop();

            mapLog.put("exception", e.getClass().getSimpleName());
            mapLog.put("exceptionMessage", e.getMessage());

            if(e.getCause() != null){
                mapLog.put("cause", e.getCause().getClass().getSimpleName());
                mapLog.put("cause", e.getCause().getMessage());
            }

            mapLog.put("when", "onException");
            mapLog.put("ms", timer.totalInMillis());

            logger.error(mapLog.toString(), e);

            throw e;
        }
    }

    private Class beanClass(Object o){
        if(o instanceof ProxyObject){
            return o.getClass().getSuperclass();
        }

        return o.getClass();
    }
}
