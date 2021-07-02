package mb.od.wells.view;

import mb.od.wells.entity.Equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WellDataModel {
    public int id;
    public String name;

    public List<Equipment> equipments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
