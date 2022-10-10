# Promotion Engine
We need you to implement a simple promotion engine for a checkout process. Our Cart contains a list of single character SKU ids (A, B, C.	) over which the promotion engine will need to run.
The promotion engine will need to calculate the total order value after applying the 2 promotion types
  1. buy 'n' items of a SKU for a fixed price (3 A's for 130)
  2. buy SKU 1 & SKU 2 for a fixed price ( C + D = 30 )
The promotion engine should be modular to allow for more promotion types to be added at a later date (e.g. a future promotion could be x% of a SKU unit price). For this coding exercise you can assume that the promotions will be mutually exclusive; in other words if one is applied the other promotions will not apply

## Test Setup
### Unit price for SKU IDs 
A	50<br>
B	30<br>
C	20<br>
D	15<br>
<br>
### Active Promotions
3 of A's for 130
2 of B's for 45 
C & D for 30
<br>
#### Scenario A
1 * A 50<br>
1 * B 30<br>
1 * C 20<br>
Total 100<br>
<br>
#### Scenario B
5 * A <b>130</b> + 2*50<br>
5 * B <b>45</b> + <b>45</b> + 30<br>
1 * C 20<br>
Total 370<br>
<br>
#### Scenario C
3 * A 130<br>
5 * B <b>45</b> + <b>45</b> + 30<br>
1 * C -<br>
1 * D <b>30</b><br>
Total	280<br>

### Sample Curl to test the app
curl --location --request POST 'http://localhost:8080/promotion/checkPrice' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
    "A":3,
    "B":5,
    "C":1,
    "D":1,
}'
