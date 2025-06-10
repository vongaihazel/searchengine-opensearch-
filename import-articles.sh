#!/bin/bash

# Loop through each JSON object in articles.json and POST to the API
jq -c '.[]' articles.json | while read -r article; do
  curl -X POST http://localhost:8080/search/api/articles \
       -H "Content-Type: application/json" \
       -d "$article"
done
