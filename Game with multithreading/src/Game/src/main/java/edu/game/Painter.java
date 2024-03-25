package edu.game;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Painter {
    public static void paintMap(char[][] map, Settings settings) {
        ColoredPrinter printer = new ColoredPrinter();
        for (char[] chars : map) {
            for (char aChar : chars) {
                if (settings.setType(aChar) == Type.EMPTY)
                    printer.print(" " + aChar + " ", Ansi.Attribute.NONE,
                            settings.getEnemyFColor(), settings.getColor(Type.EMPTY));
                else
                    printer.print(" " + aChar + " ", Ansi.Attribute.NONE,
                            Ansi.FColor.BLACK, settings.getColor(settings.setType(aChar)));
            }
            System.out.println();
        }
    }
}
