import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Modifier;

@Retention(RetentionPolicy.RUNTIME)
@interface RunCount {
    int value();
}

class DemonstrationClass {

    public void publicMethod1() {
        System.out.println("publicMethod1 вызван");
    }

    public void publicMethod2() {
        System.out.println("publicMethod2 вызван");
    }

    @RunCount(1)
    public void publicMethod3() {
        System.out.println("publicMethod3 вызван");
    }

    @RunCount(2)
    protected void protectedMethod1() {
        System.out.println("protectedMethod1 вызван");
    }

    protected void protectedMethod2() {
        System.out.println("protectedMethod2 вызван");
    }

    protected void protectedMethod3() {
        System.out.println("protectedMethod3 вызван");
    }

    private void privateMethod1() {
        System.out.println("privateMethod1 вызван");
    }

    @RunCount(3)
    private void privateMethod2() {
        System.out.println("privateMethod2 вызван");
    }

    @RunCount(4)
    private void privateMethod3(int x){
        System.out.println("privateMethod3 вызван");
    }
}

public class Main {

    private static Object getDefaultValue(Class<?> type) {
        if (type.isPrimitive()) {
            if (type == boolean.class) return false;
            if (type == char.class) return '\u0000';
            if (type == byte.class) return (byte) 0;
            if (type == short.class) return (short) 0;
            if (type == int.class) return 0;
            if (type == long.class) return 0L;
            if (type == float.class) return 0.0f;
            if (type == double.class) return 0.0;
        }
        return null;
    }

    public static void main(String[] args) {
        DemonstrationClass myClass = new DemonstrationClass();

        Method[] methods = DemonstrationClass.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(RunCount.class)) {
                RunCount annotation = method.getAnnotation(RunCount.class);
                int count = annotation.value();

                method.setAccessible(true);

                Parameter[] parameters = method.getParameters();
                Object[] parameterValues = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    parameterValues[i] = getDefaultValue(parameters[i].getType());
                }

                if (Modifier.isPrivate(method.getModifiers()) || Modifier.isProtected(method.getModifiers())){
                    for (int i = 0; i < count; i++) {
                        try {
                            method.invoke(myClass, parameterValues);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}