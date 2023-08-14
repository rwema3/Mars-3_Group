import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Prescription {
    private String prescriptionID;
    private String customerID;
    private String doctorName;
    private ArrayList<Medication> medications;
    private LocalDate date;
    private static JSONArray prescriptionList;
    //file declaration here
    String filePath = "prescriptions.json";


    public Prescription() {
        prescriptionList = new JSONArray();
    }

    public Prescription(String _prescriptionID, String _customerID, String _doctorName, ArrayList<Medication> _medication) {
        prescriptionID = _prescriptionID;
        customerID = _customerID;
        doctorName = _doctorName;
        medications = _medication;
        date = LocalDate.now();
    }

    public Prescription(String _prescriptionID, String _customeID, String _doctorName, LocalDate _date, ArrayList<Medication> _medication) {
        prescriptionID = _prescriptionID;
        customerID = _customeID;
        doctorName = _doctorName;
        medications = _medication;
        date = _date;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public ArrayList<Medication> getMedications() {
        return this.medications;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getPrecriptionID() {
        return this.prescriptionID;
    }


    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setMedications(ArrayList<Medication> medications_) {
        this.medications = medications_;
    }

    public void setDate(LocalDate date_) {
        this.date = date_;
    }

    public void setPrescriptionID(String prescrID) {
        this.prescriptionID = prescrID;
    }


    public void addPrescription(Prescription prescription) throws IOException, ParseException {

        // Read the existing JSON array from the file
        JSONParser parser = new JSONParser();
        JSONArray existingPrescriptions;
        try (FileReader fileReader = new FileReader(filePath)) {
            if (fileReader.read() == -1) {
                existingPrescriptions = new JSONArray(); // Create a new array if the file is empty
            } else {
                fileReader.close();
                existingPrescriptions = (JSONArray) parser.parse(new FileReader(filePath));
            }
        } catch (IOException e) {
            existingPrescriptions = new JSONArray();
        }

        // Create a JSON object for the new prescription
        JSONObject prescriptionObject = new JSONObject();
        prescriptionObject.put("PrescriptionID", prescription.getPrecriptionID());
        prescriptionObject.put("CustomerID", prescription.getCustomerID());
        prescriptionObject.put("DoctorName", prescription.getDoctorName());
        prescriptionObject.put("Medications", prescription.getMedicationsOnPrescription(prescription));
        prescriptionObject.put("Date", prescription.getDate().toString());

        // Add the new prescription to the existing array
        existingPrescriptions.add(prescriptionObject);

        // Write the updated JSON array back to the file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(existingPrescriptions.toJSONString());
            fileWriter.flush();
            System.out.println("Prescription data written to prescriptions.json successfully!");
        } catch (IOException e) {
            System.out.println("Error while writing data to file: " + e.getMessage());
        }


    }


    private JSONArray getMedicationsOnPrescription(Prescription prescription) {
        JSONArray jsonArray = new JSONArray();


        for (Medication medication : prescription.medications) {
            JSONObject medicationObject = new JSONObject();
            medicationObject.put("id", medication.getID());
            medicationObject.put("name", medication.getName());
            medicationObject.put("quantity", medication.getQuantity());
            medicationObject.put("processedStatus", medication.getProcessedStatus());
            jsonArray.add(medicationObject);
        }

        return jsonArray;
    }


    public ArrayList<Prescription> viewPrescription() throws FileNotFoundException, IOException, ParseException {
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try (FileReader fileReader = new FileReader(filePath)) {
            if (fileReader.read() == -1) {
                return new ArrayList<>();
            } else {
                fileReader.close();
                JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
                for (Object obj : jsonArray) {
                    JSONObject jsonObject = (JSONObject) obj;

                    String doctorName = (String) jsonObject.get("DoctorName");
                    String prescriptionID = (String) jsonObject.get("PrescriptionID");
                    String customerID = (String) jsonObject.get("CustomerID");
                    String date = (String) jsonObject.get("Date");
                    LocalDate dateToPrint = LocalDate.parse(date);

                    ArrayList<Medication> medications = new ArrayList<>();

                    JSONArray medicationsArray = (JSONArray) jsonObject.get("Medications");
                    for (Object medObj : medicationsArray) {
                        JSONObject medication = (JSONObject) medObj;
                        String medicationName = (String) medication.get("name");
                        String medicationID = (String) medication.get("id");
                        long quantity = (long) medication.get("quantity");
                        // Do something with medication data if needed
                        medications.add(new Medication(medicationID, medicationName, quantity));
                    }
                    prescriptions.add(new Prescription(prescriptionID, customerID, doctorName, dateToPrint, medications));

                }

            }
        }
        return prescriptions;
    }

    public void deletePrescription(String prescriptionIDToDelete) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray existingPrescriptions;

        try {
            existingPrescriptions = (JSONArray) parser.parse(new FileReader(filePath));
        } catch (IOException e) {
            existingPrescriptions = new JSONArray();
        }

        JSONArray updatedPrescriptions = new JSONArray();

        for (Object obj : existingPrescriptions) {
            JSONObject prescriptionObject = (JSONObject) obj;
            String prescriptionID = (String) prescriptionObject.get("PrescriptionID");

            if (!prescriptionID.equals(prescriptionIDToDelete)) {
                updatedPrescriptions.add(prescriptionObject);
            }
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(updatedPrescriptions.toJSONString());
            fileWriter.flush();
            System.out.println("Prescription deleted from prescriptions.json successfully!");
        } catch (IOException e) {
            System.out.println("Error while writing data to file: " + e.getMessage());
        }
    }

}

