import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Scanner;

public class Program {
    private final static String myDelimiter = "-------------\n";
    private static Class<?> useClass;
    private static final String[] classNames = {"classes.Car", "classes.User"};
    private static Object newObject;

    public static void main(String[] args) {
        Class<?>[] classes = addClasses();

        System.out.println("Classes:");
        for (Class<?> c : classes) System.out.println(c.getName().split("\\.")[1]);
        System.out.println(myDelimiter + "Enter class name:");

        Scanner in = new Scanner(System.in);
        if (!checkClass(in.next(), classes)) {
            System.out.println("Error! There is no such class");
            System.exit(-1);
        }

        getFieldsAndMethods();
        createObj(in);
        changeField(in);
        callMethod(in);
        in.close();
    }

    private static void callMethod(Scanner in) {
        System.out.println(myDelimiter + "Enter name of the method for call:");
        Scanner scanner = new Scanner(System.in);
        Method method = chooseMethod(scanner);
        if (method == null) throw new RuntimeException("Invalid method");
        Parameter[] parameters = method.getParameters();
        Object[] arguments = new Object[parameters.length];
        for (int i = 0; i < parameters.length; ++i) {
            System.out.println("Enter " + parameters[i].getType().getSimpleName() + " value");
            arguments[i] = parseArgs(scanner, null, parameters[i]);
        }
        try {
            Object result = method.invoke(newObject, arguments);
            if (result != null) System.out.println("Method returned:\n" + result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Дура тупая");
        }
    }

    private static Method chooseMethod(Scanner scanner) {
        Method[] methods = useClass.getDeclaredMethods();
        System.out.println(Arrays.toString(methods));
        StringBuilder tmp = new StringBuilder(scanner.nextLine());
        System.out.println(tmp);
        String tmpNameMethod = tmp.toString().split("\\(")[0];
        tmp.delete(0, tmpNameMethod.length() + 1).delete(tmp.length() - 1, tmp.length());
        System.out.println(tmp);
        String[] tmpTypes = tmp.toString().split(", ");
        System.out.println(tmpTypes.length);
        for (Method method : methods)
            if ((method.getParameterCount() == tmpTypes.length || method.getParameterCount() == 0)
                    && method.getName().equals(tmpNameMethod))
                return method;
        return null;
    }

    private static void changeField(Scanner in) {
        System.out.println(myDelimiter + "Enter name of the field for changing:");
        String changingField = in.next();
        try {
            Field field = useClass.getDeclaredField(changingField);
            field.setAccessible(true);
            System.out.println("Enter " + field.getType().getSimpleName() + " value:");
            field.set(newObject, parseArgs(in, field, null));
            System.out.println("Object updated: " + newObject);
        } catch (NoSuchFieldException e) {
            System.err.println("Error! There is no such field");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?>[] addClasses() {
        Class<?>[] classes = new Class[classNames.length];
        try {
            for (int i = 0; i < classNames.length; ++i) classes[i] = Class.forName(classNames[i]);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return classes;
    }


    private static void createObj(Scanner in) {
        System.out.println(myDelimiter + "Let's create an object.");
        Constructor<?> constructor = neededConstructor();
        if (constructor == null) return;
        Field[] fields = useClass.getDeclaredFields();
        Object[] objects = new Object[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            System.out.println(fields[i].getName() + ":");
            objects[i] = parseArgs(in, fields[i], null);
        }
        try {
            newObject = constructor.newInstance(objects);
            System.out.println("Object created: " + newObject);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseArgs(Scanner scanner, Field field, Parameter parameter) {
        return switch (field != null ? field.getType().getSimpleName() : parameter.getType().getSimpleName()) {
            case "int" -> scanner.nextInt();
            case "String" -> scanner.next();
            case "Long" -> scanner.hasNextLong() ? scanner.nextLong() : 0L;
            case "double" -> scanner.hasNextDouble() ? scanner.nextDouble() : 0.0;
            case "boolean" -> scanner.hasNextBoolean() && scanner.nextBoolean();
            default -> null;
        };
    }

    private static Constructor<?> neededConstructor() {
        Constructor<?>[] constructors = useClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() > 0) return constructor;
        }
        return null;
    }

    private static boolean checkClass(String next, Class<?>[] classes) {
        for (int i = 0; i < classNames.length; ++i) {
            if (next.equals(classNames[i].split("\\.")[1])) {
                useClass = classes[i];
                return true;
            }
        }
        return false;
    }

    private static void getFieldsAndMethods() {
        System.out.println(myDelimiter + "fields:");
        Field[] fields = useClass.getDeclaredFields();
        Method[] methods = useClass.getDeclaredMethods();
        String tmpType;
        for (Field field : fields) {
            tmpType = field.getType().getSimpleName();
            System.out.printf("\t%s %s\n", tmpType, field.getName());
        }

        System.out.println("methods:");
        for (Method method : methods) {
            tmpType = method.getReturnType().getSimpleName();
            Parameter[] parameters = method.getParameters();
            StringBuilder tmpParameters = new StringBuilder();
            for (int i = 0; i < parameters.length; ++i) {
                tmpParameters.append(parameters[i].getType().getSimpleName());
                if (i != parameters.length - 1) tmpParameters.append(", ");
            }
            System.out.printf("\t%s %s(%s)\n", tmpType, method.getName(), tmpParameters);
        }
    }
}
