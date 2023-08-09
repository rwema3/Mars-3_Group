#include <iostream>
#include <string.h>
#include <chrono>
#include <random>
#include "ProductStruct.h"


using namespace std;
using namespace std::chrono;




class ProductClass{

    public:
    Product product;

    string generateUniqueCode()
    {
        string characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        string uniqueCode = "";
        auto now = system_clock::now();
        auto millis = duration_cast<milliseconds>(now.time_since_epoch());
        mt19937 generator(millis.count());
        uniform_int_distribution<int> distribution(0, 100000);

        // generate 32 characters long unique string

        for (int i = 0; i <= 10; i++)
        {
            int random_index = distribution(generator) % characters.length();
            uniqueCode += characters[random_index];
        }

        return uniqueCode;
    };

    float promptQuantity()
    {
        int quantity;
        cout << "Enter Quantity for the product" << endl;
        cin.clear();
        cin >> quantity;

        return quantity;
    };

    float promptPrice()
    {
        float price;
        cout << "Enter Price for the product" << endl;
        cin.clear();
        cin >> price;

        return price;
    };

    string promptName()
    {
        string name;
        cout << "Enter Name " << endl;

        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        
        getline(cin, name);
        return name;
    };

    string promptCategory()
    {
        string categ;
        cout << "Enter Categroy" << endl;

        cin.clear();
        
        getline(cin, categ);
        return categ;
    };

    string promptDescription()
    {
        string desc;
        cout << "Enter Description for the product" << endl;
        cin.clear();
    
       
        getline(cin, desc);

        return desc;
    };

    string promptBrand()
    {
        string brand;
        cout << "Enter Brand" << endl;
        cin.clear();
        
        getline(cin, brand);

        return brand;
    };

    string promptDosageInstruction()
    {
        string dsg_inst;
        cout << "Enter Dosage Instruction for the product" << endl;

        cin.clear();
       
        getline(cin, dsg_inst);

        return dsg_inst;
    }

    bool promptRequirePrescription()
    {
        bool req_presc;
        cout << "Does This Product requires prescription? type 1 for yes, ino type 0 ):" << endl;

        cin.clear();
        cin>>req_presc;

        return req_presc;
    }

    Product createProduct()
    {

        product.name = promptName();
        product.brand = promptBrand();
        product.description = promptDescription();
        product.category = promptCategory();
        product.dosageInstruction = promptDosageInstruction();
        product.quantity = promptQuantity();
        product.price = promptPrice();
        product.requires_prescription = promptRequirePrescription();
        product.code = generateUniqueCode();

        return product;
    };

    string toJson(Product prod)
    {

        string productInJson;

        productInJson = "{\"";
        productInJson += "code\":\"" + prod.code + "\",\"";
        productInJson += "name\":\"" + prod.name + "\",\"";
        productInJson += "brand\":\"" + prod.brand + "\",\"";
        productInJson += "description\":\"" + prod.description + "\",\"";
        productInJson += "dosage_instruction\":\"" + prod.dosageInstruction + "\",\"";
        productInJson += "price\":" + to_string(prod.price) + ",\"";
        productInJson += "quantity\":" + to_string(prod.quantity) + ",\"";
        productInJson += "category\":\"" + prod.category + "\",\"";
        productInJson += "requires_prescription\":" + to_string(prod.requires_prescription);
        productInJson += "}";

        return productInJson;
    };

    string getValueFromString(string txt)
    {
        char *split = strtok((char *)txt.c_str(), ":");
        int c = 0;
        string splitted[2];
        while (split != NULL)
        {
            if (c == 0)
                splitted[c] = split;
            if (c == 1)
                splitted[c] = split;
            split = strtok(NULL, ":");
            c++;
        }
        return splitted[1];
    }

    string cleanString(string txt)
    {
        char *s = (char *)calloc(txt.length() - 2, sizeof(char));
        int c = 0;
        for (int i = 1; i < txt.length() - 1; i++)
        {
            if (txt[i] == '"')
                break;
            s[c] = txt[i];
            c++;
        }
        return s;
    }

    Product fromJson(string txt)
    {

        Product prod;

        std::string key;
        std::string value;
        char *split;
        string productValues[9];
        split = strtok((char *)txt.c_str(), ",");
        int c = 0;
        while (split != NULL)
        {
            if (c == 0)
                productValues[c] = split;
            if (c == 1)
                productValues[c] = split;
            if (c == 2)
                productValues[c] = split;
            if (c == 3)
                productValues[c] = split;
            if (c == 4)
                productValues[c] = split;
            if (c == 5)
            {
                productValues[c] = split;
            }
            if (c == 6)
                productValues[c] = split;
            if (c == 7)
                productValues[c] = split;
            if (c == 8)
                productValues[c] = split;
            split = strtok(NULL, ",");
            c++;
        }


        prod.code = cleanString(getValueFromString(productValues[0]));
        prod.name = cleanString(getValueFromString(productValues[1]));
        prod.brand = cleanString(getValueFromString(productValues[2]));
        prod.description = cleanString(getValueFromString(productValues[3]));
        prod.dosageInstruction = cleanString(getValueFromString(productValues[4])).c_str();
        prod.price = atof(getValueFromString(productValues[5]).c_str());
        prod.quantity = atoi(getValueFromString(productValues[6]).c_str());
        prod.category = cleanString(getValueFromString(productValues[7]));
        prod.requires_prescription = atoi(getValueFromString(productValues[8]).c_str());
        return prod;
    };
};
