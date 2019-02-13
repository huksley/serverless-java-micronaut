# Example API requests

Example curl requests to check APIs working.

## Running locally

Checks VAT number

```bash
curl -X "POST" "http://localhost:3000/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "16235074"
}'

> echo true
```

Checks VAT number, different syntax, this VAT are invalid

```bash
curl -X "POST" "http://localhost:3000/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "FI16235075"
}'

echo false
```

Checks VAT number also specifying requester data. Actullly calls checkVatApprox from [SOAP](http://ec.europa.eu/taxation_customs/vies/services/checkVatService?WSDL) and can query also address and company name.

```bash
curl -X "POST" "http://localhost:3000/vat/validateApprox" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "16235074",
  "requesterMemberStateCode": "FI", 
  "requesterVatNumber": "16235074"
}'
echo true
```
