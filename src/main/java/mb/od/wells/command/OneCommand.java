package mb.od.wells.command;

import mb.od.wells.Main;
import mb.od.wells.SQLDatabase;
import mb.od.wells.entity.Equipment;
import mb.od.wells.entity.Well;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OneCommand implements ICommand{

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
        int eq_quantity = 0;
        String well_name = null;

        Scanner sc;

        boolean doin = true;
        while (doin) {
            System.out.println("Количество оборудования:");
            sc = new Scanner(System.in);
            if(sc.hasNextInt()) {
                eq_quantity = sc.nextInt();
                doin = false;
            }
            else {
                System.out.println("Значение должно быть целым числом. Попробуйте снова");
            }
        }

        sc = new Scanner(System.in);
        System.out.println("Имя скважины:");
        well_name = sc.nextLine();

        int well_id;
        Well well = Main.getDb().findWellByName(well_name);
        if (well == null) {
            well = new Well(0, well_name);
            well_id = Main.getDb().createWell(well);
        }
        else {
            well_id = well.getId();
        }

        ArrayList<Equipment> eqs = new ArrayList<>();
        int maxId = Main.getDb().getMaxEquipmentId();

        for(int i = 0; i < eq_quantity; i++) {
            ++maxId;
            String eq_name = "EQ" + String.format("%04d", maxId);
            Equipment eq = new Equipment(0, eq_name, well_id);
            eqs.add(eq);
        }

        int count = Main.getDb().createEquipments(eqs);
        System.out.println("Добавлено записей: " + count);

        return true;
    }
}
