package mb.od.wells.entity;

public class Equipment {

    private int id;
    private String name;
    private int well_id;

    public Equipment(int id, String name, int well_id) {
        setId(id);
        setName(name);
        setWell_id(well_id);
    }

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
    public long getWell_id() {
        return well_id;
    }
    public void setWell_id(int well_id) {
        this.well_id = well_id;
    }

}
