import json

from typing import List, Dict, Union
from .product import Product


class Prescription:
    def __init__(self, DoctorName: str, PrescriptionID: str, Medications: List[Dict[str, Union[int, str, bool]]],
                 CustomerID: str, Date: str) -> None:
        self.DoctorName = DoctorName
        self.PrescriptionID = PrescriptionID
        self.Medications = Medications
        self.CustomerID = CustomerID

    def medecineInPrescription(self, product: Product, quantity: int):
        return any([not el['ProcessedStatus'] and el['id'] == product.code and el['quantity'] == quantity for el in self.Medications])
    
    def markComplete(self, product: Product, quantity: int):
        for med in self.Medications:
            if product.code == med['id']:
                med['ProcessedStatus'] = True

    def dump(self, outfile: str):
        with open(outfile, 'r') as fin:
            data = json.load(fin)
        for el in data:
            if el['PrescriptionID'] == self.PrescriptionID:
                el['Medications'] = self.Medications

        with open(outfile, 'w') as fout:
            json.dump(data, fout)
    
    @classmethod
    def load(cls, infile: str, id: str):
        with open(infile, 'r') as fin:
            data = json.load(fin)
        prescriptions = {vals['PrescriptionID']: cls(**vals) for vals in data}
        if id in prescriptions:
            return prescriptions[id]
        else:
            return None