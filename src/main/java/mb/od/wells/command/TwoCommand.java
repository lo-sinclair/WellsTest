package mb.od.wells.command;

import mb.od.wells.view.Render;
import mb.od.wells.view.View;
import mb.od.wells.view.WellDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwoCommand implements ICommand{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean execute() {
        System.out.println("Укажите имена скважен через запятую или пробел:");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        String[] names = input.split(" |,");
        for (int i = 0; i < names.length; i++) {
            names[i] = names[i].trim();
        }

        System.out.println(names);

        Render render = new Render();
        View view = new View();
        List<WellDataModel> wdm = render.makeWellsData(names);

        System.out.print(view.wellsTable(wdm));

        return true;
    }
}
