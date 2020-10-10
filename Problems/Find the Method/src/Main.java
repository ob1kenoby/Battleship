import java.lang.reflect.Method;

class MethodFinder {

    public static String findMethod(String methodName, String[] classNames) throws ClassNotFoundException {
        for (String className : classNames) {
            Class c = Class.forName(className);
            Method[] methods = c.getMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    System.out.println(c.getName());
                    break;
                }
            }
        }
        return "";
    }
}