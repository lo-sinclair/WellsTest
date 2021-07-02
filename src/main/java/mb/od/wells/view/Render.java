package mb.od.wells.view;

import mb.od.wells.Main;
import mb.od.wells.entity.Well;

import java.util.ArrayList;
import java.util.List;

public class Render {

    public List<WellDataModel> makeWellsData( String[] well_names ){
        List<WellDataModel> wellsData = new ArrayList<>();

        for( String name : well_names ) {
            WellDataModel wdm = new WellDataModel();
            Well well = Main.getDb().findWellByName(name);
            if (well != null) {
                wdm.setId(well.getId());
                wdm.setName(well.getName());
                wdm.setEquipments( Main.getDb().findEquipmentByWell(well.getId()));
                wellsData.add(wdm);
            }
        }

        return wellsData;
    }

    public List<WellDataModel> makeWellsData(){
        List<WellDataModel> wellsData = new ArrayList<>();

        List<Well> wells = Main.getDb().findAllWells();

        for( Well well : wells ) {
            WellDataModel wdm = new WellDataModel();
            if (well != null) {
                wdm.setId(well.getId());
                wdm.setName(well.getName());
                wdm.setEquipments( Main.getDb().findEquipmentByWell(well.getId()));
                wellsData.add(wdm);
            }
        }
        return wellsData;
    }
}
