# Online Store

* [General info](#general-info)
* [Experiments](#experiments)
* [Technologies](#technologies)
* [Set-up](#set-up)

## General Info

Предметна область у проекті описується двома сутностями Order та Customer (many-to-one, unidirectional).

- Input data:
```
[
  {
    "id": 1,
    "status": "NEW",
    "items": [
      "Effective Java",
      "Java 8 in Action",
      "Java Database"
    ],
    "customer": {
      "id": 1,
      "email": "customer1@gmail.com",
      "firstName": "First",
      "lastName": "Customer",
      "phone": "+380965643222",
      "street": "Ruska 1",
      "city": "Ternopil",
      "zipCode": "46001"
    },
    "orderDate": "2016-01-25"
  },
  {
    "id": 2,
    "status": "NEW",
    "items": [
      "Java Database",
      "Java 8 in Action"
    ],
    "customer": {
      "id": 2,
      "email": "customer2@gmail.com",
      "firstName": "Second",
      "lastName": "Customer",
      "phone": "+380965643722",
      "street": "Ruska 10",
      "city": "Odessa",
      "zipCode": "46001"
    },
    "orderDate": "2024-01-25"
  }
]
```
- Output data for item attribute:
```
<statistics>
  <orders>
    <item>
      <value>Java Database</value>
      <count>2</count>
    </item>
    <item>
      <value>Java 8 in Action</value>
      <count>2</count>
    </item>
    <item>
      <value>Effective Java</value>
      <count>1</count>
    </item>
  </orders>
</statistics>
```

## Experiments

Для експериментів було взято 40 файли загальним обсягом 1 ГБ.

| Thread | ~Time (s) |
|--------|-----------|
| 1      | 10        |
| 2      | 8         | 
| 4      | 6         | 
| 6      | 5.2       | 
| 8      | 5.2       |    

Зі спостереження видно, що підвищення продуктивності починається з 4 потоків і майже в 2 рази швидше, ніж при однопотоковій обробці даних. У моєму випадку програма тестувалася на 8-ядерному процесорі і при подвійному збільшенні потоків (відносно кількості ядер) почала падати продуктивність, на мій погляд це пов'язано з додатковим часом перемикання між контекстами потоків. Найгірша продуктивність у однопотоковому режимі, оскільки всі файли зчитувалися послідовно.

## Technologies
- Java 17
- Lombok
- JUnit 5, Mockito, AssertJ, JUnitParams
- Jackson Databind
- Maven

## Set-up
1. Папка [task](./task) містить усі необхідні файли для запуску програми є два варіанти запуску: запустити файл jar із консолі або запустити файл online-store.bat (він виконає три команди послідовно для створення трьох файлів: item, status, orderDate).
2. Приклади запуску програми з допустимим аргументами:
- >java -jar online-store.jar order-data items - підраховує продукти у всіх замовленнях
- >java -jar online-store.jar order-data orderDate - підраховує замовлення на поточний день
- >java -jar online-store.jar order-data status - підрахувати всі статуси
- >java -jar online-store.jar order-data status <param_1> <param_2> ... - підрахувати тільки вибрані типи статусів (приймає будь-яку кількість параметрів, усі доступні: NEW, PAID, CANCELED, SHIPPED).

<ins> Якщо передано непідтримувані аргументи, інформація про помилку буде виведена на консоль як WARNING LEVEL Log з інформацією про помилку.</ins>