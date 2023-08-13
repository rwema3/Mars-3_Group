import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

public class PrescriptionManagement {



    public static void main(String[] args) throws IOException, ParseException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice, numMedications;
        Prescription prescription = new Prescription();

        while(true) {

            while(true) {
                System.out.println("1. Add a prescription");
                System.out.println("2. View prescriptions");
                System.out.println("3. Delete Prescription");
                System.out.println("4. Exit");

                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:

                        System.out.print("Enter Prescription ID: ");
                        prescription.setPrescriptionID(reader.readLine());


                        System.out.print("Enter Customer ID: ");
                        prescription.setCustomerID(reader.readLine());

                        System.out.print("Enter Doctor's Name: ");
                        prescription.setDoctorName(reader.readLine());

                        //setting the date
                        prescription.setDate(LocalDate.now());


                        System.out.print("Enter the number of medications to add: ");
                        numMedications = Integer.parseInt(reader.readLine());

                        ArrayList<Medication> medications = new ArrayList<>();
                        String medicationName, medicationDetails, dosage, medicationID;
                        int quantity;

                        if(!displayMedications("products.json")) {
                            System.out.println("No medications available\n");
                            System.out.println("Exiting the Precription Management section...");
                            System.exit(0);
                        }


                        for (int i = 1; i <= numMedications; i++) {

                            System.out.println("Enter details for Medication " + i + ":");


                            System.out.print("ID: ");
                            medicationID = reader.readLine();

                            System.out.print("Name: ");
                            medicationName = reader.readLine();

                            System.out.print("Details: ");
                            medicationDetails = reader.readLine();

                            System.out.print("Dosage: ");
                            dosage = reader.readLine();

                            System.out.print("Quantity: ");
                            quantity = Integer.parseInt(reader.readLine());

                            Medication medication = new Medication(medicationID, medicationName, medicationDetails, dosage, quantity);
                            medications.add(medication);

                        }

                        prescription.setMedications(medications);

                        prescription.addPrescription(prescription);



                        break;


                    case 2:
                        // Listing all prescriptions in the system
                        ArrayList <Prescription> prescriptions = prescription.viewPrescription();
                        if(prescriptions.size()==0) {
                            System.out.println("No precriptions available\n");
                        }
                        else {
                            System.out.println("| PrescriptionID |  DoctorName   |    CustomerID | \tDate\t | ");
                            System.out.println("******************************************************************");

                            for(Prescription p: prescriptions)
                            {
                                System.out.println("|\t  "+ p.getPrecriptionID()+"\t\t"+ p.getDoctorName()+ "\t\t  " + p.getCustomerID()+"\t\t" + p.getDate());

                                System.out.println("");
                                System.out.println("| MedicationID |  \tName    | \t Quantity | ");
                                for(Medication med : p.getMedications())
                                {
                                    System.out.println("|\t  "+ med.getID()+"\t\t"+ med.getName()+ "\t\t " + med.getQuantity() );
                                    //Use to format String to 10 characters
                                    //String.format("%-10s", med.getName().substring(0, Math.min(med.getName().length(), 10)));
                                }

                                System.out.print("\n");
                                System.out.println("*****************************************************************");
                            }

                            System.out.println("");
                        }

                        break;
                    case 3:
                        System.out.println("Prescription was Deleted");
                        break;
                    case 4:
                        System.out.println("Exiting the Precription Management section...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }


            }


        }
    }




    public static boolean displayMedications(String filePath) throws FileNotFoundException, IOException, ParseException {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        File file = new File(filePath);

        if(!file.exists()) {
            System.out.println("The products.json doesnot exist, creating a new product file");
            file.createNewFile();
        }

        JSONParser parser = new JSONParser();
        try(FileReader fileReader = new FileReader(filePath)){
            if (fileReader.read() == -1) {
                return false;
            }
            else {
                fileReader.close();
                JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

                System.out.println("---------------------------------------------------------------------------------------");
                System.out.println("|\t"  + "\t\t  "  + "\t\t\t\t");
                System.out.println("|\t" + "\t\t"  +  "Available Medications" + "\t\t");
                System.out.println("|\t"  + "\t\t  "  + "\t\t\t\t");
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.println("| Medication ID |  Medication Name   |    Medication Price |    Medication Quantity |");
                System.out.println("---------------------------------------------------------------------------------------");

                for (Object obj: jsonArray) {
                    JSONObject jsonObject =(JSONObject) obj;

                    String medicationID = (String) jsonObject.get("code");
                    String medicationName = (String) jsonObject.get("name");
                    Double medicationPrice = (Double) jsonObject.get("price");
                    long medicationQuantity = (long) jsonObject.get("quantity");


                    System.out.println("|\t" + medicationID + "\t\t" + medicationName + "\t\t  " + decimalFormat.format(medicationPrice) + "\t\t\t  " + medicationQuantity + "\t\t");

                }
                System.out.println("---------------------------------------------------------------------------------------");


            }
        }
        return true;

    }






}
