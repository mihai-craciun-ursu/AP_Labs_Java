import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Matching {

    private Problem problem;
    private List<Element> solutionList;

    public Matching(Problem problem) {
        this.problem = problem;
        solutionList = new ArrayList<>();

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

    public boolean isElementOfList(Resident resident, Hospital hospital){
        for(Element element : solutionList){
            if(element.getResident().equals(resident)){
                if(element.getHospital().equals(hospital)){
                    return true;
                }
            }
        }
        return false;
    }
}
