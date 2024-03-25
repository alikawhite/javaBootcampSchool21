package ex00;class Main {    public static void main(String[] args) {        if (args.length != 1 || !args[0].startsWith("--count=")) {            System.err.println("Error!!");            System.exit(-1);        }        try {            int numOfCount = Integer.parseInt(args[0].substring("--count=".length()));            if (numOfCount < 1) {                System.err.println("Error!! Value less 1");                System.exit(-1);            }            Thread egg = new Thread(new Egg(numOfCount));            Thread hen = new Thread(new Hen(numOfCount));            egg.start();            hen.start();        } catch (Exception e) {            System.out.println("Error((");        }    }}