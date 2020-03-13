import java.util.ArrayList;
import java.util.List;

public class Resident {

    private String name;
    private List<Hospital> listOfPreferedHospitals;

    public Resident(String name) {
        this.name = name;
    }

    public Resident(String name, Hospital ... hospitals){
        this.name = name;
        this.listOfPreferedHospitals = new ArrayList<>();

        for(Hospital hospital : hospitals){
            listOfPreferedHospitals.add(hospital);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Resident{" +
                "name='" + name + '\'' +
                '}';
    }
}
