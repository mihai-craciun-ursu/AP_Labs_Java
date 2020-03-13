import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Matching {

    private Problem problem;
    private List<Element> solutionList;

    public Matching(Problem problem) {
        this.problem = problem;
        solutionList = new ArrayList<>();

        System.out.println("Residents preferences");
        for(Map.Entry<Resident, List<Hospital>> entry : problem.getMapOfResidents().entrySet()){
            boolean hospitalFoundForResident = false;
            int hospitalIndex = 0;
            while(!hospitalFoundForResident){
                Hospital hospital = entry.getValue().get(hospitalIndex);
                if(hospital.getCapacity() > 0){
                    hospitalFoundForResident = true;
                    hospital.setCapacity(hospital.getCapacity() - 1);
                    solutionList.add(new Element(hospital, entry.getKey()));
                }else{
                    hospitalIndex++;
                }
            }
        }
    }

    public List<Element> getSolution(){
        return solutionList;
    }
}
