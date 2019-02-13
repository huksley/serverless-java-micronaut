# Example API requests

## Running locally

curl -X "POST" "http://localhost:3000/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "16235074"
}'

=> true

curl -X "POST" "http://localhost:3000/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "FI16235075"
}'

=> false

curl -X "POST" "http://localhost:3000/vat/validateApprox" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "16235074",
  "requesterMemberStateCode": "FI", 
  "requesterVatNumber": "16235074"
}'

=> true

curl -X "POST" "https://fi074hyo2i.execute-api.eu-west-1.amazonaws.com/dev/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "es",
  "vatNumber": "B86412491"
}'
