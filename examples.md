# Example API requests

Example curl requests to check APIs working.

## Running locally

## Checks VAT number

```bash
curl -X "POST" "http://localhost:3000/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "16235074"
}'

{"valid":true,"memberStateCode":"FI","vatNumber":"16235074"}
```

## Checks VAT number, different syntax, this VAT are invalid

```bash
curl -X "POST" "http://localhost:3000/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "FI16235075"
}'

{"valid":false,"memberStateCode":"FI","vatNumber":"16235075"}
```

## Validate VAT

```bash
curl -X "POST" "https://deadbeef.execute-api.eu-west-1.amazonaws.com/dev/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "16235074"
}'

{"valid":true,"memberStateCode":"FI","vatNumber":"16235074"}
```

## Checks VAT number also specifying requester data

It calls checkVatApprox from [SOAP](http://ec.europa.eu/taxation_customs/vies/services/checkVatService?WSDL) and can query also address and company name.

```bash
curl -X "POST" "http://deadbeef.execute-api.eu-west-1.amazonaws.com/dev/vat/validateApprox" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "16235074",
  "requesterMemberStateCode": "FI",
  "requesterVatNumber": "16235074"
}'
```
