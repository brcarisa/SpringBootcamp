package edu.school21.app;


import edu.school21.preprocessor.PreProcessor;
import edu.school21.preprocessor.PreProcessorToUpperImpl;
import edu.school21.printer.PrinterWithPrefixImpl;
import edu.school21.renderer.Renderer;
import edu.school21.renderer.RendererErrImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix ");
        printer.print("Hello!");
        System.out.println("--------WORK WITH SPRING");
        PrinterWithPrefixImpl springPrinter = context.getBean(PrinterWithPrefixImpl.class);
        springPrinter.setPrefix("SPRING RULIT ");
        springPrinter.print("Hello World!");

    }
}
