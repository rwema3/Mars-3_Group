public class Medication {

    private String ID;
    private String name;
    private String details;
    private String dosage;
    private long quantity;
    private Boolean processedStatus;

    public Medication() {
        this.processedStatus = false;
    }

    public Medication(String _id, String _name, long qty) {
        this.ID = _id;
        this.name = _name;
        this.quantity = qty;
        this.processedStatus = false;
    }

    public Medication(String id, String name, String medication_details, String dosage, int quantity) {
        this.ID = id;
        this.name = name;
        this.details = medication_details;
        this.dosage = dosage;
        this.quantity = quantity;
        this.processedStatus = false;
    }

    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public String getDetails() {
        return this.details;
    }

    public String getDosage() {
        return this.dosage;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public Boolean getProcessedStatus() {
        return this.processedStatus;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setProcessedStatus(Boolean processedStatus) {
        this.processedStatus = processedStatus;
    }

    public String toString() {
        return this.ID + "," + this.name + "," + this.quantity + "," + this.processedStatus;
    }
}