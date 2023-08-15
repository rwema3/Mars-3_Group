import json

class Product:
    def __init__(
            self, 
            code: int, 
            name: str, 
            brand: str,
            description: str,
            quantity: int, 
            price: float, 
            dosage_instruction: str,
            requires_prescription: bool,
            category: str) -> None:
        self.code = code
        self.name = name
        self.brand = brand
        self.quantity = quantity
        self.category = category
        self.description = description
        self.price = price
        self.dosage_instruction = dosage_instruction
        self.requires_prescription = (requires_prescription != 0)

    def to_json(self):
        return json.dumps(self.__dict__)

    def __str__(self) -> str:
        return self.name
    
    def __repr__(self) -> str:
        return f'[{self.code}]{self.name}, {self.brand}: {self.quantity}x, {self.price}'