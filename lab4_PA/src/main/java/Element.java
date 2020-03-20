public class Element {

    private Hospital hospital;
    private Resident resident;

    public Element(Hospital hospital, Resident resident) {
        this.hospital = hospital;
        this.resident = resident;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Resident getResident() {
        return resident;
    }
}
