package edu.school21.spring.printer;import edu.school21.spring.renderer.Renderer;public class PrinterWithPrefixImpl implements Printer {    private final Renderer renderer;    private String prefix;    public PrinterWithPrefixImpl(Renderer renderer) {        this.renderer = renderer;    }    @Override    public void print(String s) {        renderer.print(prefix + " " + s);    }    public void setPrefix(String prefix) {        this.prefix = prefix;    }    public String getPrefix() {        return prefix;    }}