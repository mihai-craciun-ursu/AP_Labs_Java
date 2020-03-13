import java.util.List;
import java.util.Map;

public class Problem {
    private Map<Resident, List<Hospital>> mapOfResidents;
    private Map<Hospital, List<Resident>> mapOfHospitals;

    public Problem(Map<Resident, List<Hospital>> mapOfResidents, Map<Hospital, List<Resident>> mapOfHospitals) {
        this.mapOfResidents = mapOfResidents;
        this.mapOfHospitals = mapOfHospitals;
    }

    public Map<Resident, List<Hospital>> getMapOfResidents() {
        return mapOfResidents;
    }

    public Map<Hospital, List<Resident>> getMapOfHospitals() {
        return mapOfHospitals;
    }
}
