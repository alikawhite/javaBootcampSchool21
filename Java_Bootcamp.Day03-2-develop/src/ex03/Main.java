package ex03;import java.io.*;import java.util.*;public class Main {    private static final List<Resources> resources = new ArrayList<>();    public static void main(String[] args) throws IOException {        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {            System.err.println("Error!!");            System.exit(-1);        }        int threadsCount = Integer.parseInt(args[0].substring("--threadsCount=".length()));        if (threadsCount < 1) {            System.out.println("Invalid threadsCount");            System.exit(-1);        }        try (Scanner in = new Scanner(new FileInputStream("src/ex03/files_urls.txt"))) {            int j = 0;            while (in.hasNext()) resources.add(new Resources(in.next(), false, j = j + 1));            if (resources.size() < threadsCount) {                System.out.println("Invalid threadsCount");                System.exit(-1);            }            Thread[] threads = new Thread[threadsCount];            for (int i = 0; i < threadsCount; ++i) threads[i] = new Thread(new Download(resources, i + 1));            for (Thread thread : threads) thread.start();        } catch (FileNotFoundException e) {            e.printStackTrace();        }    }}