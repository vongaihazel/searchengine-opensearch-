# Read each article in the JSON array and send a POST request to my API
Get-Content .\articles.json | jq -c '.[]' | ForEach-Object {
    Invoke-RestMethod -Method Post `
                      -Uri http://localhost:8080/search/api/articles `
                      -ContentType "application/json" `
                      -Body $_
}
