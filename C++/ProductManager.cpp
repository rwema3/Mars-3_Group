#include "SearchProduct.cpp"

class ProductManager
{
private:
    ProductClass pClass;
public:
    int getMenu(){

        bool checkChoice=false;
        int selectedChoice = -1;
        while (!checkChoice)
        {
            cout<<"Menu:"<<endl;
            cout<<"1. Add Product"<<endl;
            cout<<"2. Search Products By Name"<<endl;
            cout<<"3. Search Products By Brand"<<endl;
            cout<<"4. Search Products By Category"<<endl;
            cout<<"5. Exit"<<endl;

            // Select a value between 1 and 4
            cout<<"Enter your choice: ";
            cin>>selectedChoice;
         
            if(cin.fail() || selectedChoice<1 || selectedChoice>5 ){

                cout<<"Enter a valid choice"<<endl;
                cin.clear();
                cin.ignore(numeric_limits<streamsize>::max(), '\n'); 
            }
            else{
                checkChoice = true;
            }

        }
        
        return selectedChoice;
    }

    void addProduct(){
        Product p = pClass.createProduct();
        FileHandler fHandler;
        fHandler.saveToJsonFile(p);
    }
    
};

int main()
{

    Product prod;
    ProductManager prodManager;

    SearchProduct sProd;
    string searchText;


    vector<Product> prodList;
    int choice = -1;

    while ( choice !=5)
    {
        /* code */

        choice = prodManager.getMenu();

        if(choice == 1){

            prodManager.addProduct();
        }

        else if (choice == 2){

            cout<<"Enter product name to search: ";
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            getline(cin, searchText);

            cout<<"Searching Text: "<<searchText<<" ..."<<endl;

            prodList = sProd.searchByName(searchText);
            sProd.showSearchResult(prodList, searchText);

        }

        else if(choice == 3){

            cout<<"Enter product brand to search: ";
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            getline(cin, searchText);

            cout<<"Searching Text: "<<searchText<<" ..."<<endl;
            
            prodList = sProd.searchByBrand(searchText);
            sProd.showSearchResult(prodList, searchText);
        }

        else if(choice == 4){

            cout<<"Enter product Category to search: ";
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            getline(cin, searchText);

            cout<<"Searching Text: "<<searchText<<" ..."<<endl;
            
            prodList = sProd.searchByCategory(searchText);
            sProd.showSearchResult(prodList, searchText);
        }
        
        else if (choice == 5){
            cout<< "Exiting Product Catalog Program..........";
            return 0;

        }
    }

    return 0;
}


