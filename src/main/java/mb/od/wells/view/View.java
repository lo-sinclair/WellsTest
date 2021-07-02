package mb.od.wells.view;

import java.util.List;

public class View {

    public String wellsTable(List<WellDataModel> wellsData){
        String out = "";
        out += String.format("%-20s | %-20s\n", "--------------------", "--------------------");
        out += String.format("%-20s | %-20s\n", "Имя скважины", "Кол-во оборудования");
        out += String.format("%-20s | %-20s\n", "--------------------", "--------------------");
        for (WellDataModel wd : wellsData ) {
            out += String.format("%-20s | %-20d\n", wd.getName(), wd.getEquipments().size());
        }

        return out;
    }
}
