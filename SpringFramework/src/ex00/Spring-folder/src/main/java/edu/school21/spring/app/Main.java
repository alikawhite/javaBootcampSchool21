package edu.school21.spring.app;

import edu.school21.spring.preprocessor.PreProcessor;
import edu.school21.spring.preprocessor.PreProcessorToUpperImpl;
import edu.school21.spring.printer.Printer;
import edu.school21.spring.printer.PrinterWithPrefixImpl;
import edu.school21.spring.renderer.Renderer;
import edu.school21.spring.renderer.RendererErrImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Демонстрация результата создания класса Printer обычным способом:");
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix");
        printer.print("Hello!");

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        System.out.println("Демонстрация создания класса Printer с Бинами:");

        Printer printerWithBeans = context.getBean("printerWithPrefixErrToUpper", Printer.class);
        printerWithBeans.print("Hello!");

        printerWithBeans = context.getBean("printerWithPrefixErrToLower", Printer.class);
        printerWithBeans.print("Hello!");

        printerWithBeans = context.getBean("printerWithPrefixStandardToLower", Printer.class);
        printerWithBeans.print("Hello!");

        printerWithBeans = context.getBean("printerWithPrefixStandardToUpper", Printer.class);
        printerWithBeans.print("Hello!");

        printerWithBeans = context.getBean("printerWithDateTimeErrToLower", Printer.class);
        printerWithBeans.print("Hello!");

        printerWithBeans = context.getBean("printerWithDateTimeErrToUpper", Printer.class);
        printerWithBeans.print("Hello!");

        printerWithBeans = context.getBean("printerWithDateTimeStandardToLower", Printer.class);
        printerWithBeans.print("Hello!");

        printerWithBeans = context.getBean("printerWithDateTimeStandardToUpper", Printer.class);
        printerWithBeans.print("Hello!");

    }
}
