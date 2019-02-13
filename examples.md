# Example API requests

Example curl requests to check APIs working.

## Running locally

```bash
curl -X "POST" "http://localhost:3000/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "16235074"
}'

> echo true
```

```bash
curl -X "POST" "http://localhost:3000/vat/validate" \
     -H 'Content-Type: application/json' \
     -d $'{
  "memberStateCode": "FI",
  "vatNumber": "FI16235075"
}'

echo false
```

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
