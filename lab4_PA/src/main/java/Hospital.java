import java.util.ArrayList;
import java.util.List;

public class Hospital implements Comparable<Hospital> {

    private String name;
    private int capacity;
    private List<Resident> listOfPrefferedResidents;

    public Hospital(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public Hospital(String name, Resident ... residents){
        this.name = name;
        this.listOfPrefferedResidents = new ArrayList<>();

        for(Resident resident : residents){
            this.listOfPrefferedResidents.add(resident);
        }
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int compareTo(Hospital hospital) {



        if(this.getCapacity() > hospital.getCapacity()){return 1;}
        else if(this.getCapacity() < hospital.getCapacity()){return -1;}
        else return this.getName().compareTo(hospital.getName());
    }
}



