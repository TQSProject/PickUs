import requests
import json
import time

acps = [
    {
        "name": "Fnac Aveiro",
        "city": "Aveiro"
    },
    {
        "name": "Continente Glicinias",
        "city": "Aveiro"
    },
    {
        "name": "Worten Lisboa",
        "city": "Lisboa"
    },
    {
        "name": "Seaside Setubal",
        "city": "Setubal"
    },
    {
        "name": "Almada Shopping",
        "city": "Setubal"
    },
    {
        "name": "Worten Porto",
        "city": "Porto"
    },
    {
        "name": "LIDL Braga",
        "city": "Braga"
    }
]

orders = [
    {
        "store": "eStore",
        "buyer": "Ricardo",
        "product": "PS4",
        "acp": 1
    },
    {
        "store": "eStore",
        "buyer": "Ricardo",
        "product": "PS5",
        "count": 2,
        "acp": "Worten Porto"
    },
    {
        "store": "eStore",
        "buyer": "Daniel",
        "product": "Toothpaste Max Pro",
        "count": 3,
        "acp": "Continente Glicinias"
    },
    {
        "store": "eStore",
        "buyer": "Daniel",
        "acp": "Almada Shopping",
        "products": [
            {
                "name": "PS5"
            },
            {
                "name": "Water Bottle 1L",
                "count": 3
            }
        ]
    }, 
    {
        "store": "eStore",
        "buyer": "Ricardo",
        "acp": "Almada Shopping",
        "products": [
            {
                "name": "Razer Mouse Pro",
                "count": 1
            },
            {
                "name": "Orange backpack",
                "count": 2
            },
            {
                "name": "Red apple"
            }
        ]
    }
]

def load_acps(acps):
    print("POSTing", len(acps), "ACPs...")
    url = "http://localhost:8080/api/v1/acps"
    headers = {'Content-Type': 'application/json'}
    for acp in acps:
        print("POSTing ACP", acp["name"])
        response = requests.post(url, data=json.dumps(acp), headers=headers)
        if response.status_code != 200:
            print("Error posting ACP", acp["name"])

    response = requests.get(url)
    if response.status_code != 200:
        print("Error GETing", url)
    data = response.json()
    numAcpsInDb = len(data)
    expectedLen = len(acps)
    if numAcpsInDb != expectedLen:
        print("Expected database to contain", expectedLen, "ACPs but it contains", numAcpsInDb)

def load_orders(orders):
    print("POSTing", len(orders), "orders...")
    url = "http://localhost:8080/api/v1/orders"
    headers = {'Content-Type': 'application/json'}
    expectedOrders = 0
    for order in orders:
        print("POSTing order...")
        response = requests.post(url, data=json.dumps(order), headers=headers)
        if response.status_code != 200:
            print("Error posting order")
        if "product" in order:
            expectedOrders += 1
        else:
            assert "products" in order
            expectedOrders += len(order["products"])

    response = requests.get(url)
    if response.status_code != 200:
        print("Error GETing", url)
    data = response.json()
    numOrdersInDb = len(data)
    if numOrdersInDb != expectedOrders:
        print("Expected database to contain", expectedOrders, "orders but it contains", numOrdersInDb)

def update_acps_state():
    print("Updating ACPs state...")
    headers = {'Content-Type': 'application/json'}

    url = "http://localhost:8080/api/v1/acps/1"
    response = requests.post(url, data="{\"status\": \"APPROVED\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating ACP status")

    url = "http://localhost:8080/api/v1/acps/2"
    response = requests.post(url, data="{\"status\": \"APPROVED\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating ACP status")

    url = "http://localhost:8080/api/v1/acps/3"
    response = requests.post(url, data="{\"status\": \"APPROVED\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating ACP status")

    url = "http://localhost:8080/api/v1/acps/6"
    response = requests.post(url, data="{\"status\": \"REFUSED\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating ACP status")

    url = "http://localhost:8080/api/v1/acps/7"
    response = requests.post(url, data="{\"status\": \"REFUSED\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating ACP status")

def update_orders_state():
    print("Updating orders state...")
    headers = {'Content-Type': 'application/json'}

    # This one stays DELIVERING
    url = "http://localhost:8080/api/v1/orders/3"
    response = requests.post(url, data="{\"status\": \"DELIVERING\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating order status")

    # orders/1 goes DELIVERING -> DELIVERED_AND_WAITING_FOR_PICKUP -> PICKED_UP
    time.sleep(1)
    url = "http://localhost:8080/api/v1/orders/1"
    response = requests.post(url, data="{\"status\": \"DELIVERING\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating order status")
    time.sleep(3)
    response = requests.post(url, data="{\"status\": \"DELIVERED_AND_WAITING_FOR_PICKUP\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating order status")
    time.sleep(2)
    response = requests.post(url, data="{\"status\": \"PICKED_UP\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating order status")

    # orders/7 goes DELIVERING -> DELIVERED_AND_WAITING_FOR_PICKUP
    url = "http://localhost:8080/api/v1/orders/7"
    response = requests.post(url, data="{\"status\": \"DELIVERING\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating order status")
    time.sleep(2)
    response = requests.post(url, data="{\"status\": \"DELIVERED_AND_WAITING_FOR_PICKUP\"}", headers=headers)
    if response.status_code != 200:
        print("Error updating order status")

if "__main__" == __name__:
    load_acps(acps)
    print()
    load_orders(orders)
    print()
    update_acps_state()
    print()
    update_orders_state()

