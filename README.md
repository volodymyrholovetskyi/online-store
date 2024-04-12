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
| 1      | 12        |
| 2      | 10        | 
| 4      | 7         | 
| 6      | 6.2       | 
| 8      | 6.2       |      
Зі спостереження видно, що підвищення продуктивності починається з 4 потоків і майже в рази швидше, ніж при однопотоковій обробці даних. У моєму випадку програма тестувалася на 8-ядерному процесорі і при подвійному збільшенні потоків (відносно кількості ядер) почала падати продуктивність, на мій погляд це пов'язано з додатковим часом перемикання між потоками. Найгірша продуктивність у однопотоковому режимі, оскільки всі файли зчитувалися послідовно.

## Technologies
- Java 17
- Lombok
- JUnit 5, Mockito, AssertJ, JUnitParams
- Jackson

## Set-up
1. Скопіюйте папку [task](./task) на робочий стіл і виконайте наступну команду в терміналі в поточній папці: ```java -jar online-store.jar <foleder_name> <args_name> <params_for_status(optional)>```
2. Приклади запуску програми з допустимим аргументами:
- >java -jar online-store.jar order-data items - підраховує продукти у всіх замовленнях
- >java -jar online-store.jar order-data orderDate - підраховує замовлення на поточний день
- >java -jar online-store.jar order-data status - підрахувати всі статуси
- >java -jar online-store.jar order-data status <param_1> <param_2> ... - підрахувати тільки вибрані типи статусів (приймає будь-яку кількість параметрів, усі доступні: NEW, PAID, CANCELED, SHIPPED).