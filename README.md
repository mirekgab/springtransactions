# Example of spring transactions.

## How to run tests

- clone the repository
```shell
git clone https://github.com/mirekgab/springtransactions.git
```
- go into the directory
```shell
cd springtransactions/
``` 
- run the test where the order is successful
```shell
./mvnw test -Dtest="OrderControllerIT#orderCompletedSuccessfully"
```
- run the test where the order fails and throw an exception
```shell
./mvnw test -Dtest="OrderControllerIT#insufficientQuantityInOneOrderPosition"
```
