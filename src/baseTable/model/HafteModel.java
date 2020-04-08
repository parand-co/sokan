package baseTable.model;

import java.util.ArrayList;
import java.util.List;

public class HafteModel {

    private Integer hafte;
    private List<RoozModel> roozModels = new ArrayList<>();

    public Integer getHafte() {
        return hafte;
    }

    public void setHafte(Integer hafte) {
        this.hafte = hafte;
    }

    public List<RoozModel> getRoozModels() {
        return roozModels;
    }

    public void setRoozModels(List<RoozModel> roozModels) {
        this.roozModels = roozModels;
    }
}
