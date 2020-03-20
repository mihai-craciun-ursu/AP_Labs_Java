import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {



    public static Faker faker = new Faker();


    public static void main(String[] args) {


        Random random = new Random();

//        Resident[] residentArray = IntStream.rangeClosed(0,3).mapToObj(i -> new Resident("R"+i)).toArray(Resident[]::new);
//        Hospital[] hospitalArray = IntStream.rangeClosed(0,2).mapToObj(i -> new Hospital("H"+i, random.nextInt(3) + 2)).toArray(Hospital[]::new);
//
//
//        List<Resident> listOfResidents = new ArrayList<>();
//        for(Resident resident : residentArray){
//            listOfResidents.add(resident);
//        }
//
//
//
//        Collections.sort(listOfResidents, Comparator.comparing(Resident::getName));
//
//        Set<Hospital> setOfHospitals = new TreeSet<>();
//
//        Collections.addAll(setOfHospitals, hospitalArray);
//
//
//        Map<Resident, List<Hospital>> mapOfResidents = new LinkedHashMap<>();
//        Map<Hospital, List<Resident>> mapOfHospitals = new LinkedHashMap<>();
//
//
//
//
//        //Populating residents Map
//        mapOfResidents.put(listOfResidents.get(0),
//                Arrays.asList(hospitalArray[0], hospitalArray[1], hospitalArray[2]));
//
//        mapOfResidents.put(listOfResidents.get(1),
//                Arrays.asList(hospitalArray[0], hospitalArray[1], hospitalArray[2]));
//
//        mapOfResidents.put(listOfResidents.get(2),
//                Arrays.asList(hospitalArray[0], hospitalArray[1]));
//
//        mapOfResidents.put(listOfResidents.get(3),
//                Arrays.asList(hospitalArray[0], hospitalArray[2]));
//
//
//        //Populate hospitals Map
//        mapOfHospitals.put(hospitalArray[0],
//                Arrays.asList(residentArray[3], residentArray[0], residentArray[1], residentArray[2]));
//
//        mapOfHospitals.put(hospitalArray[1],
//                Arrays.asList(residentArray[0], residentArray[2], residentArray[1]));
//
//        mapOfHospitals.put(hospitalArray[2],
//                Arrays.asList(residentArray[0], residentArray[1], residentArray[3]));
//
//
//        List<Hospital> target = Arrays.asList(hospitalArray[0], hospitalArray[1]);
//
//        List<Resident> prefferedResidents = listOfResidents.stream()
//                .filter(resident -> mapOfResidents.get(resident).containsAll(target))
//                .collect(Collectors.toList());
//
//        for(Resident resident : prefferedResidents){
//            System.out.println(resident.getName());
//        }
//
//        List<Hospital> prefferedHospitals = setOfHospitals.stream()
//                .filter(hospital -> mapOfHospitals.get(hospital).get(0).equals(residentArray[0]))
//                .collect(Collectors.toList());
//
//        for(Hospital hospital : prefferedHospitals){
//            System.out.println(hospital.getName());
//        }
//
//
//        Problem problem = new Problem(mapOfResidents, mapOfHospitals);
//
//        Matching matchingHardCoded = new Matching(problem);

        //

        Problem randomProblem = createRandomInstanceOfTheProblem();
        Matching matchingRandom = new Matching(randomProblem);

        printInstanceOfTheProblem(randomProblem);

        List<Element> solution = matchingRandom.getSolution();


        System.out.println("Possible solution:");
        for(Element element : solution){
            System.out.println(element.getResident().getName() + " : " + element.getHospital().getName());
        }

        if(isSolutionStable(solution, randomProblem)){
            System.out.println("\n\nSolution is stable");
        }else{
            System.out.println("\nSolution is not stable");
        }



    }

    private static boolean isSolutionStable(List<Element> elementList, Problem problem){

        for(Element element : elementList){
            List<Resident> hospitalResidentList = problem.getMapOfHospitals().get(element.getHospital());
            List<Hospital> residentHospitalList = problem.getMapOfResidents().get(element.getResident());

            for(Resident resident : hospitalResidentList){
                if((hospitalResidentList.indexOf(resident) > hospitalResidentList.indexOf(element.getResident()))
                        && hospitalResidentList.indexOf(element.getResident()) > 0){
                    return false;
                }
            }

            for(Hospital hospital : residentHospitalList){
                if((residentHospitalList.indexOf(hospital) > residentHospitalList.indexOf(element.getHospital()))
                        && (residentHospitalList.indexOf((element.getHospital())) > 0) ){
                    return false;
                }
            }

        }

        return true;
    }

    private static Problem createRandomInstanceOfTheProblem(){
        Random random = new Random();

        int numberOfResidents = random.nextInt(81) + 20;
        int numberOfHospitals = random.nextInt(21) + 10;
        int totalCapacity = 0;

        List<Resident> listOfResidents = new ArrayList<>();
        Set<Hospital> setOfHospitals = new TreeSet<>();

        Map<Resident, List<Hospital>> mapOfResidents = new LinkedHashMap<>();
        Map<Hospital, List<Resident>> mapOfHospitals = new LinkedHashMap<>();

        for(int residentIndex = 0; residentIndex < numberOfResidents; residentIndex++){
            listOfResidents.add(new Resident(faker.name().name()));
        }

        Collections.sort(listOfResidents, Comparator.comparing(Resident::getName));

        for(int hospitalIndex = 0; hospitalIndex < numberOfHospitals; hospitalIndex++){
            int randomCapacity = random.nextInt(6) + 10;
            totalCapacity = totalCapacity + randomCapacity;
            setOfHospitals.add(new Hospital(faker.medical().hospitalName(), randomCapacity));
        }


            while(totalCapacity < numberOfResidents){
                System.out.println("OverLoad");

                int randomCapacity = random.nextInt(6) + 10;
                totalCapacity = totalCapacity + randomCapacity;

                setOfHospitals.add(new Hospital(faker.medical().hospitalName(), randomCapacity));
                numberOfHospitals = numberOfHospitals + 1;
            }


        for(Resident resident : listOfResidents){
            List<Hospital> copyOfHospitalsList = new ArrayList<>();
            copyOfHospitalsList.addAll(setOfHospitals);
            List<Hospital> listOfPreferredHospitals = new ArrayList<>();

            int randomNumberOfPreferences = random.nextInt(8) + 3;

            for(int preferenceIndex = 0; preferenceIndex < randomNumberOfPreferences; preferenceIndex++){
                int chosenHospital = random.nextInt(copyOfHospitalsList.size());
                listOfPreferredHospitals.add(copyOfHospitalsList.get(chosenHospital));
                copyOfHospitalsList.remove(copyOfHospitalsList.get(chosenHospital));
            }

            mapOfResidents.put(resident, listOfPreferredHospitals);
        }

        for(Hospital hospital : setOfHospitals){
            List<Resident> copyOfResidentsList = new ArrayList<>();
            copyOfResidentsList.addAll(listOfResidents);
            List<Resident> listOfPreferredResidents = new ArrayList<>();

            int randomNumberOfPreferences = random.nextInt(18) + 3;

            for(int preferenceIndex = 0; preferenceIndex < randomNumberOfPreferences; preferenceIndex++){
                int chosenResident = random.nextInt(copyOfResidentsList.size());
                listOfPreferredResidents.add(copyOfResidentsList.get(chosenResident));
                copyOfResidentsList.remove(copyOfResidentsList.get(chosenResident));
            }

            mapOfHospitals.put(hospital, listOfPreferredResidents);
        }

        return new Problem(mapOfResidents, mapOfHospitals);
    }

    private static void printInstanceOfTheProblem(Problem problem){
        System.out.println("\n\nResidents preferences\n");

        for(Map.Entry<Resident, List<Hospital>> entry : problem.getMapOfResidents().entrySet()){
            System.out.print(entry.getKey().getName() + " : ( ");
            for(Hospital hospital : entry.getValue()){
                System.out.print(hospital.getName() + " ");
            }
            System.out.println(")");
        }


        System.out.println("\n\nHospitals preferences");
        for(Map.Entry<Hospital, List<Resident>> entry : problem.getMapOfHospitals().entrySet()){
            System.out.print(entry.getKey().getName() + "(" + entry.getKey().getCapacity() + ")" + " : ( ");
            for(Resident resident : entry.getValue()){
                System.out.print(resident.getName() + " ");
            }
            System.out.println(")");
        }
    }
}
