import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        System.out.println();

        Random random = new Random();

        var residentArray = IntStream.rangeClosed(0,3).mapToObj(i -> new Resident("R"+i)).toArray(Resident[]::new);
        var hospitalArray = IntStream.rangeClosed(0,2).mapToObj(i -> new Hospital("H"+i, random.nextInt(3) + 1)).toArray(Hospital[]::new);

        List<Resident> listOfResidents = new ArrayList<>();
        for(Resident resident : residentArray){
            listOfResidents.add(resident);
        }



        Collections.sort(listOfResidents, Comparator.comparing(Resident::getName));

        Set<Hospital> setOfHospitals = new TreeSet<>();
        for(Hospital hospital : setOfHospitals){
            setOfHospitals.add(hospital);
        }


        Map<Resident, List<Hospital>> mapOfResidents = new HashMap<>();
        Map<Hospital, List<Resident>> mapOfHospitals = new HashMap<>();


        //Populating residents Map
        mapOfResidents.put(listOfResidents.get(0),
                Arrays.asList(new Hospital[]{hospitalArray[0], hospitalArray[1], hospitalArray[2]}));

        mapOfResidents.put(listOfResidents.get(1),
                Arrays.asList(new Hospital[]{hospitalArray[0], hospitalArray[1], hospitalArray[2]}));

        mapOfResidents.put(listOfResidents.get(2),
                Arrays.asList(new Hospital[]{hospitalArray[0], hospitalArray[1]}));

        mapOfResidents.put(listOfResidents.get(3),
                Arrays.asList(new Hospital[]{hospitalArray[0], hospitalArray[2]}));


        //Populate hospitals Map
        mapOfHospitals.put(hospitalArray[0],
                Arrays.asList(new Resident[]{residentArray[3], residentArray[0], residentArray[1], residentArray[2]}));

        mapOfHospitals.put(hospitalArray[1],
                Arrays.asList(new Resident[]{residentArray[0], residentArray[2], residentArray[1]}));

        mapOfHospitals.put(hospitalArray[2],
                Arrays.asList(new Resident[]{residentArray[0], residentArray[1], residentArray[3]}));

        System.out.println("Residents preferences");
        for(Map.Entry<Resident, List<Hospital>> entry : mapOfResidents.entrySet()){
            System.out.print(entry.getKey().getName() + " : ( ");
            for(Hospital hospital : entry.getValue()){
                System.out.print(hospital.getName() + " ");
            }
            System.out.println(")");
        }

        System.out.println();

        System.out.println("Hospitals preferences");
        for(Map.Entry<Hospital, List<Resident>> entry : mapOfHospitals.entrySet()){
            System.out.print(entry.getKey().getName() + "(" + entry.getKey().getCapacity() + ")" + " : ( ");
            for(Resident resident : entry.getValue()){
                System.out.print(resident.getName() + " ");
            }
            System.out.println(")");
        }

        Problem problem = new Problem(mapOfResidents, mapOfHospitals);
        Matching partition = new Matching(problem);

        List<Element> solution = partition.getSolution();


        System.out.println("\nPossible solution:");
        for(Element element : solution){
            System.out.println(element.getResident().getName() + " : " + element.getHospital().getName());
        }
    }
    
}
